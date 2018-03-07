package com.silverbullet.data.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by jeffyuan on 2018/3/6.
 */
public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> {

    int countNum();

    List<T> findList();

    int deleteByPrimaryKey(ID id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
