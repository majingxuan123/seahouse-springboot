package com.seahouse.compoment.utils.videoutils;


import com.seahouse.compoment.utils.dateutils.DateUtil;
import com.seahouse.compoment.utils.numberutils.NumberUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class VideoUtil {

    @Value("${ffmepg_src}")
    private String ffmepgpath;

    @Value("${video_src}")
    private String video_Src;

    @Value("${picture_size}")
    private String picture_size;

    @Value("${picture_src}")
    private String picture_src;
    @Value("${mencoder_path}")
    private String mencoder_path;


    public String getMencoder_path() {
        return mencoder_path;
    }

    public String getPicture_src() {
        return picture_src;
    }

    public String getPicture_size() {
        return picture_size;
    }

    public String getVideo_Src() {
        return video_Src;
    }

    public String getFfmepgpath() {
        return ffmepgpath;
    }

    /**
     * 视频转码
     *
     * @param upFilePath 用于指定要转换格式的文件,要截图的视频源文件
     * @return 返回新文件的地址  返回值如果为空说明失败了
     * @throws Exception
     */
    public String processFlv(String upFilePath) throws Exception {
        // asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv
        if (!(upFilePath.endsWith("asx") || upFilePath.endsWith("asf") || upFilePath.endsWith("mpg") || upFilePath.endsWith("wmv") || !
                upFilePath.endsWith("3gp") || upFilePath.endsWith("mp4") || !upFilePath.endsWith("mov") || upFilePath.endsWith("avi") || upFilePath.endsWith("flv"))) {
            return "";
        }
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        // 添加转换工具路径
        convert.add(getFfmepgpath());
        // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add("-i");
        // 添加要转换格式的视频文件的路径
        convert.add(upFilePath);
        //指定转换的质量
        convert.add("-qscale");
        convert.add("6");
        //设置音频码率
        convert.add("-ab");
        convert.add("64");
        //设置声道数
        convert.add("-ac");
        convert.add("2");
        //设置声音的采样频率
        convert.add("-ar");
        convert.add("22050");
        //设置帧频
        convert.add("-r");
        convert.add("25");
        // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add("-y");
        String url = getVideo_Src() + DateUtil.dateToString(new Date(), "yyyyMMddHHmmss") + NumberUtil.getRandom(10) + ".flv";
        convert.add(url);
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(convert);
            builder.redirectErrorStream(true);
            builder.start();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return url;
    }

    /**
     * 视频转码 并且截图
     *
     * @param upFilePath 用于指定要转换格式的文件,要截图的视频源文件
     * @return 返回新文件的地址  返回值如果为空说明失败了
     * @throws Exception
     */
    public String[] executeCodecsAndScreenShut(String upFilePath) throws Exception {
        String[] strings = new String[2];
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        // 添加转换工具路径
        convert.add(getFfmepgpath());
        // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add("-i");
        // 添加要转换格式的视频文件的路径
        convert.add(upFilePath);
        //指定转换的质量
        convert.add("-qscale");
        convert.add("6");
        //设置音频码率
        convert.add("-ab");
        convert.add("64");
        //设置声道数
        convert.add("-ac");
        convert.add("2");
        //设置声音的采样频率
        convert.add("-ar");
        convert.add("22050");
        //设置帧频
        convert.add("-r");
        convert.add("25");
        // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add("-y");
        String url = getVideo_Src() + DateUtil.dateToString(new Date(), "yyyyMMddHHmmss") + NumberUtil.getRandom(10) + ".flv";
        strings[1] = url;
        convert.add(url);
        //下面是截图使用的
        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(getFfmepgpath());
        cutpic.add("-i");
        // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add(upFilePath);
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("-ss");
        // 添加起始时间为第17秒
        cutpic.add("17");
        // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("-t");
        // 添加持续时间为1毫秒
        cutpic.add("0.001");
        // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add("-s");
        // 添加截取的图片大小为350*240
        cutpic.add(getPicture_size());
        String pictureUrl = getPicture_src() + DateUtil.dateToString(new Date(), "yyyyMMddHHmmss") + NumberUtil.getRandom(6) + ".jpg";
        // 添加截取的图片的保存路径
        cutpic.add(pictureUrl);
        strings[2] = pictureUrl;
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(convert);
            builder.redirectErrorStream(true);
            builder.start();
            builder.command(cutpic);
            builder.redirectErrorStream(true);
//          如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
//          因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
        } catch (Exception e) {
            e.printStackTrace();
            return strings;
        }
        return strings;
    }


    /**
     * 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
     *
     * @param path 传入文件路径
     * @return
     */
    private String processAVI(String path) {
        List<String> commend = new ArrayList<String>();
        //mencoder路径
        commend.add(getMencoder_path());
        //需要转码的文件路径
        commend.add(path);
        commend.add("-oac");
        commend.add("lavc");
        commend.add("-lavcopts");
        commend.add("acodec=mp3:abitrate=64");
        commend.add("-ovc");
        commend.add("xvid");
        commend.add("-xvidencopts");
        commend.add("bitrate=600");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        String pathStr = getVideo_Src() + DateUtil.dateToString(new Date(), "yyyyMMddHHmmss") + NumberUtil.getRandom(10) + ".avi";
        commend.add(pathStr);
        try {
            //调用线程命令启动转码
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            return pathStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 执行转码  如果返回值为空 说明转码失败了
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public String processVideo(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("传入的文件不存在");
        }
        // asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv
        if (filePath.endsWith("asx") || filePath.endsWith("asf") || filePath.endsWith("mpg") || filePath.endsWith("wmv") || !
                filePath.endsWith("3gp") || filePath.endsWith("mp4") || !filePath.endsWith("mov") || filePath.endsWith("avi") || filePath.endsWith("flv")) {
            return processFlv(filePath);
            //如果这三种格式先转化成avi  在转化成  FLV
        } else if (filePath.endsWith("wmv9") || filePath.endsWith("rm") || filePath.endsWith("rmvb")) {
            String aviUrl = processAVI(filePath);
            return processFlv(aviUrl);
        } else {
            return "";
        }
    }


}
