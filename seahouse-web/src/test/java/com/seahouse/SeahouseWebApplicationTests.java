package com.seahouse;

import com.seahouse.compoment.utils.dao.CommonDao;
import com.seahouse.compoment.utils.redisutils.RedisUtil;
import com.seahouse.domain.entity.TUser;
import com.seahouse.service.userdemo.UserServiceDemoBS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeahouseWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeahouseWebApplicationTests {

    @Resource
    private RedisUtil redisUtil;

    @Resource(name = "commonDaoImpl")
    private CommonDao commonDao;


    @Resource(name="userServiceDemoBSImpl")
    private UserServiceDemoBS UserServiceDemoBS;

    @Test
    public void contextLoads() {
        System.err.println("*********************************");
        TUser entityBySql = commonDao.getEntityBySQL("select * from T_USER where user_id = ? ", TUser.class,"1");
        System.out.println("我的生日是："+entityBySql.getBirthday());

        String seq_user_id = commonDao.getSequence("SEQ_USER_ID");
        System.out.println("getsequence====>"+seq_user_id);

        TUser entity = commonDao.getEntity(TUser.class, 1l);

        System.err.println("*********************************");
    }

}
