package com.silverbullet.params.utils;

import com.silverbullet.params.domain.SysParamsDictionary;
import com.silverbullet.utils.BaseDataResult;

import java.util.Map;

/**
 * 对显示内容进行格式化
 * @author jeffyuan
 * @version 1.0
 * @createDate 2019/3/3 14:31
 * @updateUser jeffyuan
 * @updateDate 2019/3/3 14:31
 * @updateRemark
 */
public class SysParamsDictionaryDataFormat {

    /**
     * 根据mapDisplay中的内容替换显示paramsDictionaryBaseDataResult的值
     *
     * @param paramsDictionaryBaseDataResult
     * @param mapTypeDisplay 对应为参数转换map
     * @return com.silverbullet.utils.BaseDataResult<com.silverbullet.params.domain.SysParamsDictionary>
     * @author jeffyuan
     * @createDate 2019/3/3 14:36
     * @updateUser jeffyuan
     * @updateDate 2019/3/3 14:36
     * @updateRemark
     */
    public static BaseDataResult<SysParamsDictionary> formatType(BaseDataResult<SysParamsDictionary> paramsDictionaryBaseDataResult,
                                                             Map<String, String> mapTypeDisplay) {

        for (SysParamsDictionary item : paramsDictionaryBaseDataResult.getResultList()) {
            item.setType(mapTypeDisplay.get(item.getType()));
        }

        return paramsDictionaryBaseDataResult;
    }
}
