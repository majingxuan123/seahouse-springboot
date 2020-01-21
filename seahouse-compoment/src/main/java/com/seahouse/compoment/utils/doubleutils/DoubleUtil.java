package com.seahouse.compoment.utils.doubleutils;

import java.math.BigDecimal;

/**
 * 处理DOUBLE类型的   加减乘除的工具类
 */
public class DoubleUtil {

    /**
     * 将传入的两个double类型相加
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double add(Double num1, Double num2) {
        return BigDecimal.valueOf(num1).add(BigDecimal.valueOf(num2)).doubleValue();
    }


    /**
     * 将传入的两个double类型相减
     * <p>
     * 第一个减去第二个
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double subtract(Double num1, Double num2) {
        return BigDecimal.valueOf(num1).subtract(BigDecimal.valueOf(num2)).doubleValue();
    }

    /**
     * 将传入的两个double类型相乘
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double multply(Double num1, Double num2) {
        return BigDecimal.valueOf(num1).multiply(BigDecimal.valueOf(num2)).doubleValue();
    }


    /**
     * 将传入的两个double类型相除
     * 第一个减去第二个
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double divide(Double num1, Double num2) {
        return BigDecimal.valueOf(num1).divide(BigDecimal.valueOf(num2)).doubleValue();
    }


}
