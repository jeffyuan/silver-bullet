package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface SysAuthActionMapper extends CrudRepository<SysAuthAction, String>{
    List<SysAuthAction> findListByPostId(String postId);
}