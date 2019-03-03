package com.silverbullet.ml;

import java.util.List;

/**
 * Created by jeffyuan on 2018/8/9.
 */
public interface IMLService {
    /**
     * 提取文档摘要
     * @param document 文档内容，段落通过\n
     * @param size
     * @return
     */
    public List<String> extractSummary(String document, int size);
}
