package com.reader.books.entify;

/**
 * 图书种类汇总
 * Created by Administrator on 2017/6/4.
 */

public class BookSpecies {
    private int id;
    private String catalog;

    public BookSpecies() {
    }

    public BookSpecies(int id, String catalog) {
        this.id = id;
        this.catalog = catalog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return "BookSpecies{" +
                "id=" + id +
                ", catalog='" + catalog + '\'' +
                '}';
    }
}
