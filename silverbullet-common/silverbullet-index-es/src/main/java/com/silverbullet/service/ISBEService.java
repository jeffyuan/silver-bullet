package com.silverbullet.service;

import com.silverbullet.domain.SBELog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 *  事务接口类
 * Created by jeffyuan on 2018/11/19.
 */
public interface ISBEService {
    SBELog save(SBELog book);

    void delete(SBELog book);

    SBELog findOne(String id);

    Iterable<SBELog> findAll();

    Page<SBELog> findByAuthor(String author, PageRequest pageRequest);

    List<SBELog> findByTitle(String title);
}
