package com.silverbullet.utils;

import java.util.List;

/**
 * Created by jeffyuan on 2018/3/7.
 */
public class BaseDataResult<T> {

    protected List<T> resultList = null;
    protected long totalNum = 0;

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }
}
