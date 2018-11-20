package com.silverbullet.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 日志索引类
 * Created by jeffyuan on 2018/11/19.
 */
@Document(indexName = "sbelog", type = "log")
public class SBELog {
    @Id
    @Field(type = FieldType.Keyword)   // 只能检索，不能分词
    private String id;
    private String title;
    private String logCont;

    @Field(type = FieldType.Keyword)  // 只能检索，不能分词
    private String author;
    private Date inputDate;

    private int weight;

    public SBELog() {

    }

    public SBELog(String id, String title, String logCont, String author, Date inputDate, int weight) {
        this.id = id;
        this.title = title;
        this.logCont = logCont;
        this.author = author;
        this.inputDate = inputDate;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogCont() {
        return logCont;
    }

    public void setLogCont(String logCont) {
        this.logCont = logCont;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return title + "<#>" + logCont;
    }
}

