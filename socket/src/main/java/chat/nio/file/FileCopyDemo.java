package chat.nio.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

interface FileCopyRunner {
    void copyFile(File source, File target);
}

/**
 * 文件拷贝
 */
public class FileCopyDemo {

    private static final int ROUNDS = 5;

    private static void benchmark(FileCopyRunner test, File source, File target) {
        long elapsed = 0L;
        for (int i = 0; i < ROUNDS; i++) {
            long start = System.currentTimeMillis() / 1000;
            test.copyFile(source, target);
            elapsed += System.currentTimeMillis() / 1000 - start;
            target.delete();
        }
        System.out.println(elapsed);
        System.out.println(test + ":" + elapsed / ROUNDS);
    }

    public static void main(String[] args) {

        FileCopyRunner noBufferStreamCopy = new FileCopyRunner() {
            @Override
            public String toString() {
                return "noBufferStreamCopy";
            }

            @Override
            public void copyFile(File source, File target) {
                InputStream fin = null;
                OutputStream fout = null;


                try {
                    fin = new FileInputStream(source);
                    fout = new FileOutputStream(target);

                    // 每次只能读出一个字节
                    int result;
                    while ((result = fin.read()) != -1) {
                        fout.write(result);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fin);
                    close(fout);
                }
            }

        };
        FileCopyRunner bufferStreamCopy = new FileCopyRunner() {
            @Override
            public String toString() {
                return "BufferStreamCopy";
            }

            @Override
            public void copyFile(File source, File target) {
                InputStream fin = null;
                OutputStream fout = null;
                try {
                    fin = new BufferedInputStream(new FileInputStream(source));
                    fout = new BufferedOutputStream(new FileOutputStream(target));
                    byte[] buffer = new byte[1024];
                    int result;
                    while ((result = fin.read(buffer)) != -1) {
                        fout.write(buffer, 0, result);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fin);
                    close(fout);
                }


            }
        };
        FileCopyRunner nioBufferCopy = new FileCopyRunner() {
            @Override
            public String toString() {
                return "nioBufferCopy";
            }

            @Override
            public void copyFile(File source, File target) {
                FileChannel fin = null;
                FileChannel fout = null;

                try {
                    fin = new FileInputStream(source).getChannel();
                    fout = new FileOutputStream(target).getChannel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    while (fin.read(byteBuffer) != -1) {
                        // 从写切换为读模式
                        byteBuffer.flip();

                        while (byteBuffer.hasRemaining()) {
                            fout.write(byteBuffer);
                        }

                        // 从读模式切位写模式
                        byteBuffer.clear();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fin);
                    close(fout);
                }

            }
        };
        FileCopyRunner nioTransferCopy = new FileCopyRunner() {
            @Override
            public String toString() {
                return "nioTransferCopy";
            }

            @Override
            public void copyFile(File source, File target) {
                FileChannel fin = null;
                FileChannel fout = null;

                try {
                    fin = new FileInputStream(source).getChannel();
                    fout = new FileOutputStream(target).getChannel();
                    long l = 0L;
                    long total = fin.size();
                    while (total != l) {
                        l = fin.transferTo(0, total, fout);
                    }
                    // fout.transferFrom(fin,0,fin.size());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fin);
                    close(fout);
                }


            }
        };


        File smallFile = new File("C:\\Users\\yxw\\Desktop\\tmp\\test\\file-copy\\small.txt");
        File smallFileCopy = new File("C:\\Users\\yxw\\Desktop\\tmp\\test\\file-copy\\small-copy.txt");
        //  noBufferStreamCopy.copyFile(smallFile, smallFileCopy);
        //   bufferStreamCopy.copyFile(smallFile, smallFileCopy);
        //  nioBufferCopy.copyFile(smallFile, smallFileCopy);
        nioTransferCopy.copyFile(smallFile, smallFileCopy);


//        System.out.println("---Copying small file---");
//       // benchmark(noBufferStreamCopy, smallFile, smallFileCopy);
//        benchmark(bufferStreamCopy, smallFile, smallFileCopy);
//        benchmark(nioBufferCopy, smallFile, smallFileCopy);
//        benchmark(nioTransferCopy, smallFile, smallFileCopy);
//
//
//        File bigFile = new File("C:\\Users\\yxw\\Desktop\\tmp\\test\\file-copy\\big.txt");
//        File bigFileCopy = new File("C:\\Users\\yxw\\Desktop\\tmp\\test\\file-copy\\big-copy.txt");
//
//        System.out.println("---Copying big file---");
//       // benchmark(noBufferStreamCopy, bigFile, bigFileCopy);
//        benchmark(bufferStreamCopy, bigFile, bigFileCopy);
//        benchmark(nioBufferCopy, bigFile, bigFileCopy);
//        benchmark(nioTransferCopy, bigFile, bigFileCopy);


    }

    private static void test() {


    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
