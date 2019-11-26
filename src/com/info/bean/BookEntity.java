package com.info.bean;

public class BookEntity {
	private String bookName;
	private String author;
	private String bookId;
	private int bookQuantity;
	private double price;
	private String title;


	public BookEntity(BookEntity copyBookEntity){
		this.bookQuantity = copyBookEntity.bookQuantity;
		this.bookName = copyBookEntity.bookName;
		this.author = copyBookEntity.author;
		this.bookId = copyBookEntity.bookId;
		this.price = copyBookEntity.price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getBookQuantity() {
		return bookQuantity;
	}

	public void setBookQuantity(int bookQuantity) {
		this.bookQuantity = bookQuantity;
	}

	public BookEntity() {
		super();
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
	
	
}
