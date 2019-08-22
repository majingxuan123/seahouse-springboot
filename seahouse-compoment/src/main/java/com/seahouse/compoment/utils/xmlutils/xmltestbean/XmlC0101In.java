package com.seahouse.compoment.utils.xmlutils.xmltestbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
public class XmlC0101In {

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
    @XmlElement(name = "PARAM")
    private C0101In param;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public C0101In getParam() {
        return param;
    }

    public void setParam(C0101In param) {
        this.param = param;
    }

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
}
