package com.utte.wifip2ptest;

import java.io.Serializable;

public class FileBean implements Serializable {

    public static final long serialVersionUID = 1;

    public String filePath;

    public long fileLength;

    //MD5码：保证文件的完整性
    public String md5;

    public FileBean(String filePath, long fileLength, String md5) {
        this.filePath = filePath;
        this.fileLength = fileLength;
        this.md5 = md5;
    }
}
