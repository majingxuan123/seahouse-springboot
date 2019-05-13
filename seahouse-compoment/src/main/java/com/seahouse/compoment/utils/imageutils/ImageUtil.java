package com.seahouse.compoment.utils.imageutils;

import com.seahouse.compoment.utils.dateutils.DateUtil;
import com.seahouse.compoment.utils.numberutils.NumberUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 图片工具类
 */
@Component
public class ImageUtil {


    @Value("${ffmepg_src}")
    private String ffmpegpath;

    @Value("${picture_src}")
    private String picture_src;
    @Value("${picture_size}")
    private String picture_size;

    public String getPicture_src() {
        return picture_src;
    }

    public String getPicture_size() {
        return picture_size;
    }

    public String getFfmpegpath() {
        return ffmpegpath;
    }

    /**
     * 更改图片的尺寸
     *
     * @param src  旧图片地址
     * @param dest 新图片地址
     * @param w    宽度
     * @param h    高度
     * @throws Exception
     */
    public static void zoomImage(String src, String dest, int w, int h) throws Exception {
        double wr = 0, hr = 0;
        System.out.println("src===" + src);
        File srcFile = new File(src);
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(srcFile);
        wr = w * 1.0 / bufImg.getWidth();
        hr = h * 1.0 / bufImg.getHeight();
        if (w != 0 && h != 0) {
            //Image Itemp = bufImg.getScaledInstance(w, h, BufferedImage.TYPE_INT_ARGB);
            AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
            Image iTemp = ato.filter(bufImg, null);
            try {
                ImageIO.write((BufferedImage) iTemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            throw new NullPointerException("传入的高度与宽度不可为0");
        }

    }

    /**
     * @param src
     */
    public void deleteImage(String src) {
        File file = new File(src);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }


    /**
     * 对视频文件进行截图
     *
     * @param path
     * @return
     */
    public boolean processImg(String path) {
        File fi = new File(path);
        // asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv
        if (!(path.endsWith("asx") || path.endsWith("asf") || path.endsWith("mpg") || path.endsWith("wmv") || !
                path.endsWith("3gp") || path.endsWith("mp4") || !path.endsWith("mov") || path.endsWith("avi") || path.endsWith("flv"))) {
            return false;
        }
        List commend = new java.util.ArrayList();
        commend.add(getFfmpegpath());
        commend.add("-i");
        commend.add(path);
        commend.add("-y");
        commend.add("-f");
        commend.add("image2");
        // 添加参数＂-ss＂，该参数指定截取的起始时间
        commend.add("-ss");
        commend.add("38");
        // 添加参数＂-t＂，该参数指定持续时间
        commend.add("-t");
        // 添加持续时间为1毫秒
        commend.add("0.001");
        // 添加参数＂-s＂，该参数指定截取的图片大小
        commend.add("-s");
        commend.add(getPicture_size());
        System.out.println(fi.getPath());
        String randomNum = NumberUtil.getRandom(5);
        String dateStr = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
        String newImageFile = getPicture_src() + "/" + dateStr + randomNum + ".jpg";
        System.out.println(newImageFile);
        //一个随机的名字
        //todo: 此处信息应该存在数据库之中 以后才有渠道使用
        commend.add(newImageFile);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
