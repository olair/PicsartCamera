@file:Suppress("MemberVisibilityCanBePrivate")

package com.olair.utils

import java.io.*

/**
 * Create by oLair on 2020/10/23.
 */
object FileUtil {
    /**
     * 保存文件
     *
     * @param data 要保存的数据
     * @param dir  文件目录
     * @param name 文件名
     */
    fun saveToFile(data: ByteArray, dir: String, name: String): Boolean {
        val file = File(dir, name)
        if (file.exists()) {
            file.delete()
        }
        var outputStream: OutputStream? = null
        try {
            file.createNewFile()
            outputStream = FileOutputStream(file)
            outputStream.write(data)
        } catch (ignored: IOException) {
            return false
        } finally {
            closeIO(outputStream)
        }
        return true
    }

    fun closeIO(ioCloseable: Closeable?) {
        try {
            ioCloseable?.close()
        } catch (ignored: IOException) {
        }
    }
}