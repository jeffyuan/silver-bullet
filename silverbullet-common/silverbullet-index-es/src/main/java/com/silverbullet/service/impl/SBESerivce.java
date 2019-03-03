package com.silverbullet.service.impl;

import com.silverbullet.dao.SBERepository;
import com.silverbullet.domain.SBELog;
import com.silverbullet.service.ISBEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 事务类防范
 * Created by jeffyuan on 2018/11/19.
 */
@Service
public class SBESerivce implements ISBEService {

    @Autowired
    private SBERepository sbeRepository;

    @Override
    public SBELog save(SBELog book) {
        return sbeRepository.save(book);
    }

    @Override
    public void delete(SBELog book) {
        sbeRepository.delete(book);
    }

    @Override
    public SBELog findOne(String id) {
        return sbeRepository.findById(id).get();
    }

    @Override
    public Iterable<SBELog> findAll() {
        return sbeRepository.findAll();
    }

    @Override
    public Page<SBELog> findByAuthor(String author, PageRequest pageRequest) {
        return sbeRepository.findAll(pageRequest);
    }

    @Override
    public List<SBELog> findByTitle(String title) {
        return sbeRepository.findByTitle(title);
    }
}
