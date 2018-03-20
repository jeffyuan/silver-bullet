package com.silverbullet.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.silverbullet.cms.config.RepositoryProperties;
import com.silverbullet.cms.service.IFileService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * 文件控制类
 * Created by jeffyuan on 2018/3/19.
 */
public class FileServiceImpl implements IFileService {

    @Autowired
    private RepositoryProperties repositoryProperties;

    @Override
    public String saveFile(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }

        String fileName = UUID.randomUUID().toString() + ".bin";
        String relativePath = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());

        String absPath = repositoryProperties.getPath() + File.separator + relativePath;
        File fileSave = new File(absPath);
        if (!fileSave.exists()) {
            fileSave.mkdirs();
        }

        fileSave = new File(absPath + File.separator + fileName);
        FileOutputStream fileOutputStream = new FileOutputStream (fileSave);

        int bytesWritten = 0;
        int byteCount = 0;
        byte[] bytes = new byte[1024];
        while((inputStream.read(bytes)) != -1){

            fileOutputStream.write(bytes, bytesWritten, byteCount);
            bytesWritten += byteCount;
        }

        return File.separator + relativePath + File.separator + fileName;
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
}
