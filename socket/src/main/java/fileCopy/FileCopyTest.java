package fileCopy;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * @Author yangxw
 * @Date 2020-11-28 下午2:18
 * @Description
 * @Version 1.0
 */
public class FileCopyTest {
    public static void main(String[] args) {

        try {
            copy("D:\\yangxw\\work\\my_project\\yxw\\combiner\\socket\\src\\main\\resources\\a.txt",
                    "D:\\yangxw\\work\\my_project\\yxw\\combiner\\socket\\src\\main\\resources\\b.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void copy(String source, String target) throws IOException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(source));

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(target));
        int result = 0;
        byte[] byteBuffer = new byte[1024];
        while ((result = bufferedInputStream.read(byteBuffer)) != -1) {
            bufferedOutputStream.write(byteBuffer, 0, result);
        }
        if (bufferedInputStream != null) {
            bufferedInputStream.close();
        }

        if (bufferedOutputStream != null) {
            bufferedOutputStream.close();
        }

    }
}
