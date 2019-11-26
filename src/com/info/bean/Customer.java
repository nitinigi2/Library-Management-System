package com.info.bean;

import java.util.HashMap;
import java.util.Map;

public class Customer extends User{
	private long mobNumber;
	private String Address;
	private String Dob;
	private int NoBooksCanBeIssued=3;
	private Map<Book, String> mapBookDate;


	public String getDob() {
		return Dob;
	}

	public void setDob(String dob) {
		Dob = dob;
	}

	public Map<Book, String> getMapBookDate() {
		return mapBookDate;
	}

	public void setMapBookDate(Map<Book, String> mapBookDate) {
		this.mapBookDate = mapBookDate;
	}

	public int getNoBooksCanBeIssued() {
		return NoBooksCanBeIssued;
	}

	public void setNoBooksCanBeIssued(int noBooksCanBeIssued) {
		NoBooksCanBeIssued = noBooksCanBeIssued;
	}

	public Customer() {
		super();
		this.mapBookDate = new HashMap<>();
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

}
