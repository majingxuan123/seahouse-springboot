package com.seahouse.compoment.utils.ftputils;

/**
 * <ul>
 * <li>文件名称: Ftp_param</li>
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
public class Ftp_param {
    /**
     * FTP上传IP
     */
    private String upFtpServerIp;
    /**
     * FTP上传用户名
     */
    private String upFtpUsername;
    /**
     * FTP上传密码
     */
    private String upFtpPassword;
    /**
     * 上传至FTP的本地文件
     */
    private String upLocalFile;
    /**
     * FTP上传路径  FTP指定文件夹路径
     */
    private String upFtpPath;
    /**
     * FTP上传文件取名  FTP指定文件名
     */
    private String upFileName;
    /**
     * FTP下载文件取名  FTP指定文件名
     */
    private String downFileName;

    /**
     * ftp下载的文件路径
     */
    private String downFilePath;
    /**
     * FTP上传文件格式编码 默认上传文件格式编码为GBK
     */
    private String upFileEncoding = "UTF-8";
    /**
     * FTP上传缓存大小  默认缓存大小设置为1024
     */
    private int upBufferSize = 1024;
    /**
     * FTP的IP
     */
    private String downFtpServerIp;
    /**
     * FTP用户名
     */
    private String downFtpUsername;
    /**
     * FTP密码
     */
    private String downFtpPassword;
    /**
     * FTP下载到本地的文件名（含路径）
     */
    private String downLocalFile;
    /**
     * FTP下载到本地的文件路径
     */
    private String downLocalPath;
    /**
     * FTP下载的文件名（含路径）
     */
    private String downFtpFile;
    /**
     * FTP下载的文件格式编码  默认下载文件格式编码为GBK
     */
    private String downFileEncoding = "UTF-8";
    /**
     * FTP下载的缓存大小  默认缓存大小设置为1024
     */
    private int downBuffersize = 1024;

    /**
     * FTP连接端口  默认FTP连接端口为21
     */
    private String ftpPort = "21";

    /**
     * @param upftpserverip FTP 的IP地址
     * @param upftpusername FTP 账号
     * @param upftppassword FTP密码
     */
    public Ftp_param(String upftpserverip, String upftpusername, String upftppassword) {
        this.upFtpServerIp = upftpserverip;
        this.upFtpUsername = upftpusername;
        this.upFtpPassword = upftppassword;
    }

    public String getUpFtpServerIp() {
        return upFtpServerIp;
    }

    public void setUpFtpServerIp(String upFtpServerIp) {
        this.upFtpServerIp = upFtpServerIp;
    }

    public String getUpFtpUsername() {
        return upFtpUsername;
    }

    public void setUpFtpUsername(String upFtpUsername) {
        this.upFtpUsername = upFtpUsername;
    }

    public String getUpFtpPassword() {
        return upFtpPassword;
    }

    public void setUpFtpPassword(String upFtpPassword) {
        this.upFtpPassword = upFtpPassword;
    }

    public String getUpLocalFile() {
        return upLocalFile;
    }

    public void setUpLocalFile(String upLocalFile) {
        this.upLocalFile = upLocalFile;
    }

    public String getUpFtpPath() {
        return upFtpPath;
    }

    public void setUpFtpPath(String upFtpPath) {
        this.upFtpPath = upFtpPath;
    }

    public String getUpFileName() {
        return upFileName;
    }

    public void setUpFileName(String upFileName) {
        this.upFileName = upFileName;
    }

    public String getDownFileName() {
        return downFileName;
    }

    public void setDownFileName(String downFileName) {
        this.downFileName = downFileName;
    }

    public String getDownFilePath() {
        return downFilePath;
    }

    public void setDownFilePath(String downFilePath) {
        this.downFilePath = downFilePath;
    }

    public String getUpFileEncoding() {
        return upFileEncoding;
    }

    public void setUpFileEncoding(String upFileEncoding) {
        this.upFileEncoding = upFileEncoding;
    }

    public int getUpBufferSize() {
        return upBufferSize;
    }

    public void setUpBufferSize(int upBufferSize) {
        this.upBufferSize = upBufferSize;
    }

    public String getDownFtpServerIp() {
        return downFtpServerIp;
    }

    public void setDownFtpServerIp(String downFtpServerIp) {
        this.downFtpServerIp = downFtpServerIp;
    }

    public String getDownFtpUsername() {
        return downFtpUsername;
    }

    public void setDownFtpUsername(String downFtpUsername) {
        this.downFtpUsername = downFtpUsername;
    }

    public String getDownFtpPassword() {
        return downFtpPassword;
    }

    public void setDownFtpPassword(String downFtpPassword) {
        this.downFtpPassword = downFtpPassword;
    }

    public String getDownLocalFile() {
        return downLocalFile;
    }

    public void setDownLocalFile(String downLocalFile) {
        this.downLocalFile = downLocalFile;
    }

    public String getDownLocalPath() {
        return downLocalPath;
    }

    public void setDownLocalPath(String downLocalPath) {
        this.downLocalPath = downLocalPath;
    }

    public String getDownFtpFile() {
        return downFtpFile;
    }

    public void setDownFtpFile(String downFtpFile) {
        this.downFtpFile = downFtpFile;
    }

    public String getDownFileEncoding() {
        return downFileEncoding;
    }

    public void setDownFileEncoding(String downFileEncoding) {
        this.downFileEncoding = downFileEncoding;
    }

    public int getDownBuffersize() {
        return downBuffersize;
    }

    public void setDownBuffersize(int downBuffersize) {
        this.downBuffersize = downBuffersize;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }
}
