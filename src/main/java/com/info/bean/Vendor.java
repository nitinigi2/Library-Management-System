package com.info.bean;

import java.util.ArrayList;
import java.util.List;

public class Vendor {
    private String name;
    private String id;
    private List<BookEntity> vendorBookEntityList;


    public Vendor(String name, String id) {
        this.name = name;
        this.id = id;
        this.vendorBookEntityList = new ArrayList<BookEntity>();
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

    public List<BookEntity> getVendorBookEntityList() {
        return vendorBookEntityList;
    }

    public void setVendorBookEntityList(List<BookEntity> vendorBookEntityList) {
        this.vendorBookEntityList = vendorBookEntityList;
    }
}
