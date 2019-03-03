package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthPostAction;
import com.silverbullet.data.repository.CrudRepository;

import java.util.List;

public interface SysAuthPostActionMapper extends CrudRepository<SysAuthPostAction, String>{

    List findListByPostId(String postId);

}