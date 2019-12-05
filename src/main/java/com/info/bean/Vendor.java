package com.info.bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vendor_id;
    private String name;
    private String id;

    @OneToMany(mappedBy = "vendor")
    private List<BookType> vendorBookTypeList;

    public Vendor(){}

    public Vendor(String name, String id) {
        this.name = name;
        this.id = id;
        this.vendorBookTypeList = new ArrayList<BookType>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BookType> getVendorBookTypeList() {
        return vendorBookTypeList;
    }

    public void setVendorBookTypeList(List<BookType> vendorBookTypeList) {
        this.vendorBookTypeList = vendorBookTypeList;
    }
}
