package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthUserPost;
import com.silverbullet.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface SysAuthUserPostMapper extends CrudRepository<SysAuthUserPost, Integer>{
  public List<Map<String, String>> findListByUserId(Integer id);
  public int deleteByUserId(Integer id);
  public String getUserPostId(Integer id);
}