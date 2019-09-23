package com.jf.basic.nio;

import java.io.IOException;
import java.io.RandomAccessFile;

import static java.lang.System.out;

/**
 * RandomAccessFile是用来访问那些保存数据记录的文件的，你就可以用seek( )方法来访问记录，并进行读写了。这些记录的大小不必相同；
 * 但是其大小和位置必须是可知的。但是该类仅限于操作文件。
 * <p>
 * RandomAccessFile不属于InputStream和OutputStream类系的。实际上，除了实现DataInput和DataOutput接口之外(DataInputStream
 * 和DataOutputStream也实现了这两个接口)，它和这两个类系毫不相干，甚至不使用InputStream和OutputStream类中已经存在的任何功能；
 * 它是一个完全独立的类，所有方法(绝大多数都只属于它自己)都是从零开始写的。这可能是因为RandomAccessFile能在文件里面前后移动，
 * 所以它的行为与其它的I/O类有些根本性的不同。总而言之，它是一个直接继承Object的，独立的类。
 * <p>
 * 基本上，RandomAccessFile的工作方式是，把DataInputStream和DataOutputStream结合起来，再加上它自己的一些方法，
 * 比如定位用的getFilePointer( )，在文件里移动用的seek( )，以及判断文件大小的length( )、skipBytes()跳过多少字节数。
 * 此外，它的构造函数还要一个表示以只读方式(“r”)，还是以读写方式(“rw”)打开文件的参数 (和C的fopen( )一模一样)。它不支持只写文件。
 * <p>
 * 只有RandomAccessFile才有seek搜寻方法，而这个方法也只适用于文件。BufferedInputStream有一个mark( )方法，
 * 你可以用它来设定标记(把结果保存在一个内部变量里)，然后再调用reset( )返回这个位置，但是它的功能太弱了，而且也不怎么实用。
 * <p>
 * RandomAccessFile的绝大多数功能，但不是全部，已经被JDK 1.4的nio的”内存映射文件(memory-mapped files)”给取代了，
 * 你该考虑一下是不是用”内存映射文件”来代替RandomAccessFile了。
 *
 * @author Junfeng
 */
public class RandomAccessFileTest {

    public static void main(String[] arg) {
        // 制造数据
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("test.bat", "rw")) {
            for (int i = 1; i < 5; i++) {
                out.println(" -[DEBUG]-  " + i + " = " + i * 1.14);
                randomAccessFile.writeDouble(i * 1.14);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 修改指定位置数据
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("test.bat", "rw")) {
            // 直接将指针跳到第2个double数据后面
            randomAccessFile.seek(2 * 8);
            // 覆盖第三个double,
            // 注意： RandomAccessFile无法实现再指定位置插入，只有将指定位置后的内容读取到缓冲区，插入数据后，再讲缓冲区数据写入方式来实现
            randomAccessFile.writeDouble(99.99);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 展示数据
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("test.bat", "r")) {
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                out.println(" -[DEBUG]-  show:  " + randomAccessFile.readDouble());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
