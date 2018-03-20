package com.silverbullet.cms.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 控制文件存储
 * Created by jeffyuan on 2018/3/19.
 */
public interface IFileService {
    /**
     * 存储文件
     * @param inputStream 文件流
     * @return 存储相对路径例如  /2018/03/19/file.bin
     */
    public String saveFile(InputStream inputStream) throws IOException;

    /**
     * 获取文件
     * @param filePath 文件相对地址
     * @return
     */
    public InputStream getFile(String filePath) throws FileNotFoundException;
}
