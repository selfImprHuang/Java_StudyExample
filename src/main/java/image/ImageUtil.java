package image;


import entity.ImageSpecial;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 文字写入图层背景工具类 
 *
 * 内容过多自动分页，生成的图片名 为源文件名加上序号 
 *
 * 落款自动写入格式 
 *
 * 过滤连续出现的换行符 
 *
 * 设置基本属性，调用compound方法即可 
 * @author 志军
 */
public class ImageUtil {

    private ImageSpecial imageSpecial = new ImageSpecial();

    public ImageSpecial getImageSpecial() {
        return imageSpecial;
    }

    public void setImageSpecial(ImageSpecial imageSpecial) {
        this.imageSpecial = imageSpecial;
    }

    /*
     * 备用方法，形式如段落 
     * public void drawTitle(Font font, int width, String title, Graphics2D g) { 
        AttributedString as = null; 
        // title = ToSBC(title); 
        // TODO 
        int fullLen = (width - contentPadding * 2) / titleFontSize;// 一行的最多容量 
        Map<Integer, String> map = rowAttr(title, fullLen); 
        Object[] keys = map.keySet().toArray(); 
        String tempStr = ""; 
        if (keys.length == 1) {// 只有一行 
            tempStr = map.get(0); 
            as = new AttributedString(title); 
            as.addAttribute(TextAttribute.FONT, font); 
            g.drawString(as.getIterator(), width / 2 - tempStr.length() / 2 
                    * titleFontSize, setPointIndex(titleFontSize + secttSize)); 
        } else { 
            int sum = 0; 
            int writeLen = keys.length; 
            for (int i = 0; i < writeLen - 1; i++) { 
                tempStr = map.get(keys[i]); 
                as = new AttributedString(tempStr); 
                as.addAttribute(TextAttribute.FONT, font); 
                int sl = tempStr.length(); 
                g.drawString(as.getIterator(), width / 2 - sl / 2 
                        * titleFontSize, pointIndex 
                        + (titleFontSize + secttSize / 2) * (i + 1));// i+1 
            } 
            tempStr = map.get(keys.length - 1); 
            as = new AttributedString(tempStr); 
            as.addAttribute(TextAttribute.FONT, font); 
            g.drawString(as.getIterator(), width / 2 - writeLen / 2 
                    * titleFontSize, pointIndex 
                    + (titleFontSize + secttSize / 2) * writeLen);// 最后一行 
            sum += (titleFontSize + secttSize) * writeLen; 
            setPointIndex(sum); 
        } 
        pointOnTitle = pointIndex; 
    } 
*/



    /**
     * 合成图片
     * <p>
     * strTitleString 标题
     * </p>
     * <p>
     * context 正文
     * </p>
     * <p>
     * luokuan 落款
     * </p>
     * 分页产生的图片名 递增1
     *
     */

