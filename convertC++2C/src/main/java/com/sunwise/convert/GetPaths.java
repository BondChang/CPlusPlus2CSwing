package com.sunwise.convert;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* 获取文件夹中的信息
 * 1: C文件的完整名称列表
 * 2: 含有头文件的子文件夹的完整名称列表 */
public class GetPaths {

    /* C代码文件列表 */
    private List<String> cPaths;

    /* H代码文件列表 */
    private List<String> hPathFiles;

    public GetPaths(String directoryPath) {
        File dirFile = new File(directoryPath);

        /* 判断文件夹存在 且 的确是文件夹 */
        if (dirFile.exists() && dirFile.isDirectory()) {
            this.cPaths = new ArrayList();

            this.hPathFiles = new ArrayList();

            /* 调用递归函数处理 */
            try {
                this.parseDirectory(dirFile, directoryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                throw new IOException(directoryPath + "文件夹不存在！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* 递归解析 */
    private void parseDirectory(File dirFile, String rootPathString) throws IOException {
        File files[] = dirFile.listFiles();

        for (File file : files) {
            /* 遍历文件夹下的所有元素 */
            if (file.isDirectory()) {
                /* 如果是文件夹, 则递归处理 */
                parseDirectory(file, rootPathString);
            } else if (file.isFile()) {
                String fileName = file.getAbsolutePath();
                //                String fileName = file.getCanonicalPath();
                //                Path fileNamePath = Paths.get(fileName);
                //                fileName = "./" + rootPath.relativize(fileNamePath).toString();
                //                fileName = FilenameUtils.separatorsToUnix(fileName);fileName
                String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                if (suffixName.equalsIgnoreCase("cpp")) {
                    /* Cpp文件 */
                    cPaths.add(fileName);
                } else if (suffixName.equalsIgnoreCase("h")) {
                    hPathFiles.add(fileName);
                } else if (suffixName.equalsIgnoreCase("hpp")) {
                    hPathFiles.add(fileName);
                }
            }
        }
    }

    public List<String> getcPaths() {
        return cPaths;
    }

    public List<String> gethPathFiles() {
        return hPathFiles;
    }

    public static void main(String[] args) {
        GetPaths getPaths = new GetPaths("C:\\Users\\bondc\\Desktop\\test");
        List<String> hPathsList = getPaths.gethPathFiles();
        String hStr = "";
        for (String h : hPathsList) {
            hStr += "\"";
            hStr += h.replace("\\", "/");
            hStr += "\"";
            hStr += ",";
        }
        System.out.println(hStr);

    }
}
