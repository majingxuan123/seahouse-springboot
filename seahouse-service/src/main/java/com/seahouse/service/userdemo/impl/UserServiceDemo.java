package com.seahouse.service.userdemo.impl;

import com.seahouse.compoment.utils.dao.CommonDao;
import com.seahouse.domain.entity.TUser;
import com.seahouse.domain.userdemo.User;
import com.seahouse.service.userdemo.UserServiceDemoBS;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <ul>
 * <li>文件名称: UserServiceDemo</li>
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
@Service
public class UserServiceDemo implements UserServiceDemoBS {

    @Resource(name="commonDaoImpl")
    private CommonDao commonDao;

    @Override
    public TUser getUser(int id) {
        TUser tUser = commonDao.getEntityByid(TUser.class,Long.valueOf(id));
        return tUser;
    }
}
