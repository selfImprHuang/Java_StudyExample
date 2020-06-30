package image;

import entity.ImageSpecial;

import java.awt.*;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtilTest {



    public static void main(String[] args) throws Exception {

        File bgFile = new File("F:\\Java个人代码\\Java_Utils\\src\\main\\test\\util\\image");

        ImageUtil iUtil = new ImageUtil();
        ImageSpecial imageSpecial = new ImageSpecial();
        imageSpecial.setBgFile(bgFile);// 设置背景图片
        imageSpecial.setColor(Color.BLACK);// 设置画笔颜色
        imageSpecial.setTitleFontSize(42);// 设置标题字体大小 一号    42二号
        imageSpecial.setContentFontSize(30);// 设置正文字体    小四号
        imageSpecial.setContentPadding(42);// 设置边距 距背景边界
        imageSpecial.setSecttSize(30);// 设置行高 段间距默认两倍行高

        imageSpecial.setTitleFontBlod(Font.BOLD); //设置字体的样式
        imageSpecial.setContentFontBlod(Font.PLAIN);

        imageSpecial.setTitleFontStyle("黑体");//设置字体的类型
        imageSpecial.setContentFontStyle("宋体");
        iUtil.setImageSpecial(imageSpecial);


        String strTitleString = "关于积极参与2012第二届";
        String context = "积极参与积极参与处室、厅直各单位：\r\n\r\n"
                + "组织开展评选活动组织开展评选活动经启动，" + "公众参与网上投票评比时间自2012年9月20日"
                + "起至10月15日23时止。"
                + "\r\n\r\n\r\n\r\n本次评选活动为有奖评选，" +
                "fdfsffsdfsdfsdfsdfsfs欢组织开展评选活动。各单位要认真"
                + "组织开展评选活动，组织开展评选活动经启动将评选活动通组织开展评选活动经启动。"
                + " \r\n网址：http://www.jxsl.gov.cn/sltvote/" + "积极参与积极参与处室、厅直各单位：\r\n\r\n"
                + "组织开展评选活动组织开展评选活动经启动，" + "公众参与网上投票评比时间自2012年9月20日"
                + "起至10月15日23时止。"
                + "\r\n\r\n\r\n\r\n本次评选活动为有奖评选，" +
                "fdfsffsdfsdfsdfsdfsfs欢组织开展评选活动。各单位要认真"
                + "组织开展评选活动，组织开展评选活动经启动将评选活动通组织开展评选活动经启动。"
                + " \r\n网址：http://www.jxsl.gov.cn/sltvote/" + "积极参与积极参与处室、厅直各单位：\r\n\r\n"
                + "组织开展评选活动组织开展评选活动经启动，" + "公众参与网上投票评比时间自2012年9月20日"
                + "起至10月15日23时止。"
                + "\r\n\r\n\r\n\r\n本次评选活动为有奖评选，" +
                "fdfsffsdfsdfsdfsdfsfs欢组织开展评选活动。各单位要认真"
                + "组织开展评选活动，组织开展评选活动经启动将评选活动通组织开展评选活动经启动。"
                + " \r\n网址：http://www.jxsl.gov.cn/sltvote/" + "积极参与积极参与处室、厅直各单位：\r\n\r\n"
                + "组织开展评选活动组织开展评选活动经启动，" + "公众参与网上投票评比时间自2012年9月20日"
                + "起至10月15日23时止。"
                + "\r\n\r\n\r\n\r\n本次评选活动为有奖评选，" +
                "fdfsffsdfsdfsdfsdfsfs欢组织开展评选活动。各单位要认真"
                + "组织开展评选活动，组织开展评选活动经启动将评选活动通组织开展评选活动经启动。"
                + " \r\n网址：http://www.jxsl.gov.cn/sltvote/";
        String[] luokuan = new String[]{"酱油部",
                toCN_Date("2012-9-21")};
        System.out.println(toCN_Date("2012-9-21"));
        iUtil.compound(strTitleString, context, luokuan);

    }


    /**
     * 转化为中文日期格式 形式如：二〇一〇年十月二十一
     *
     * 传入格式：yyyy-MM-dd
     *
     * @param str
     * @return 中文日期格式
     * @throws Exception
     */
    static String toCN_Date(String str) throws Exception {
        String eL = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";// 匹配正则
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(str);
        boolean dateFlag = m.matches();
        if (dateFlag) {
            String[] strings = str.split("-");
            char[] y = strings[0].toCharArray();// 年
            StringBuffer cndate = new StringBuffer();
            for (int i = 0; i < y.length; i++) {
                char cy = conversionNum((int) y[i] - 48);
                cndate.append(cy);
            }
            String mounth = conversionMouth(Integer.parseInt(String
                    .valueOf(strings[1])));
            String daString = conversionMouth(Integer.parseInt(String
                    .valueOf(strings[2])));
            cndate.append("年" + mounth + "月");
            cndate.append(daString + "日");
            return cndate.toString();
        } else {
            throw new Exception("请使用正确格式的日期字符串,格式为yyyy-MM-dd");
        }
    }


    /**
     * 解析月/日
     */
    private static String conversionMouth(int a) {
        if (a <= 10) {
            return String.valueOf(conversionNum(a));
        } else {
            int gw = a % 10;
            int sw = a / 10;
            String swString = String.valueOf(conversionNum(sw));
            String gwString = String.valueOf(conversionNum(gw));
            if (a < 20) {
                return "十" + gwString;
            } else {
                return swString + "十" + gwString;
            }
        }
    }

    /**
     * 置换日期格式
     */
    private static char conversionNum(int c) {
        char rc = 0;
        switch (c) {
            case 1:
                rc = '一';
                break;
            case 2:
                rc = '二';
                break;
            case 3:
                rc = '三';
                break;
            case 4:
                rc = '四';
                break;
            case 5:
                rc = '五';
                break;
            case 6:
                rc = '六';
                break;
            case 7:
                rc = '七';
                break;
            case 8:
                rc = '八';
                break;
            case 9:
                rc = '九';
                break;
            case 10:
                rc = '十';
                break;
            case 0:
                rc = '〇';
                break;
        }
        return rc;
    }


}
