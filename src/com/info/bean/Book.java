package com.info.bean;

public class Book extends BookEntity{
    private String barcode;
    private String DateOfIssue;
    private Customer occupiedBy;
    private boolean canBeIssued;


    public boolean getCanBeIssued() {
        return canBeIssued;
    }

    public void setCanBeIssued(boolean canBeIssued) {
        this.canBeIssued = canBeIssued;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDateOfIssue() {
        return DateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        DateOfIssue = dateOfIssue;
    }

    public Customer getOccupiedBy() {
        return occupiedBy;
    }

    public void setOccupiedBy(Customer occupiedBy) {
        this.occupiedBy = occupiedBy;
    }
}
