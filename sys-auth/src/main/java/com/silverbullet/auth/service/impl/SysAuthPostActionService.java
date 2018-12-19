package com.silverbullet.auth.service.impl;

import com.silverbullet.auth.dao.SysAuthPostActionMapper;
import com.silverbullet.auth.domain.SysAuthPostAction;
import com.silverbullet.auth.service.ISysAuthPostActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: OFG
 * @Despriction:
 * @Data: Created in 15:25 2018/12/14
 * @Modify By:
 **/
@Service
public class SysAuthPostActionService implements ISysAuthPostActionService {


    @Autowired
    private SysAuthPostActionMapper sysAuthPostActionMapper;

    /**
     * 根据postId查找信息
     *
     * @param postId
     * @return
     */
    @Override
    public List<SysAuthPostAction> getParamsByPostId(String postId) {
        return sysAuthPostActionMapper.findListByPostId(postId);
    }
}
