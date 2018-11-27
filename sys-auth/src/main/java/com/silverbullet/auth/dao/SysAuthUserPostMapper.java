package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.auth.domain.SysAuthUserPost;
import com.silverbullet.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface SysAuthUserPostMapper extends CrudRepository<SysAuthUserPost, String>{
  public List<Map<String, String>> findListByUserId(String id);
  public int deleteByUserId(String id);
  public String getUserPostId(String id);
}