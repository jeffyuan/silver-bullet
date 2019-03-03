package com.silverbullet.baidu.services;

import com.silverbullet.baidu.entity.FileInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffyuan on 2017/12/20.
 */
@Service
public class ImageShowService {

    /**
     * 获取resources下image的文件列表
     * @return
     */
    public List<FileInfo> getImageList() {
        try {
            File file = ResourceUtils.getFile("classpath:static/image");

            List<FileInfo> fileInfos = new ArrayList<FileInfo>();

            String [] fileList = file.list();
            for (String strFile : fileList) {
                FileInfo fi = new FileInfo(strFile, "/image/" + strFile, 0);
                fileInfos.add(fi);
            }

            return fileInfos;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
