package com.silverbullet.ml.impl;

import com.hankcs.hanlp.HanLP;
import com.silverbullet.ml.IMLService;

import java.util.List;

/**
 * Created by jeffyuan on 2018/8/9.
 */
public class MLService implements IMLService {
    /**
     * 提取文档摘要
     *
     * @param document 文档内容，段落通过\n
     * @param size
     * @return
     */
    @Override
    public List<String> extractSummary(String document, int size) {
        return HanLP.extractSummary(document, size);
    }
}
