package com.seahouse.imageutils;

/**
 * <ul>
 * <li>文件名称: FontText</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2019/5/30</li>
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
public class FontText {
    private String text;

    private int wm_text_pos;

    private String wm_text_color;

    private Integer wm_text_size;

    private String wm_text_font;//字体  “黑体，Arial”

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getWm_text_pos() {
        return wm_text_pos;
    }

    public void setWm_text_pos(int wm_text_pos) {
        this.wm_text_pos = wm_text_pos;
    }

    public String getWm_text_color() {
        return wm_text_color;
    }

    public void setWm_text_color(String wm_text_color) {
        this.wm_text_color = wm_text_color;
    }

    public Integer getWm_text_size() {
        return wm_text_size;
    }

    public void setWm_text_size(Integer wm_text_size) {
        this.wm_text_size = wm_text_size;
    }

    public String getWm_text_font() {
        return wm_text_font;
    }

    public void setWm_text_font(String wm_text_font) {
        this.wm_text_font = wm_text_font;
    }

    public FontText(String text, int wm_text_pos, String wm_text_color,
                    Integer wm_text_size, String wm_text_font) {
        super();
        this.text = text;
        this.wm_text_pos = wm_text_pos;
        this.wm_text_color = wm_text_color;
        this.wm_text_size = wm_text_size;
        this.wm_text_font = wm_text_font;
    }

    public FontText() {
    }
}
