package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysIcon;
import com.silverbullet.utils.BaseDataResult;

/**
 * @Auther: OFG
 * @Despriction: 图标管理接口
 * @Data: Created in 14:58 2018/9/7
 * @Modify By:
 **/
public interface ISysIcon {

    public BaseDataResult<SysIcon> list();
}
