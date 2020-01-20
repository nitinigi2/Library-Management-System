package com.info.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Embeddable
public class BookType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int book_entity_id;
	private String bookName;
	private String author;
	private String bookId;
	private String subject;
	private int bookQuantity;
	private double price;
	@ManyToOne
	private Vendor vendor;

	//@OneToMany
	//private List<Book> bookList;

	public BookType(BookType copyBookType){
		this.bookQuantity = copyBookType.bookQuantity;
		this.bookName = copyBookType.bookName;
		this.author = copyBookType.author;
		this.bookId = copyBookType.bookId;
		this.price = copyBookType.price;
		this.subject = copyBookType.subject;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	/*
        public List<Book> getBookList() {
            return bookList;
        }


        public void setBookList(List<Book> bookList) {
            this.bookList = bookList;
        }
        */

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getBook_entity_id() {
		return book_entity_id;
	}

	public void setBook_entity_id(int book_entity_id) {
		this.book_entity_id = book_entity_id;
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

	public BookType() {
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

	@Override
	public String toString() {
		return "BookType{" +
				"book_entity_id=" + book_entity_id +
				", bookName='" + bookName + '\'' +
				", author='" + author + '\'' +
				", bookId='" + bookId + '\'' +
				", subject='" + subject + '\'' +
				", bookQuantity=" + bookQuantity +
				", price=" + price +
				", vendor=" + vendor +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookType bookType = (BookType) o;
		return book_entity_id == bookType.book_entity_id &&
				bookQuantity == bookType.bookQuantity &&
				Double.compare(bookType.price, price) == 0 &&
				Objects.equals(bookName, bookType.bookName) &&
				Objects.equals(author, bookType.author) &&
				Objects.equals(bookId, bookType.bookId) &&
				Objects.equals(subject, bookType.subject) &&
				Objects.equals(vendor, bookType.vendor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(book_entity_id, bookName, author, bookId, subject, bookQuantity, price, vendor);
	}
}
