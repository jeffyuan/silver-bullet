package com.silverbullet.cms.service.impl;

import com.silverbullet.cms.pojo.CmsFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.silverbullet.cms.config.RepositoryProperties;
import com.silverbullet.cms.service.IFileService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 文件控制类
 * Created by jeffyuan on 2018/3/19.
 */
public class FileServiceImpl implements IFileService {

    @Autowired
    private RepositoryProperties repositoryProperties;

    @Override
    public String saveFile(CmsFileInfo cmsFileInfo) throws IOException {
        if (cmsFileInfo.getInputStream() == null || cmsFileInfo.getFileUrlShort() == null
                || cmsFileInfo.getFileUrlShort().length() < 32) {
            return null;
        }

        String relativePath = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());

        String absPath = repositoryProperties.getPath() + File.separator + relativePath;
        File fileSave = new File(absPath);
        if (!fileSave.exists()) {
            fileSave.mkdirs();
        }

        fileSave = new File(absPath + File.separator + cmsFileInfo.getFileUrlShort());
        FileOutputStream fileOutputStream = new FileOutputStream (fileSave);

        int bytesWritten = 0;
        int byteCount = 0;
        byte[] bytes = new byte[1024];
        while((byteCount = cmsFileInfo.getInputStream().read(bytes)) != -1){

            fileOutputStream.write(bytes, 0, byteCount);
            bytesWritten += byteCount;
        }

        cmsFileInfo.setFileLen((float)bytesWritten);

        return "/" + relativePath + "/" + cmsFileInfo.getFileUrlShort();
    }

    @Override
    public InputStream getFile(String filePath) throws FileNotFoundException {

        String absPath = repositoryProperties.getPath() + filePath;
        File file = new File(absPath);
        if (!file.exists()) {
            return null;
        }

        InputStream inputStream = new FileInputStream(file);

        return inputStream;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件相对路径
     * @return
     */
    @Override
    public boolean deleteFile(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return true;
        }

        String absPath = repositoryProperties.getPath() + File.separator + filePath;
        File fileSave = new File(absPath);
        if (fileSave.exists()) {
            fileSave.delete();
        }

        return true;
    }
}
