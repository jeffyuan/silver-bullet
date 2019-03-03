package com.silverbullet.baidu.services;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.silverbullet.baidu.util.BaiduAiKeyConst;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by jeffyuan on 2017/12/20.
 */
@Service
public class ImageClassifyService {
    public JSONObject getImage(String image) {
        AipImageClassify aipImageClassify = new AipImageClassify(BaiduAiKeyConst.APPID_IMAGECLASSIFY,
                BaiduAiKeyConst.APIKEY_IMAGECLASSIFY, BaiduAiKeyConst.SECREKEY_IMAGECLASSIFY);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("with_face", "1");

        // 参数为本地图片路径
        //String image = "C:\\Users\\GESOFT\\Documents\\GitHub\\springboot-baiduai\\a.jpg";
        JSONObject res = aipImageClassify.objectDetect(image, options);
        System.out.println(res.toString(2));


        // 参数为本地图片二进制数组
        /*byte[] file = readImageFile(image);
        res = client.objectDetect(file,options);
        System.out.println(res.toString(2));*/

        return res;
    }
}
