package com.seahouse.compoment.utils.xmlutils.xmltestbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <ul>
 * <li>文件名称: XmlC0101Out</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2019/7/25</li>
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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
public class XmlC0101Out {

    @XmlElement(name = "MSGFMT")
    private String msgfmt;
    @XmlElement(name = "REQUEST_SN")
    private String request_sn;
    @XmlElement(name = "BAC060")
    private String bac060;
    @XmlElement(name = "AKB020")
    private String AKB020;
    @XmlElement(name = "ZKE212")
    private String zke212;
    @XmlElement(name = "ZAE142")
    private String ZAE142;
    @XmlElement(name = "ZKE369")
    private String zke369;
    @XmlElement(name = "USERID")
    private String userid;
    @XmlElement(name="PASSWD")
    private String password;
    @XmlElement(name="RETURN_CODE")
    private String return_code;
    @XmlElement(name="RETURN_MSG")
    private String return_msg;

    public String getMsgfmt() {
        return msgfmt;
    }

    public void setMsgfmt(String msgfmt) {
        this.msgfmt = msgfmt;
    }

    public String getRequest_sn() {
        return request_sn;
    }

    public void setRequest_sn(String request_sn) {
        this.request_sn = request_sn;
    }

    public String getBac060() {
        return bac060;
    }

    public void setBac060(String bac060) {
        this.bac060 = bac060;
    }

    public String getAKB020() {
        return AKB020;
    }

    public void setAKB020(String AKB020) {
        this.AKB020 = AKB020;
    }

    public String getZke212() {
        return zke212;
    }

    public void setZke212(String zke212) {
        this.zke212 = zke212;
    }

    public String getZAE142() {
        return ZAE142;
    }

    public void setZAE142(String ZAE142) {
        this.ZAE142 = ZAE142;
    }

    public String getZke369() {
        return zke369;
    }

    public void setZke369(String zke369) {
        this.zke369 = zke369;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
}
