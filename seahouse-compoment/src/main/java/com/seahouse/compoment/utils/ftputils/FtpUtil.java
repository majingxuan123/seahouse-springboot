package com.seahouse.compoment.utils.ftputils;

import com.seahouse.compoment.utils.fileutils.FileUtils_sh;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <ul>
 * <li>文件名称: FtpTool</li>
 * <li>文件描述: FTP上传下载</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>公   司: 厦门市中软件科技有限公司</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>创建日期:2014-9-22 </li>
 * </ul>
 * <ul>
 * <li>修改记录: </li>
 * <li>版 本 号: </li>
 * <li>修改日期: </li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */

@Component
public class FtpUtil {

    private static FTPClient ftpClient = null;

    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 连接到服务器
     *
     * @return true 连接服务器成功，false 连接服务器失败
     */
    public static boolean connectServer(Ftp_param ftp_param) {
        boolean flag = true;
        if (ftpClient == null) {
            int reply;
            try {
                ftpClient = new FTPClient();
                ftpClient.setControlEncoding(ftp_param.getUpFileEncoding());
                ftpClient.configure(getFtpConfig());
                ftpClient.connect(ftp_param.getUpFtpServerIp());
                ftpClient.login(ftp_param.getUpFtpUsername(), ftp_param.getUpFtpPassword());
                reply = ftpClient.getReplyCode();
                ftpClient.setDataTimeout(120000);
                System.out.println("----------  开始连接---------");
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    System.err.println("FTP server refused connection.");
                    // logger.debug("FTP 服务拒绝连接！");
                    flag = false;
                }
            } catch (SocketException e) {
                flag = false;
                e.printStackTrace();
                System.err.println("登录ftp服务器【" + ftp_param.getUpFtpServerIp() + "】失败,连接超时！");
                // logger.debug("登录ftp服务器【" Ip "】失败");
            } catch (IOException e) {
                flag = false;

                e.printStackTrace();
                System.err.println("登录ftp服务器【" + ftp_param.getUpFtpServerIp() + "】失败，FTP服务器无法打开！");
                // logger.debug("登录ftp服务器【" Ip "】失败");
            }

        }
        return flag;
    }


    /**
     * 设置FTP客服端的配置--一般可以不设置
     *
     * @return
     */
    private static FTPClientConfig getFtpConfig() {
        FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
        return ftpConfig;
    }


    /**
     * FTP上传单个文件
     *
     * @param file      上传文件
     * @param ftp_param 上传文件ftp参数
     *                  <p>
     *                  ftp保存上传文件的路径    Upftppath
     *                  ftp保存文件名           Upfilename
     * @return 上传成功或失败
     */
    public static boolean fileUpload(File file, Ftp_param ftp_param) {
        ByteArrayInputStream bais = null;
        try {
            int reply;
            ftpClient = new FTPClient();
            ftpClient.setControlEncoding(ftp_param.getUpFileEncoding());
            ftpClient.configure(getFtpConfig());
            ftpClient.connect(ftp_param.getUpFtpServerIp(), Integer.valueOf(ftp_param.getFtpPort()));
            ftpClient.login(ftp_param.getUpFtpUsername(), ftp_param.getUpFtpPassword());
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);

            byte[] b = FileUtils_sh.getBytesFromFile(file);
            bais = new ByteArrayInputStream(b);

            if (!ftpClient.changeWorkingDirectory(ftp_param.getUpFtpPath())) {// 如果不能进入dir下，说明此目录不存在！

                if (!makeDirectory(ftp_param.getUpFtpPath())) {

                    logger.error("创建文件目录【" + ftp_param.getUpFtpPath() + "】 失败！");
                }
            }

            //changeWorkingDirectory("/",ftp_param);// 回到FTP根目录
            //设置上传目录
            boolean isUpFtpPath = ftpClient.changeWorkingDirectory(ftp_param.getUpFtpPath());
            if (!isUpFtpPath) {
                logger.error("找不到FTP上传目录");
                throw new IOException("找不到上传目录");
            }
            //上传文件
            boolean isStroe = ftpClient.storeFile(ftp_param.getUpFileName(), bais);
            if (isStroe) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            IOUtils.closeQuietly(bais);
            try {
                //closeConnect();
                if (null != ftpClient) {
                    ftpClient.disconnect();
                    System.out.println("ftp关闭成功");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }
        }
    }


    /**
     * 在服务器上创建一个文件夹
     *
     * @param dir 文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>...
     */
    public static boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                System.out.println("创建目录---" + dir + "成功");
            } else {
                System.out.println("创建目录---" + dir + "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据上传目录 获取目录下所有的文件名
     * <p>
     * 默认格式是UTF-8
     *
     * @param ftp_param Upftppath  跳转到指定的目录下
     * @return
     */
    public static HashMap<String, String> getFilesMap(Ftp_param ftp_param) {
        HashMap<String, String> picShowMap = new HashMap<String, String>();
        try {
            //connectServer(ftp_param);
            int reply;
            ftpClient = new FTPClient();
            ftpClient.setControlEncoding(ftp_param.getUpFileEncoding());
            ftpClient.setDefaultPort(Integer.valueOf(ftp_param.getFtpPort()));
            ftpClient.configure(getFtpConfig());
            ftpClient.connect(ftp_param.getUpFtpServerIp());
            ftpClient.login(ftp_param.getUpFtpUsername(), ftp_param.getUpFtpPassword());
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
            //设置下载目录  跳转到指定的FTP目录之下
            boolean isUpFtpPath = ftpClient.changeWorkingDirectory(ftp_param.getUpFtpPath());
            if (!isUpFtpPath) {
                logger.error("获取文件目录失败");
                throw new IOException("获取文件目录失败");
            }
            String[] ftpFileName = ftpClient.listNames();
            for (int i = 0; i < ftpFileName.length; i++) {
                String name = ftpFileName[i];
                String picPath = URLEncoder.encode(name, "UTF-8");
                //下一行的ftpToolParams.getDownlocalpath() 的路径是获文件夹路径，且已经被URLEncoder转换了的路径
                if (!StringUtils.isEmpty(ftp_param.getDownLocalPath())) {
                    picShowMap.put(String.valueOf((i + 1)), ftp_param.getDownLocalPath() + picPath);
                }
                picShowMap.put(String.valueOf((i + 1)), picPath);
            }
        } catch (IOException ioe) {
            logger.error("获取文件失败");
            ioe.printStackTrace();
        } finally {
            try {
                if (null != ftpClient) {
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
        return picShowMap;
    }

    /**
     * 下载ftp上的文件
     *
     * @param ftp_param ftpToolParams包含的参数有：
     *                  <p>
     *                  端口                      ftpport
     *                  ftp（下载目标）文件路径    downfilepath
     *                  ftp（下载目标）文件名      downfilename
     *                  保存到本地文件的路径       getDownlocalpath
     *                  字符编码                  upfileencoding
     * @return 下载成功或者失败
     * @throws Exception 读取文件时，当文件不存在会报空指针异常，还可能会报IOException
     */
    public static boolean downloadFtpFile(Ftp_param ftp_param) throws Exception {
        // 声明该方法返回变量
        // 初始化FTPClient对象
        InputStream ins = null;
        try {
            // 连接ftp服务器
            ftpClient.connect(ftp_param.getUpFtpServerIp(), Integer.valueOf(ftp_param.getFtpPort()));
            // 登陆服务器
            ftpClient.login(ftp_param.getUpFtpUsername(), ftp_param.getUpFtpPassword());
            // 跳转到指定目录
            if (!StringUtils.isEmpty(ftp_param.getDownFilePath())) {
                ftpClient.changeWorkingDirectory(ftp_param.getDownFilePath());
            }
            // 使用apache的retrieveFileStream方法读取指定文件
            ins = ftpClient.retrieveFileStream(ftp_param.getUpFileName());

            File file = new File(ftp_param.getDownLocalPath() + ftp_param.getDownFileName());

            if (file.exists()) {
                //将流写入文件
                boolean createFileFlag = FileUtils_sh.inputstreamtofile(ins, file);
                if (createFileFlag) {
                    System.out.println("文件下载完成" + ftp_param.getDownLocalPath() + ftp_param.getDownFileName());
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new FileNotFoundException("没有找到该文件");
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (null != ins) {
                    ins.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件的instream
     *
     * @param ftp_param ftpToolParams包含的参数有：
     *                  <p>
     *                  端口                      ftpport
     *                  ftp（下载目标）文件路径    downfilepath
     *                  ftp（下载目标）文件名      downfilename
     *                  保存到本地文件的路径       getDownlocalpath
     *                  字符编码                  upfileencoding
     * @return 流  如果为空就是没有找到文件
     */
    public static InputStream getFileInputStream(Ftp_param ftp_param) {
        // 声明该方法返回变量
        // 初始化FTPClient对象
        InputStream ins = null;
        try {
            // 连接ftp服务器
            ftpClient.connect(ftp_param.getUpFtpServerIp(), Integer.valueOf(ftp_param.getFtpPort()));
            // 登陆服务器
            ftpClient.login(ftp_param.getUpFtpUsername(), ftp_param.getUpFtpPassword());
            // 跳转到指定目录
            if (!StringUtils.isEmpty(ftp_param.getDownFilePath())) {
                ftpClient.changeWorkingDirectory(ftp_param.getDownFilePath());
            }
            // 使用apache的retrieveFileStream方法读取指定文件
            ins = ftpClient.retrieveFileStream(ftp_param.getUpFileName());
            return ins;
        } catch (IOException e) {
            e.printStackTrace();
            return ins;
        }
    }

    /**
     * 关闭ftp连接 以节约资源
     *
     * @throws IOException
     */
    public void ftpDisConnect() throws IOException {
        ftpClient.disconnect();
    }


    public static void main(String[] args) throws UnsupportedEncodingException {

        Ftp_param ftp_param = new Ftp_param("192.168.1.99", "yfzx", "yfzx");
//
////      上传根目录
//        ftp_param.setUpftppath("/");
////      下载根目录
//        ftp_param.setDownlocalpath("d:/");
//        //下载文件的名字
//        ftp_param.setDownFileName("test.txt");
//        //ftp端口
//        ftp_param.setFtpport("21");
        //下载的编码格式

        //测试是否连接成功
        boolean b = connectServer(ftp_param);

        System.out.println(b);

//        //上传要设置上传文件的名字
//        File file = new File("d:/hbkq.log");
//        ftp_param.setUpfilename(file.getName());
//        boolean b2 = fileUpload(file,ftp_param);
//        System.out.println("--------------"+b2);
        //---------------------------------------------------------------------------------------

//----------------------------------------------------------------------------
        HashMap<String, String> map = getFilesMap(ftp_param);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println(entry.getKey() + ":" + URLDecoder.decode(entry.getValue(), "UTF-8"));
            // it.remove(); 删除元素
        }
//--------------------------------------------


//
//        try {
//            System.out.println(downloadFtpFile(ftp_param));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }

}
