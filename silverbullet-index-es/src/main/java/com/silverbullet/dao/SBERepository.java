package com.silverbullet.dao;

import com.silverbullet.domain.SBELog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * es 事务方法
 * Created by jeffyuan on 2018/11/19.
 */
public interface SBERepository extends ElasticsearchRepository<SBELog, String> {
    Page<SBELog> findByAuthor(String author, Pageable pageable);
    List<SBELog> findByTitle(String title);

}
