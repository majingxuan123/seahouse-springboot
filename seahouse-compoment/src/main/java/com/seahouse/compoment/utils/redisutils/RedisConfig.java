package com.seahouse.compoment.utils.redisutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * <ul>
 * <li>文件名称: RedisConfig</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/3/27 0027</li>
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
//@Configuration  //保证该类会被扫描到
//@ConfigurationProperties(prefix = "redis")   //读取前缀为 config 的内容
//@PropertySource("classpath:redis.properties")  //定义了要读取的properties文件的位置
//@Component
public class RedisConfig {
    @Value("${redis.ip}")
    public String redis_ip;

    @Value("${redis.port}")
    public int redis_port;

    @Value("${redis.password}")
    public String redis_password;

    @Value("${jedis.pool.maxActive}")
    public String redis_maxActive;

    @Value("${jedis.pool.maxIdle}")
    public String redis_maxIdle;

    @Value("${jedis.pool.maxWait}")
    public String redis_maxWait;

    @Value("${jedis.pool.testOnBorrow}")
    public String redis_testOnBorrow;

    @Value("${jedis.pool.testOnReturn}")
    public String redis_testOnReturn;

    public Jedis jedis;

    /**
     * 连接redis
     * @return  true连接成功    false:连接失败
     */
    public boolean connectRedis() {
        if (jedis == null) {
            try {
                //连接redis服务器，192.168.0.100:6379
                Jedis newJedis = new Jedis(redis_ip, redis_port);
                //权限认证
                newJedis.auth(redis_password);
                newJedis.connect();
                jedis = newJedis;
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }else {
            return true;
        }
    }


    /**
     * 返回连接到redis的Jedis对象
     *
     *
     * 使用完毕缓存之后 请执行closeJedisConnect()；
     *
     *
     * @return null   无法连接到redis
     */
    public Jedis getJedis() {
        if (jedis != null) {
            return jedis;
        } else {
            if (connectRedis()){
                return jedis;
            }else {
                return null;
            }
        }
    }
    /**
     * 关闭redis连接
     *
     * @return true: 关闭成功   false：关闭失败
     */
    public boolean closeJedisConnect() {
        jedis.disconnect();
        if (jedis == null) {
            return true;
        } else {
            return false;
        }

    }

}
