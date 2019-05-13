package com.seahouse.compoment.utils.numberutils;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * <ul>
 * <li>文件名称: NumberUtil</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/3/14 0014</li>
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
@Component
public class NumberUtil {

    /**
     * 获取一个随机数
     *
     * @param number
     * @return
     */
    public static String getRandom(int number) {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < number; i++) {
            result = result * 10 + array[i];
        }
        String str = String.valueOf(result);
        if (str.length() < number) {
            str = getValueByAppendVal("0", str, "0", 6);
        }
        return str;
    }

    /**
     * 获取组装值
     *
     * @param cf        操作标志，右填充  1
     *                  左填充  0
     * @param val       操作值   备操作的对象
     * @param appendVal 填充值    用什么来填充
     * @param len       组装长度  一共多少位
     * @return
     * @throws Exception
     * @throws IndexOutOfBoundsException
     */
    public static String getValueByAppendVal(String cf, String val, String appendVal, int len) throws IndexOutOfBoundsException {
        StringBuffer sb = new StringBuffer();

        int val_len = val.length();  // 传人的值的长度

        if (val_len > len) {
            throw new IndexOutOfBoundsException(val + "的值超过限制长度" + len);
        }
        for (int i = 1; i <= (len - val_len); i++) {
            sb.append(appendVal);
        }

        if ("1".equals(cf)) {
            return val + sb.toString(); // 右填充
        } else if ("0".equals(cf)) {
            return sb.toString() + val; // 左填充
        } else {
            return val;
        }

    }
}
