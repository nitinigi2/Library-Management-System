package com.info.bean;

import com.sun.javafx.collections.MappingChange;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Embeddable
@PrimaryKeyJoinColumn
public class Customer extends User {
    private long mobNumber;
    private String Address;
    private String Dob;
    private int NoBooksCanBeIssued = 3;

	@Transient
    @OneToMany(mappedBy = "issuedby", cascade = CascadeType.ALL)
    List<Book> booksIssuedByCustomer;

    public Customer() {
        super();
        this.booksIssuedByCustomer = new ArrayList<>();
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public List<Book> getBooksIssuedByCustomer() {
        return booksIssuedByCustomer;
    }

    public void setBooksIssuedByCustomer(List<Book> booksIssuedByCustomer) {
        this.booksIssuedByCustomer = booksIssuedByCustomer;
    }

    public int getNoBooksCanBeIssued() {
        return NoBooksCanBeIssued;
    }

    public void setNoBooksCanBeIssued(int noBooksCanBeIssued) {
        NoBooksCanBeIssued = noBooksCanBeIssued;
    }

    public long getMobNumber() {
        return mobNumber;
    }

    public void setMobNumber(long mobNumber) {
        this.mobNumber = mobNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "mobNumber=" + mobNumber +
                ", Address='" + Address + '\'' +
                ", Dob='" + Dob + '\'' +
                ", NoBooksCanBeIssued=" + NoBooksCanBeIssued +
                ", booksIssuedByCustomer=" + booksIssuedByCustomer +
                '}';
    }
}
