package com.seahouse.compoment.utils.codeutils;

/**
 * <ul>
 * <li>文件名称: CodeUtil</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/3/19 0019</li>
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
public class CodeUtil {

    /**
     * 判断字符串的编码格式
     *
     * @param str 传入字符串
     * @return 返回编码格式  如果为空  说明不是方法中的几种格式
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            //判断是不是GB2312
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                //是的话，返回“GB2312“，以下代码同理
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            //判断是不是ISO-8859-1
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            //判断是不是UTF-8
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            //判断是不是GBK
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        //如果都不是，说明输入的内容不属于常见的编码格式。
        return "";
    }

}
