package com.sunwise.convert;

import org.apache.commons.io.FileUtils;

import java.io.*;

/* 文件夹拷贝 */
public class CopyDirectory {

    /* 复制操作 */
    public void copy(String sourcePath, String targetPath) {
        File pathFrom = new File(sourcePath);
        File pathTo = new File(targetPath);

        /* 如果目标路径不存在, 则创建文件夹  */
        if (!pathTo.exists()) {
            pathTo.mkdir();
        }

        try {
            FileUtils.copyDirectory(pathFrom, pathTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
