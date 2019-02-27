package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysAuthPostAction;

import java.util.List;

/**
 * @Auther: OFG
 * @Despriction:
 * @Data: Created in 15:25 2018/12/14
 * @Modify By:
 **/
public interface ISysAuthPostActionService {


    /**
     * 根据postId查找信息
     * @param postId
     * @return
     */
    public List<SysAuthPostAction> getParamsByPostId(String postId);
}
