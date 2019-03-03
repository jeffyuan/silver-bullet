package com.silverbullet.baidu.services;

import com.baidu.aip.ocr.AipOcr;
import com.silverbullet.baidu.util.BaiduAiKeyConst;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by jeffyuan on 2017/12/20.
 */
@Service
public class OcrService {
    /**
     * 识别通用文字
     * @param imgPath
     * @return
     */
    public JSONObject generalRecognition(String imgPath) {

        AipOcr aipOcr = new AipOcr(BaiduAiKeyConst.APPID_OCR, BaiduAiKeyConst.APIKEY_OCR, BaiduAiKeyConst.SECREKEY_OCR);

        // 参数为本地图片路径
        //String imagePath = "general.jpg";
        JSONObject response1 = aipOcr.basicGeneral(imgPath, new HashMap<String, String>());

      /*  // 参数为本地图片文件二进制数组
        byte[] file = readImageFile(imagePath);
        JSONObject response2 = client.basicGeneral(file, new HashMap<String, String>());
        System.out.println(response2.toString());

        // 参数为图片url
        String url = "http://some.com/a.jpg";
        JSONObject response3 = client.basicGeneralUrl(url, new HashMap<String, String>());
        System.out.println(response3.toString());*/

        return response1;
    }


    /**
     * 精度识别
     * @param imagePath
     * @return
     */
    public JSONObject accurateRecognition(String imagePath) {
        // 参数为本地图片路径
       // String imagePath = "general.jpg";

        AipOcr aipOcr = new AipOcr(BaiduAiKeyConst.APPID_OCR, BaiduAiKeyConst.APIKEY_OCR, BaiduAiKeyConst.SECREKEY_OCR);

        JSONObject response = aipOcr.basicAccurateGeneral(imagePath, new HashMap<String, String>());
        //System.out.println(response.toString());

        // 参数为本地图片文件二进制数组
//        byte[] file = readImageFile(imagePath);
//        JSONObject response = client.basicAccurateGeneral(file);
//        System.out.println(response.toString());

        return response;
    }

    public void tableRecognition(String imgPath) {
        AipOcr aipOcr = new AipOcr(BaiduAiKeyConst.APPID_OCR, BaiduAiKeyConst.APIKEY_OCR, BaiduAiKeyConst.SECREKEY_OCR);
        JSONObject res = aipOcr.tableRecognitionAsync(imgPath);

        // res为使用tableRecognitionAsync接口的json返回值
        String reqId = res.getJSONArray("result").getJSONObject(0).getString("request_id");
        // 获取json结果
        System.out.println(aipOcr.getTableRecognitionJsonResult(reqId));
        // 获取excel结果
        System.out.println(aipOcr.getTableRecognitionExcelResult(reqId));

    }
}
