package com.info.utility;

import com.info.bean.BookEntity;
import com.info.bean.Vendor;

import java.util.ArrayList;

public class VendorList {
    private static ArrayList<Vendor> vendorArrayList = new ArrayList<>();
    private static ArrayList<BookEntity> booksList = new ArrayList<>();

    public ArrayList<Vendor> getVendorArrayList() {
        return vendorArrayList;
    }

    public ArrayList<BookEntity> getBooksList() {
        return booksList;
    }

    public void bookListData() {
        BookEntity bookEntity1 = new BookEntity();
        bookEntity1.setBookName("Physics");
        bookEntity1.setAuthor("ABC");
        bookEntity1.setPrice(500);
        bookEntity1.setBookQuantity(10);
        bookEntity1.setBookId("book1id");

        BookEntity bookEntity2 = new BookEntity();
        bookEntity2.setBookName("Chemistry");
        bookEntity2.setAuthor("ABCD");
        bookEntity2.setPrice(610);
        bookEntity2.setBookQuantity(20);
        bookEntity2.setBookId("book2id");

        BookEntity bookEntity3 = new BookEntity();
        bookEntity3.setBookName("Let us C");
        bookEntity3.setAuthor("ABCDE");
        bookEntity3.setPrice(700);
        bookEntity3.setBookQuantity(15);
        bookEntity3.setBookId("book3id");

        BookEntity bookEntity4 = new BookEntity();
        bookEntity4.setBookName("Math");
        bookEntity4.setAuthor("AB");
        bookEntity4.setPrice(390);
        bookEntity4.setBookQuantity(130);
        bookEntity4.setBookId("book4id");

        BookEntity bookEntity5 = new BookEntity();
        bookEntity5.setBookName("English");
        bookEntity5.setAuthor("AS");
        bookEntity5.setPrice(450);
        bookEntity5.setBookQuantity(100);
        bookEntity5.setBookId("book5id");


        BookEntity bookEntity6 = new BookEntity();
        bookEntity6.setBookName("Java");
        bookEntity6.setAuthor("UVW");
        bookEntity6.setPrice(500);
        bookEntity6.setBookQuantity(20);
        bookEntity6.setBookId("book6id");

        BookEntity bookEntity7 = new BookEntity();
        bookEntity7.setBookName("Python");
        bookEntity7.setAuthor("ABCRD");
        bookEntity7.setPrice(610);
        bookEntity7.setBookQuantity(20);
        bookEntity7.setBookId("book7id");

        BookEntity bookEntity8 = new BookEntity();
        bookEntity8.setBookName("Algorithm");
        bookEntity8.setAuthor("ABCDE");
        bookEntity8.setPrice(730);
        bookEntity8.setBookQuantity(15);
        bookEntity8.setBookId("book8id");

        BookEntity bookEntity9 = new BookEntity();
        bookEntity9.setBookName("Data Structures");
        bookEntity9.setAuthor("AB");
        bookEntity9.setPrice(340);
        bookEntity9.setBookQuantity(70);
        bookEntity9.setBookId("book9id");

        BookEntity bookEntity10 = new BookEntity();
        bookEntity10.setBookName("C++");
        bookEntity10.setAuthor("AS");
        bookEntity10.setPrice(650);
        bookEntity10.setBookQuantity(60);
        bookEntity10.setBookId("book10id");

        booksList.add(bookEntity1);
        booksList.add(bookEntity2);
        booksList.add(bookEntity3);
        booksList.add(bookEntity4);
        booksList.add(bookEntity5);

        booksList.add(bookEntity6);
        booksList.add(bookEntity7);
        booksList.add(bookEntity8);
        booksList.add(bookEntity9);
        booksList.add(bookEntity10);
    }


    public void vendorListData() {
        Vendor vendor1 = new Vendor("Vendor1", "vendor1");
        vendor1.setVendorBookEntityList(getBooksList().subList(0,2));

        Vendor vendor2 = new Vendor("Vendor2", "vendor2");
        vendor2.setVendorBookEntityList(getBooksList().subList(2, 4));

        Vendor vendor3 = new Vendor("Vendor3", "vendor3");
        vendor3.setVendorBookEntityList( getBooksList().subList(4,6));

        Vendor vendor4 = new Vendor("Vendor4", "vendor4");
        vendor4.setVendorBookEntityList(getBooksList().subList(6,8));

        Vendor vendor5 = new Vendor("Vendor5", "vendor5");
        vendor5.setVendorBookEntityList(getBooksList().subList(8,10));

        vendorArrayList.add(vendor1);
        vendorArrayList.add(vendor2);
        vendorArrayList.add(vendor3);
        vendorArrayList.add(vendor4);
        vendorArrayList.add(vendor5);
    }

}
