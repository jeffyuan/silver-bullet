package com.silverbullet.gebit.dao;

import com.silverbullet.data.repository.CrudRepository;
import com.silverbullet.gebit.domain.GebitReportType;
import com.silverbullet.gebit.service.IGebitCloudReportTypeService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: OFG
 * @Despriction:
 * @Data: Created in 17:25 2018/12/19
 * @Modify By:
 **/
@Mapper
public interface GebitCloudReportTypeMapper extends CrudRepository<GebitReportType, String> {


}
