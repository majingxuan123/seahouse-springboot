package com.seahouse.compoment.utils.fileutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 定义项目用到的全局参数，
 */
@Component
public class ProjectConfig {
    //从服务器下载  文件地址

    public String DOWNLOADSRC;
    //上传到服务器  文件地址
    @Value("${uploadsrc}")
    public String UPLOADSRC;
    //aes秘钥  必须16位
    @Value("${aes_Key}")
    public String AES_KEY;
    //des秘钥  必须8位
    @Value("${des_Key}")
    public String DES_KEY;
    //ffmpeg地址      asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv
    // ffmpeg.exe的目录
    @Value("${ffmepg_src}")
    public String FFMPEGPATH;
    @Value("${mencoder_path}")
    public String MENCODER_PATH;
    //视频截屏存放地址
    @Value("${picture_src}")
    public String SCREENSHUTIMAGE_PATH;
    //转码后的文件存放地址
    @Value("${video_src}")
    public String video_src;
    //视频截图的大小
    @Value("${picture_size}")
    public String PICTURE_SIZE;

    public String getDOWNLOADSRC() {
        return DOWNLOADSRC;
    }

    public void setDOWNLOADSRC(String DOWNLOADSRC) {
        this.DOWNLOADSRC = DOWNLOADSRC;
    }

    public String getUPLOADSRC() {
        return UPLOADSRC;
    }

    public void setUPLOADSRC(String UPLOADSRC) {
        this.UPLOADSRC = UPLOADSRC;
    }

    public String getAES_KEY() {
        return AES_KEY;
    }

    public void setAES_KEY(String AES_KEY) {
        this.AES_KEY = AES_KEY;
    }

    public String getDES_KEY() {
        return DES_KEY;
    }

    public void setDES_KEY(String DES_KEY) {
        this.DES_KEY = DES_KEY;
    }

    public String getFFMPEGPATH() {
        return FFMPEGPATH;
    }

    public void setFFMPEGPATH(String FFMPEGPATH) {
        this.FFMPEGPATH = FFMPEGPATH;
    }

    public String getMENCODER_PATH() {
        return MENCODER_PATH;
    }

    public void setMENCODER_PATH(String MENCODER_PATH) {
        this.MENCODER_PATH = MENCODER_PATH;
    }

    public String getSCREENSHUTIMAGE_PATH() {
        return SCREENSHUTIMAGE_PATH;
    }

    public void setSCREENSHUTIMAGE_PATH(String SCREENSHUTIMAGE_PATH) {
        this.SCREENSHUTIMAGE_PATH = SCREENSHUTIMAGE_PATH;
    }

    public String getVideo_src() {
        return video_src;
    }

    public void setVideo_src(String video_src) {
        this.video_src = video_src;
    }

    public String getPICTURE_SIZE() {
        return PICTURE_SIZE;
    }

    public void setPICTURE_SIZE(String PICTURE_SIZE) {
        this.PICTURE_SIZE = PICTURE_SIZE;
    }
//    @PostConstruct
//    public static void init() {
//        InputStream inStream = Aes_Util.class.getClassLoader().getResourceAsStream("project.properties");
//        Properties prop = new Properties();
//        try {
//            prop.load(inStream);
//            PICTURE_SIZE = prop.getProperty("picture_size");
//            SCREENSHUTIMAGE_PATH = prop.getProperty("picture_src");
//            video_src = prop.getProperty("video_src");
//            DOWNLOADSRC = prop.getProperty("downloadSrc");
//            UPLOADSRC = prop.getProperty("uploadSrc");
//            AES_KEY = prop.getProperty("aes_Key");
//            DES_KEY = prop.getProperty("des_Key");
//            FFMPEGPATH = System.getProperty("user.dir") + prop.getProperty("ffmepg_src");
//            MENCODER_PATH = System.getProperty("user.dir") + prop.getProperty("mencoder_path");
//            File pictureDir = new File(SCREENSHUTIMAGE_PATH);
//            File videoDir = new File(video_src);
//            if (!pictureDir.exists()) {
//                pictureDir.mkdirs();
//            }
//            if (!videoDir.exists()) {
//                videoDir.mkdirs();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


}
