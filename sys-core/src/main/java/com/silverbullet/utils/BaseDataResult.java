package com.silverbullet.utils;

import java.util.List;

/**
 * Created by jeffyuan on 2018/3/7.
 */
public class BaseDataResult<T> {

    protected List<T> resultList = null;
    protected int totalNum = 0;

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
