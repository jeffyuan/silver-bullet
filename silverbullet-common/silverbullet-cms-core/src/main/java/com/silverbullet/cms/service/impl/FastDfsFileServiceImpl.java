package com.silverbullet.cms.service.impl;

import com.silverbullet.cms.pojo.CmsFileInfo;
import com.silverbullet.cms.service.IFileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jeffyuan on 2018/3/19.
 */
public class FastDfsFileServiceImpl implements IFileService {


    @Override
    public String saveFile(CmsFileInfo cmsFileInfo) throws IOException {
        return null;
    }

    @Override
    public InputStream getFile(String filePath) throws FileNotFoundException {
        return null;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件相对路径
     * @return
     */
    @Override
    public boolean deleteFile(String filePath) {
        return false;
    }
}
