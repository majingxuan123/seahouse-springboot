package com.seahouse.web.controller.userdemo;

import com.seahouse.domain.entity.TUser;
import com.seahouse.domain.userdemo.User;
import com.seahouse.service.userdemo.impl.UserServiceDemo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: UserController</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2019/5/12</li>
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
@RequestMapping("/getuserdemo")
public class UserController {

    @Resource
    private UserServiceDemo UserServiceDemo;

    @RequestMapping("/{id}")
    public String  getUser(@PathVariable int id, Model model) {
        TUser tUser = UserServiceDemo .getUser(id);

        model.addAttribute("user", tUser);
        return "/pages/getuserdemo/detail";
    }

    @RequestMapping("/list")
    public String  listUser(Model model) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 9; i++) {
            User dto = new User();
            dto.setId((long) i);
            dto.setUsername("pepstack-" + i);
            dto.setAddress("Shanghai, China");
            dto.setAge(20 + i);
            userList.add(dto);
        }
        model.addAttribute("users", userList);
        return "/pages/getuserdemo/list";
    }

}
