package com.silverbullet.baidu.entity;

/**
 * Created by jeffyuan on 2017/12/20.
 */
public class FileInfo {
    // 名称
    private String fileName;
    // 相对路径
    private String fileRelativePath;
    // 文件大小
    private long filesize;
    // 创建时间
    private String ctime;
    // 编辑时间
    private String etime;

    public FileInfo(String fileName, String fileRelativePath, long filesize) {
        this.fileName = fileName;
        this.fileRelativePath = fileRelativePath;
        this.filesize = filesize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileRelativePath() {
        return fileRelativePath;
    }

    public void setFileRelativePath(String fileRelativePath) {
        this.fileRelativePath = fileRelativePath;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }
}
