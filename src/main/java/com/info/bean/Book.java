package com.info.bean;

import javax.persistence.*;

@Entity
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int barcode;
    private String DateOfIssue;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer issuedby;
    private boolean canBeIssued;

    @ManyToOne
    private BookType bookType;


    public Book(){}

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getIssuedby() {
        return issuedby;
    }

    public boolean isCanBeIssued() {
        return canBeIssued;
    }

    public boolean getCanBeIssued() {
        return canBeIssued;
    }

    public void setCanBeIssued(boolean canBeIssued) {
        this.canBeIssued = canBeIssued;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public String getDateOfIssue() {
        return DateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        DateOfIssue = dateOfIssue;
    }

    public void setIssuedby(Customer issuedby) {
        this.issuedby = issuedby;
    }

}
