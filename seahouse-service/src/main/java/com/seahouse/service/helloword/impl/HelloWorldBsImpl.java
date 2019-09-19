package com.seahouse.service.helloword.impl;

import com.seahouse.compoment.utils.dao.CommonDao;
import com.seahouse.compoment.utils.redisutils.RedisUtil;
import com.seahouse.domain.entity.TUser;
import com.seahouse.service.helloword.HelloWorldBS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * <ul>
 * <li>文件名称: HelloWorldBSImpl</li>
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
//加上注解后   public方法全部有事务
@Transactional
@Service
public class HelloWorldBsImpl implements HelloWorldBS {

    @Resource
    private RedisUtil redisUtil;

    @Resource(name = "commonDaoImpl")
    private CommonDao commonDao;


    @Override
    public void sayHello() {

        if (redisUtil.get("hello") == null) {
            redisUtil.set("hello", "hello world");
        }

        System.out.println(redisUtil.get("hello").toString());
    }

    @Override
    public void sayHelloJdbc() {
        TUser entityBySql = commonDao.getEntityBySQL("select * from T_USER where user_id = ? ", TUser.class,"1");
        System.out.println("我的生日是："+entityBySql.getBirthday());

        String seq_user_id = commonDao.getSequence("SEQ_USER_ID");
        System.out.println("getsequence====>"+seq_user_id);

    }
}
