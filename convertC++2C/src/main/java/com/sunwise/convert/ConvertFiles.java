package com.sunwise.convert;

import java.util.ArrayList;
import java.util.List;

public class ConvertFiles {

    /* cpp文件列表 */
    private List<String> cppFileList;

    /* hpp文件列表 */
    private List<String> hppFileList;

    public List<String> getCppFileList() {
        return cppFileList;
    }

    public ConvertFiles() {
        this.cppFileList = new ArrayList<>();
        this.hppFileList = new ArrayList<>();
    }

    public void setCppFileList(List<String> cppFileList) {
        this.cppFileList = cppFileList;
    }

    public List<String> getHppFileList() {
        return hppFileList;
    }

    public void setHppFileList(List<String> hppFileList) {
        this.hppFileList = hppFileList;
    }
}
