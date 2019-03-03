package com.silverbullet.params.dao;

import com.silverbullet.params.domain.SysParamsDictionaryItem;
import com.silverbullet.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface SysParamsDictionaryItemMapper extends CrudRepository<SysParamsDictionaryItem, String>{
    public List<SysParamsDictionaryItem> findListByDictKeyId(String dictKeyId);
    public int countNumByDictKeyId(String dictKeyId);
}