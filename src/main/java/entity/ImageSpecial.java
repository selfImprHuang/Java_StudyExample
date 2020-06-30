package entity;

import java.awt.*;
import java.io.File;
import java.util.Map;

public class ImageSpecial {


    // 常量字体大小
    // 大字体
    private final static int LARGESIZE = 100;
    // 普通字体
    private final static int NORMALSIZE = 50;
    // 小字体
    public final static int SMALLSIZE = 30;

    // 段间距
    private final static int SECTSIZE = 80;

    // 文字区边距
    private final static int PADDING = 100;



    //----------------------------------上述为常量区----------------------------------

    public final static Color DEAUFT_COLOR = Color.WHITE;

    /**
     * 连续书写时的记录位
     */
    private int pointIndex;

    /**
     *  title书写之后的记录位
     */
    private int pointOnTitle;

    /**
     * 当前第几页
     */
    private int indexPage = 1;

    private Map<Integer, String> map;
    /**
     * 内容区字体大小
     */
    private int contentFontSize = NORMALSIZE;
    /**
     * 标题字体大小
     */
    private int titleFontSize = LARGESIZE;
    /**
     * 段间距
     */
    private int secttSize = SECTSIZE;
    /**
     * 文本域边距大小
     */
    private int contentPadding = PADDING;
    /**
     * 标题字体
     */
    private String titleFontStyle;
    /**
     * 标题字体粗细斜体设置
     *
     */
    private int titleFontBlod;
    /**
     * 内容字体粗细斜体设置
     */
    private int contentFontBlod;
    /**
     * 内容字体
     */
    private String contentFontStyle;
    /**
     * 画笔颜色
     */

    private Color color;
    /**
     * 背景图片
     */
    private File bgFile;

    /**
     * 设置当前书写的像素位子
     */
    public  int setPointIndex(int increment) {
        pointIndex += increment;
        return pointIndex;
    }

    public String getTitleFontStyle() {
        return titleFontStyle;
    }



    /**
     * 设置字体类型
     */
    public void setTitleFontStyle(String titleFontStyle) {
        this.titleFontStyle = titleFontStyle;
    }

    public int getTitleFontBlod() {
        return titleFontBlod;
    }

    /**
     * 标题字体粗细斜体设置
     *
     */
    public void setTitleFontBlod(int titleFontBlod) {
        this.titleFontBlod = titleFontBlod;
    }

    public int getContentFontBlod() {
        return contentFontBlod;
    }

    /**
     * 内容字体粗细斜体设置
     * 将同时设置正文与尾款
     */
    public void setContentFontBlod(int contentFontBlod) {
        this.contentFontBlod = contentFontBlod;
    }

    public String getContentFontStyle() {
        return contentFontStyle;
    }

    public void setContentFontStyle(String contentFontStyle) {
        this.contentFontStyle = contentFontStyle;
    }

    /**
     * 设置背景图
     */
    public void setBgFile(File bgFile) {
        this.bgFile = bgFile;
    }

    /**
     * 设置画笔颜色 默认白色
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * 设置内容区字体大小 默认NORMALSIZE = 50
     */
    public void setContentFontSize(int contentFontSize) {
        this.contentFontSize = contentFontSize;
    }

    /**
     * 设置标题字体大小 默认LARGESIZE = 100
     */
    public void setTitleFontSize(int titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    /**
     * 设置段间距 默认SECTSIZE = 70
     */
    public void setSecttSize(int secttSize) {
        this.secttSize = secttSize;
    }

    /**
     * 设置文本边距 默认PADDING70
     */
    public void setContentPadding(int contentPadding) {
        this.contentPadding = contentPadding;
    }

    public int getPointIndex() {
        return pointIndex;
    }


    public int getPointOnTitle() {
        return pointOnTitle;
    }

    public void setPointOnTitle(int pointOnTitle) {
        this.pointOnTitle = pointOnTitle;
    }

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    public int getContentFontSize() {
        return contentFontSize;
    }

    public int getTitleFontSize() {
        return titleFontSize;
    }

    public int getSecttSize() {
        return secttSize;
    }

    public int getContentPadding() {
        return contentPadding;
    }

    public Color getColor() {
        return color;
    }

    public File getBgFile() {
        return bgFile;
    }
}
