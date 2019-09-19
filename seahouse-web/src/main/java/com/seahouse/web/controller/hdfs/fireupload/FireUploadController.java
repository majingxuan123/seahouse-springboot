package com.seahouse.web.controller.hdfs.fireupload;

import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: FireUploadController</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2019/9/10</li>
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
 *
 */
@Controller
@RequestMapping("/fileUpload")
public class FireUploadController {


    @Value("${uploadsrc}")
    private String upload_Src;

    /**
     * 主页测试上传
     *
     * @param request
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "uploadTest", method = RequestMethod.POST)   //fileUpload/uploadTest
    public void fileUploadTest(HttpServletRequest request) throws IOException {
        //将请求强转成文件上传的请求
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        //存储文件的地址
        List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");

        File newFile = new File(upload_Src);
        if (!newFile.exists()) {
            newFile.mkdir();
        }

        for (MultipartFile multipartFile : files) {
            if (multipartFile.getBytes().length!=0){
                if (!StringUtil.isEmpty(multipartFile.getOriginalFilename())){
                    multipartFile.transferTo(new File(upload_Src, multipartFile.getOriginalFilename()));
                    System.out.println("上传文件" + multipartFile.getOriginalFilename());
                }
            }
        }
        System.out.println("success");
    }

}
