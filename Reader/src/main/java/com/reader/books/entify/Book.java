package com.reader.books.entify;

import java.io.Serializable;

/**
 * 图书实体类
 * Created by john on 2017/5/30.
 */

public class Book implements Serializable{

    private int id;
    private String img;//封面
    private int img1;
    private String title;//书名
    private String sub;//简介
    private String author;
    private String reading;//阅读人数
    private String online;
    private String bytime;//上线时间

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public Book() {
    }
    public Book(int img1,String title){
        this.img1=img1;
        this.title=title;
    }
    public Book(int id, String img, String title, String sub, String author, String reading, String online, String bytime) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.sub = sub;
        this.author = author;
        this.reading = reading;
        this.online = online;
        this.bytime = bytime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub2) {
        this.sub = sub;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getBytime() {
        return bytime;
    }

    public void setBytime(String bytime) {
        this.bytime = bytime;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", img=" + img +
                ", title='" + title + '\'' +
                ", sub='" + sub + '\'' +
                ", author='" + author + '\'' +
                ", reading='" + reading + '\'' +
                ", online='" + online + '\'' +
                ", bytime='" + bytime + '\'' +
                '}';
    }
}
