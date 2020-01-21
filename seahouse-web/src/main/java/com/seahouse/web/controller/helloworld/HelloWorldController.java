package com.seahouse.web.controller.helloworld;

import com.seahouse.service.helloword.HelloWorldBS;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <ul>
 * <li>文件名称: HelloWorldController</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2019/5/11</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */
@Controller
@RequestMapping("/")
public class HelloWorldController {

    @Resource(name = "helloWorldBsImpl")
    private HelloWorldBS helloWorldBS;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HelloWorldController.class);


    @RequestMapping("/hello")
    public String helloWorld(HttpServletRequest request, HttpServletResponse response) {
        helloWorldBS.sayHello();
        log.info("登陆网址：{}","pages/hello/hello");
        return "pages/hello/hello";
    }


    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        helloWorldBS.sayHelloJdbc();
        return "pages/hello/hello";
    }



}
