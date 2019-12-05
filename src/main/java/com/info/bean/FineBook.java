package com.info.bean;

import java.util.Date;

public class FineBook {
    private BookType bookType;
    private Date date;

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
