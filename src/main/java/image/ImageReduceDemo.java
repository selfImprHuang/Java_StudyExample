package image;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 志军
 * 图片压缩工具类使用示例
 */
public class ImageReduceDemo {



    public void reduce() throws IOException {
        //原图地址
        String imgPath = "E:\\学习资料--自学+项目学习\\自学资料\\代码学习\\代码配置ssm\\codeSpringMybatis\\src\\main\\java\\com\\springmvc\\image\\test.png";
        //压缩图片地址
        String thumbnailFilePathName = "E:\\学习资料--自学+项目学习\\自学资料\\代码学习\\代码配置ssm\\codeSpringMybatis\\src\\main\\java\\com\\springmvc\\image\\" + UUID.randomUUID().toString();
        Thumbnails.of(imgPath).scale(1f).outputFormat("jpg").toFile(thumbnailFilePathName);
        //生成压缩后的字节
        byte[] b1 = imageToByte(thumbnailFilePathName + ".jpg");
        byte[] b2 = imageToByte(imgPath);
        System.out.println("压缩前后字节差：" + (b2.length - b1.length));
        //把字节写入文件
        toFile(b1, "E:\\学习资料--自学+项目学习\\自学资料\\代码学习\\代码配置ssm\\codeSpringMybatis\\src\\main\\java\\com\\springmvc\\image\\filebyte.txt");
        //还原图片
        toFile(b1, "E:\\学习资料--自学+项目学习\\自学资料\\代码学习\\代码配置ssm\\codeSpringMybatis\\src\\main\\java\\com\\springmvc\\image\\huanyuan.jpg");

    }


    /**
     * 图片转换成byte数组
     */
    private byte[] imageToByte(String filePath) {
        byte[] bytes = new byte[0];
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
            bytes = new byte[in.available()];
            in.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    /**
     * 将byte数组写入文件，便于阅读？
     */
    private void toFile(byte[] bytes, String filePath) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(filePath));
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
