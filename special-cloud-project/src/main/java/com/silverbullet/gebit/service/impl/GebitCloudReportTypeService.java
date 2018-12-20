package com.silverbullet.gebit.service.impl;

import com.silverbullet.gebit.dao.GebitCloudReportTypeMapper;
import com.silverbullet.gebit.service.IGebitCloudReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: OFG
 * @Despriction: 分类管理service接口
 * @Data: Created in 17:21 2018/12/19
 * @Modify By:
 **/
@Service
public class GebitCloudReportTypeService implements IGebitCloudReportTypeService {


    @Autowired
    private GebitCloudReportTypeMapper gebitCloudReportTypeMapper;
}
