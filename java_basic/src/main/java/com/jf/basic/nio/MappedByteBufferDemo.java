package com.jf.basic.nio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * TODO
 *
 * @author Junfeng
 */
public class MappedByteBufferDemo {

    public static void main(String[] arg) {
        readFile();
    }

    public static void readFile(){
        File file = new File("C:\\Users\\JF\\Desktop\\test.txt");
        byte[] bytes = new byte[(int) file.length()];
        try (FileChannel fc = new FileInputStream(file).getChannel()){
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            for (int i = 0; i < mbb.capacity(); i++) {
                bytes[i] = mbb.get();
            }

            Scanner scanner = new Scanner(new ByteArrayInputStream(bytes));

            while (scanner.hasNext()){
                out.println(scanner.next());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
