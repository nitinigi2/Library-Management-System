package com.info.bean;

import javax.persistence.*;

@Entity
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int barcode;
    private String DateOfIssue;

    @OneToOne(fetch = FetchType.EAGER)
    private Customer issuedby;
    private boolean canBeIssued;
    private String bookName;
    private String author;
    private String bookId;
    private double price;


    public Book(){}

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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", barcode=" + barcode +
                ", DateOfIssue='" + DateOfIssue + '\'' +
                ", issuedby=" + issuedby +
                ", canBeIssued=" + canBeIssued +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", bookId='" + bookId + '\'' +
                ", price=" + price +
                '}';
    }
}