    public int compound(String strTitleString, String context, String[] luokuan) throws Exception {
        int resultCount = 0;// 生成的图片张数
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(imageSpecial.getBgFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int imgWidth = bi.getWidth();
        int ImgHeight = bi.getHeight();
        Graphics2D g = getGraphics(bi);
        Font titleFont = new Font(imageSpecial.getTitleFontStyle(), imageSpecial.getTitleFontBlod(), imageSpecial.getTitleFontSize());// 标题
        Font contentFont = new Font(imageSpecial.getContentFontStyle(), imageSpecial.getContentFontBlod(), imageSpecial.getContentFontSize());// 内容
        Font luoKuanFont = new Font(imageSpecial.getContentFontStyle(), imageSpecial.getContentFontBlod(),
                imageSpecial.getContentFontSize());// 落款
        if (strTitleString == null && context == null && luokuan == null) {
            throw new Exception("没有数据，没有写入任何数据");
        } else {
            g.setFont(contentFont);
            Map<Integer, String> pagemap = getPageStrings(imgWidth, ImgHeight,
                strTitleString, context);
            resultCount = pagemap.size();
            for (int i = 1; i <= resultCount; i++) {
                imageSpecial.setIndexPage(0);// 初始化
                String pageContent = pagemap.get(i);
                drawTitle(titleFont, imgWidth, strTitleString, g);
                String[] testContext = getContents(pageContent);// 每一页段数据
                if (testContext != null) {
                    for (String aTestContext : testContext) {
                        drawContent(imgWidth, aTestContext, g);
                    }
                    if (pagemap.size() == 1 || i == pagemap.size()) {
                        imageSpecial.setIndexPage(2 * imageSpecial.getSecttSize()); // 设置落款 开始位置
                        if (luokuan != null) {
                            drawLuoKuan(imgWidth, luokuan, luoKuanFont, g);
                        }
                    }
                }
                try {
                    String fatherPath = imageSpecial.getBgFile().getParent();
                    String fileAbName = imageSpecial.getBgFile().getName();
                    String fn = fileAbName.substring(0,
                        fileAbName.lastIndexOf("."));// 文件名前缀
                    String filenameString = fatherPath + "/" + fn + i + ".jpg";
                    File f = new File(filenameString);
                    ImageIO.write(bi, "jpg", f);
                    bi = ImageIO.read(imageSpecial.getBgFile());
                    g = getGraphics(bi);
                    g.setFont(contentFont);
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
            g.dispose();
            // bgFile.delete();// 删除背景
            System.out.println("合成完毕");
            return resultCount;
        }

    }// end

    private Graphics2D getGraphics(BufferedImage bi) {
        Graphics2D g = bi.createGraphics();
        // 字体平滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(imageSpecial.getColor());
        return g;
    }

    /**
     * 写入标题 居中 字数过多换行
     *
     */
    public void drawTitle(Font font, int width, String title, Graphics2D g) {
        AttributedString as = null;
        // title = ToSBC(title);

        int fullLen = (width - imageSpecial.getContentPadding() * 2) / imageSpecial.getTitleFontSize();// 一行的最多容量
        int titleLen = title.length();
        if (titleLen > fullLen) {
            String s = title;
            int writeLen = (s.length() + fullLen - 1) / fullLen;// 可写入行数
            String string;
            int sum = 0;
            for (int i = 0; i < writeLen - 1; i++) {
                int begin = i * fullLen;
                int end = begin + fullLen;
                string = s.substring(begin, end);
                int sl = string.length();// 实际每行写入字数
                as = new AttributedString(string);
                as.addAttribute(TextAttribute.FONT, font);
                g.drawString(as.getIterator(), width / 2 - sl / 2
                    * imageSpecial.getTitleFontSize(), imageSpecial.getPointIndex()
                    + (imageSpecial.getTitleFontSize() + imageSpecial.getSecttSize()/ 2) * (i + 1));// i+1
                // 去除二段重行
            }
            string = s.substring((writeLen - 1) * fullLen);
            as = new AttributedString(string);
            as.addAttribute(TextAttribute.FONT, font);
            g.drawString(as.getIterator(), width / 2 - string.length() / 2
                * imageSpecial.getTitleFontSize(),imageSpecial.getPointIndex()
                + (imageSpecial.getTitleFontSize() + imageSpecial.getSecttSize() / 2) * writeLen);// 最后一行
            sum += (imageSpecial.getTitleFontSize() + imageSpecial.getSecttSize() ) * writeLen;
            imageSpecial.setPointIndex(sum);
        } else {
            as = new AttributedString(title);
            as.addAttribute(TextAttribute.FONT, font);
            g.drawString(as.getIterator(), width / 2 - titleLen / 2
                * imageSpecial.getTitleFontSize(),imageSpecial.setPointIndex(imageSpecial.getTitleFontSize()+imageSpecial.getSecttSize()));
            imageSpecial.setPointIndex( imageSpecial.getPointIndex()  + (int) Math.ceil(1.5 * imageSpecial.getSecttSize()));

        }
        imageSpecial.setPointOnTitle(imageSpecial.getPointIndex());
    }

    /**
     * 写入落款
     *
     * 从后面往前写
     *
     */
    public void drawLuoKuan(int width, String[] wsString, Font font,
                            Graphics2D g) throws Exception {
        AttributedString as = null;
        int padding = 2;
        int fontsize = font.getSize();
        int fullLen = (width - imageSpecial.getContentPadding() * 2) / fontsize;// 一行的最多容量
        for (int i = 0; i < wsString.length; i++) {
            // wsString[i] = ToSBC(wsString[i]);
            if (wsString[i].length() > fullLen) {
                throw new Exception("落款长度过长");
            }
            as = new AttributedString(wsString[i]);
            as.addAttribute(TextAttribute.FONT, font);
            if (wsString[0].length() > wsString[1].length()) {
                if (i == 0) {
                    g.drawString(as.getIterator(),
                        (fullLen - wsString[0].length() + padding)
                            * fontsize, imageSpecial.setPointIndex(fontsize
                            + imageSpecial.getSecttSize()));
                    imageSpecial.setPointIndex(-(imageSpecial.getSecttSize()/ 2));
                } else {
                    g.drawString(
                        as.getIterator(),
                        (fullLen
                            - wsString[1].length()
                            + ((wsString[1].length() - wsString[0]
                            .length()) / 2) + padding)
                            * fontsize, imageSpecial.setPointIndex(fontsize
                            + imageSpecial.getSecttSize()));
                }
            } else if (wsString[0].length() < wsString[1].length()) {
                if (i == 0) {
                    g.drawString(
                        as.getIterator(),
                        (fullLen
                            - wsString[0].length()
                            + ((wsString[0].length() - wsString[1]
                            .length()) / 2) + padding)
                            * fontsize, imageSpecial.setPointIndex(fontsize
                            + imageSpecial.getSecttSize()));
                    imageSpecial.setPointIndex(-(imageSpecial.getSecttSize() / 2));
                } else {
                    g.drawString(as.getIterator(),
                        (fullLen - wsString[1].length() + padding)
                            * fontsize, imageSpecial.setPointIndex(fontsize
                            + imageSpecial.getSecttSize()));
                }
            } else {
                g.drawString(as.getIterator(),
                    (fullLen - wsString[0].length() + padding) * fontsize,
                        imageSpecial.setPointIndex(fontsize + imageSpecial.getSecttSize()));
                imageSpecial.setPointIndex(-(imageSpecial.getSecttSize() / 2));
            }
        }

    }

    /**
     * 书写段落
     * 文本边距为1个字符
     * 写入段落 内容区 一个字的像素为SMALLSIZE
     */
    public void drawContent(int width, String content, Graphics2D g) {
        // content = ToSBC(content);
        // TODO
        int fullFontLen = (width - imageSpecial.getContentPadding() * 2) /imageSpecial.getContentFontSize();// 满行写入
        // 可写长度(字数)
        String duanshou;
        int writeLen;
        int sum = 0;
        if (content.length() < fullFontLen) {
            duanshou = content;
            writeLen = 1;
            g.drawString(duanshou, imageSpecial.getContentPadding(),imageSpecial.getPointIndex() + (2 * imageSpecial.getSecttSize()));
            imageSpecial.setPointIndex(2 * (int) Math.ceil(1.5 * imageSpecial.getSecttSize()));
        } else {
            Map<Integer, String> map = rowAttr(content, fullFontLen);
            Object[] keys = map.keySet().toArray();
            duanshou = map.get(keys[0]);//段首行
            g.drawString(duanshou, imageSpecial.getContentPadding() + 2 * imageSpecial.getContentFontSize(), imageSpecial.getPointIndex()+ (int) Math.ceil(1.5 * imageSpecial.getSecttSize()));//写入段首行  缩进两个字符

            for (int i = 1; i < keys.length; i++) {
                String tempStr = map.get(keys[i]);
                g.drawString(tempStr, imageSpecial.getContentPadding(), imageSpecial.getPointIndex() + (int) Math.ceil(1.5 * imageSpecial.getSecttSize())
                    + (int) Math.ceil(1.5 * imageSpecial.getSecttSize()) * i);
            }
            writeLen = keys.length;
            sum += (((int) Math.ceil(1.5 * imageSpecial.getSecttSize())) * (writeLen));// 段落之间的间隙
            imageSpecial.setPointIndex(sum + imageSpecial.getSecttSize());
        }

    }

    /**
     * 内容得知行属性
     */
    public Map<Integer, String> rowAttr(String str, int fullFontLen) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        int fullNum = 2 * fullFontLen;// 满行可写入数字字母的 个数 汉字为双倍
        int i = 0;
        char[] strArr = str.toCharArray();
        int count = 0;// 控制句容量 计数子
        int index = 0;// 句号
        while (true) {
            if (strArr[i] == 32 || strArr[i] < 127) {
                count++;
            } else {
                count += 2;// 汉字两个字符像素位子
            }
            if (fullNum <= count) {
                String s = str.substring(0, i);
                if (index == 0) {//段首行 满容量缩进两个字符
                    s = str.substring(0, i - getSuojinNum(s));
                }
                map.put(index, s);
                str = str.substring(s.length());// 取后面新的一句
                strArr = str.toCharArray();
                index++;
                count = 0;
                i = 0;
                continue;
            }
            i++;
            if (getOccupyNum(str) <= fullNum) {// 最后一句容量小于临界
                map.put(index, str);
                break;
            }

        }
        return map;

    }

    /**
     * 缩进需要挤出的字符数
     * 缩进两个汉字字符
     */
    private int getSuojinNum(String str) {
        char[] strArr = str.toCharArray();
        int count = 0;//统计像素
        int sum = 0;//统计字数
        int i = strArr.length - 1;
        while (true) {
            if (strArr[i] == 32 || strArr[i] < 127) {
                count++;
            } else {
                count += 2;// 汉字两个字符像素位子
            }
            sum++;
            if (count >= 4) {
                break;
            }
        }
        return sum;
    }

    /**
     * 得到一句容量
     */
    private int getOccupyNum(String str) {
        int count = 0;
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == 32 || ch[i] < 127) {
                count++;
            } else {
                count += 2;
            }
        }

        return count;
    }

