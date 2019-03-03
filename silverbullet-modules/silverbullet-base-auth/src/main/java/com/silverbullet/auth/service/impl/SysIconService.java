package com.silverbullet.auth.service.impl;

import com.silverbullet.auth.dao.SysIconMapper;
import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.auth.domain.SysIcon;
import com.silverbullet.auth.service.ISysIcon;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: OFG
 * @Despriction:
 * @Data: Created in 15:01 2018/9/7
 * @Modify By:
 **/
@Service
public class SysIconService implements ISysIcon{

    @Autowired
    SysIconMapper sysIconMapper;

    @Override
    public BaseDataResult<SysIcon> list() {

        BaseDataResult<SysIcon> listResults = new BaseDataResult<SysIcon>();
        listResults.setResultList(sysIconMapper.findList());
        return listResults;
    }
}
