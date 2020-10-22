package com.olair.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Create by oLair on 2020/10/23.
 */
public class FileUtil {

    /**
     * 保存文件
     *
     * @param data 要保存的数据
     * @param dir  文件目录
     * @param name 文件名
     */
    public static boolean saveToFile(byte[] data, String dir, String name) {
        File file = new File(dir, name);
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
        OutputStream outputStream = null;
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
        } catch (IOException ignored) {
            return false;
        } finally {
            closeIO(outputStream);
        }
        return true;
    }

    public static void closeIO(Closeable ioCloseable) {
        try {
            if (ioCloseable != null) {
                ioCloseable.close();
            }
        } catch (IOException ignored) {
        }
    }

}
