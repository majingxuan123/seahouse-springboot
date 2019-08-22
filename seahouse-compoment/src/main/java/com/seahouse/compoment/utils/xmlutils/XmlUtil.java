package com.seahouse.compoment.utils.xmlutils;

import com.seahouse.compoment.utils.xmlutils.xmltestbean.C0101Out;
import com.seahouse.compoment.utils.xmlutils.xmltestbean.XmlC0101In;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>文件名称: XmlUtil</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/4/18 0018</li>
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
public class XmlUtil {

    /**
     * 将xml转化为 使用xml注解修饰过的javabean
     *
     * @param xml
     * @param T
     * @param <T>
     * @return
     * @throws JAXBException
     */
    public static <T> T XmlToJavaBean(String xml, Class<T> T) throws UnsupportedEncodingException, JAXBException {
        if (StringUtils.isEmpty(xml)) {
            return null;
        }
        //如果没有含有那两个注解直接报错
        xml = new String(xml.getBytes(), "UTF-8");
        if (!HasXmlAnnotation(T)) {
            return null;
        }
        JAXBContext jaxbC = JAXBContext.newInstance(T);
        Unmarshaller us = jaxbC.createUnmarshaller();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xml.getBytes());
        return (T) us.unmarshal(byteArrayInputStream);
    }


    /**
     * 将xml转化为 使用xml注解修饰过的javabean
     *
     * @param
     * @param
     * @param
     * @return
     * @throws JAXBException
     */
    public static String BeanToXml(Object o) {
        String string = null;
        try {
            //返回类
            Class clz = o.getClass();
            if (!HasXmlAnnotation(clz)) {
                throw new NullPointerException("XML实体类没有标注注解");
            }
            //返回值
            string = null;
            File file = new File("receive");
            JAXBContext context = JAXBContext.newInstance(clz);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(o, file);
            byte[] bytes = null;
            try {
                bytes = FileUtils.readFileToByteArray(file);
                string = new String(bytes, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            throw new NullPointerException("xml解析出错");
        }
        return string;
    }

    /**
     * 判断是否有
     * XmlAccessorType和XmlRootElement
     * 注解
     *
     * @param clz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static boolean HasXmlAnnotation(Class clz) {
        Annotation[] annotations = clz.getAnnotations();
        Map<Integer, String> map = new HashMap<>();
        map.put(XmlAccessorType.class.hashCode(), "");
        map.put(XmlRootElement.class.hashCode(), "");
        for (int i = 0; i < annotations.length; i++) {  //遍历循环
            if (map.get(annotations[i].annotationType().hashCode()) != null) {
                map.remove(annotations[i].annotationType().hashCode());
            }
        }
        if (map.size() == 0) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws JAXBException, IllegalAccessException, InstantiationException, ClassNotFoundException, UnsupportedEncodingException {


//        StringBuffer sb = new StringBuffer();
//        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
//        sb.append("<REQUEST>");
//        sb.append("<MSGFMT>HIS</MSGFMT>");
//        sb.append("<REQUEST_SN>2198159</REQUEST_SN>");
//        sb.append("<BAC060>C0101In</BAC060>");
//        sb.append("<AKB020>44445</AKB020>");
//        sb.append("<ZKE212>11</ZKE212>");
//        sb.append("<ZAE142>NULL</ZAE142>");
//        sb.append("<ZKE369>1</ZKE369>");
//        sb.append("<USERID>99_9028</USERID>");
//        sb.append("<PASSWD>99_9028</PASSWD>");
//        sb.append("<PARAM>");
//        sb.append("<ZKE737>123</ZKE737>");
//        sb.append("<ZKE738>XUTest001</ZKE738>");
//        sb.append("<ZKE739>20190213095221</ZKE739>");
//        sb.append("<AAC058>01</AAC058>");
//        sb.append("<AAC147>360424199101066734</AAC147>");
//        sb.append("<AAC003>zzh</AAC003>");
//        sb.append("<AAC004>1</AAC004>");
//        sb.append("<AAC005>1</AAC005>");
//        sb.append("<AAC006>19910106</AAC006>");
//        sb.append("<AAE006>新余</AAE006>");
//        sb.append("<AAE005>13333333333</AAE005>");
//        sb.append("<ZDF002>11</ZDF002>");
//        sb.append("<ZDF003>ceshi</ZDF003>");
//        sb.append("<ZDF005>20190213110139</ZDF005>");
//        sb.append("</PARAM>");
//        sb.append("</REQUEST>");
//
//
//        XmlC0101In xmlC0101 = XmlToJavaBean(sb.toString(), XmlC0101In.class);
//
//
//        StringBuffer sb1 = new StringBuffer();
//        sb1.append("<?xml version='1.0' encoding='UTF-8'?>");
//        sb1.append("<RESPONSE> ");
//        sb1.append("<MSGFMT>HIS</MSGFMT>  ");
//        sb1.append("<REQUEST_SN>1846427</REQUEST_SN>  ");
//        sb1.append("<BAC060>C0101</BAC060>  ");
//        sb1.append("<AKB020>020302</AKB020>  ");
//        sb1.append("<ZKE212>11</ZKE212>  ");
//        sb1.append("<ZAE142>NULL</ZAE142>  ");
//        sb1.append("<ZKE369>1</ZKE369>  ");
//        sb1.append("<USERID>99_9028</USERID>  ");
//        sb1.append("<PASSWD>99_9028</PASSWD>  ");
//        sb1.append("<RETURN_CODE>0000</RETURN_CODE>  ");
//        sb1.append("<RETURN_MSG>注册成功！</RETURN_MSG>  ");
//        sb1.append("<RESULTSET></RESULTSET> ");
//        sb1.append("</RESPONSE>");
//        C0101Out c0101Out = XmlToJavaBean(sb1.toString(), C0101Out.class);
//        String str = BeanToXml(c0101Out);
//        System.out.println(str);

    }


}