    /**
     * 是否存在下一页
     */
    public boolean hasNextPage(int height) {
        if (imageSpecial.getPointIndex() > (height - 2 * imageSpecial.getSecttSize())) {
            // 存在下一页新页开始
            imageSpecial.setPointIndex(0);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 写入内容时是否产生分页 TODO 第一段就超出页 除第一段之后的超页
     */
    private Map<Integer, String> getPageStrings(int width, int height,
                                                String title, String testContext) {
        // title = ToSBC(title);
        // 一行标题的最多容量
        int titleFullLen = (width - imageSpecial.getContentPadding() * 2) / imageSpecial.getTitleFontSize();
        // 标题占有行
        int titleWriteLen = (title.length() + titleFullLen - 1) / titleFullLen;
        // 标题高度
        int titleHeight = titleWriteLen * (imageSpecial.getTitleFontSize() +imageSpecial.getSecttSize());
        // 满行写入可写长度(字数)
        int fullFontLen = (width - imageSpecial.getContentPadding() * 2) / imageSpecial.getContentFontSize();

        int contentHeight = getDuanLuoHeight(fullFontLen, titleHeight, height,
            testContext);

        // int contentHeight = getContents(testContext).length * 2 *
        // secttSize;// 满区内容段落间距总高度
        // 除去落款,背景可书写总行数
        int bgTotalRows = (height - titleHeight - contentHeight) / imageSpecial.getSecttSize()
            - 3;
        // 实际内容所需行数
        int vTotalRows = (testContext.length() + fullFontLen - 1) / fullFontLen;
        // 满屏写入内容字数
        int fullWord = bgTotalRows * fullFontLen;
        if (imageSpecial.getMap() == null) {
            imageSpecial.setMap( new HashMap<Integer, String>());
        }
        if (vTotalRows < bgTotalRows) {
            imageSpecial.getMap().put(imageSpecial.getIndexPage(), testContext);
        } else {
            int count = 0;
            // 需要总页数
            int pageCount = (vTotalRows + bgTotalRows - 1) / bgTotalRows;
            String string;
            for (int i = count; i < pageCount; i++) {
                if (i == pageCount - 1) {
                    // 最后一页
                    string = testContext.substring(i * fullWord);
                } else {
                    string = testContext.substring(i * fullWord, (i + 1)
                        * fullWord);
                }
                // System.out.println("当页内容:\n"+string);
                imageSpecial.getMap().put(imageSpecial.getIndexPage(), string);
                imageSpecial.setIndexPage(imageSpecial.getIndexPage()+1);

            }
        }
        System.out.println("总页数" + imageSpecial.getMap().size());
        return imageSpecial.getMap();
    }

    // 每页段落总间隙
    // 如果是第一页 写入 如果是其他的 算满屏额
    private int getDuanLuoHeight(int fullFontLen, int titleHeight, int height,
                                 String testContext) {
        int bgTotalRows = (height - titleHeight) / imageSpecial.getSecttSize() - 4;// 满页可写入行数
        int fullWord = bgTotalRows * fullFontLen;// 满屏写入内容字数
        int wlen = testContext.length();
        if (wlen < fullWord) {
            return getContents(testContext).length * 2 *  imageSpecial.getSecttSize();
        } else {
            return getContents(testContext.substring(0, fullWord)).length * 2
                *  imageSpecial.getSecttSize();
        }
    }

    /**
     * 字符串处理 解析出段落
     */
    private String[] getContents(String str) {
        while (str.startsWith("\r\n")) {
            str = str.substring(2);
        }
        str = str.replaceAll("(\r?\n(\\s*\r?\n)+)", "\r\n");// 处理多个连续的\r\n
        // 置换成一个/r/n
        String[] a = str.split("\r\n");
        return a.length == 0 ? null : a;
    }

    /**
     * 字符串处理
     */
    private String[] getContent(String str) {

        String string = str.replace("\r\n", "");
        string = string.replace("<p>", "{");
        string = string.replace("</p>", "}");
        List<String> list = new ArrayList<String>();
        while (string.indexOf("{") > -1) {
            int start = string.indexOf("{") + 1;
            int end = string.indexOf("}");
            String st = string.substring(start, end);
            list.add(st);
            string = string.substring(end + 1);
        }
        for (int i = 0; i < list.size(); i++) {
            if ("".equals(list.get(i)) || list.get(i) == null) {
                list.remove(i);
            }
        }
        String[] a = list.toArray(new String[0]);
        return a.length == 0 ? null : a;
    }

    // 首零的处理
    public String removeBeginZ(String str) {
        if (str.startsWith("0")) {
            int index = str.indexOf("0");
            return str.substring(index);
        } else {
            return str;
        }

    }

    /**
     获得当前字体列表
     */
    public List<String> getSysFontList() {
        List<String> fl = new ArrayList<String>();
        String[] fontlist =
            GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String except = "^[a-zA-Z0-9_ -.]+$";  //过滤英文字体
        Pattern pattern = Pattern.compile(except);
        for (String string : fontlist) {
            if (!pattern.matcher(string).matches()) {
                System.out.println(string);
                fl.add(string);
            }
        }

        return fl;
    }

}  