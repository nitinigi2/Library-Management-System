package com.info.utility;

import com.info.bean.BookType;
import com.info.bean.Vendor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class VendorData {
    private static ArrayList<Vendor> vendorArrayList = new ArrayList<Vendor>();
    private static ArrayList<BookType> booksList = new ArrayList<BookType>();

    public ArrayList<Vendor> getVendorArrayList() {
        return vendorArrayList;
    }

    public ArrayList<BookType> getBooksList() {
        return booksList;
    }

    public void generateBookList(SessionFactory sessionFactory) {
        BookType bookType1 = new BookType();
        bookType1.setBookName("Fundamentals of Physics");
        bookType1.setAuthor("ABC");
        bookType1.setPrice(500);
        bookType1.setBookQuantity(10);
        bookType1.setSubject("Physics");
        bookType1.setBookId("book1id");

        BookType bookType2 = new BookType();
        bookType2.setBookName("Fundamentals of Chemistry");
        bookType2.setAuthor("ABCD");
        bookType2.setPrice(610);
        bookType2.setBookQuantity(20);
        bookType2.setSubject("Chemistry");
        bookType2.setBookId("book2id");

        BookType bookType3 = new BookType();
        bookType3.setBookName("Let us C");
        bookType3.setAuthor("ABCDE");
        bookType3.setPrice(700);
        bookType3.setBookQuantity(15);
        bookType3.setSubject("C");
        bookType3.setBookId("book3id");

        BookType bookType4 = new BookType();
        bookType4.setBookName("RD Sharma");
        bookType4.setAuthor("AB");
        bookType4.setPrice(390);
        bookType4.setBookQuantity(130);
        bookType4.setSubject("Math");
        bookType4.setBookId("book4id");

        BookType bookType5 = new BookType();
        bookType5.setBookName("Learn English");
        bookType5.setAuthor("AS");
        bookType5.setPrice(450);
        bookType5.setSubject("English");
        bookType5.setBookQuantity(100);
        bookType5.setBookId("book5id");


        BookType bookType6 = new BookType();
        bookType6.setBookName("Core Java Basics");
        bookType6.setAuthor("UVW");
        bookType6.setPrice(500);
        bookType6.setSubject("Java");
        bookType6.setBookQuantity(20);
        bookType6.setBookId("book6id");

        BookType bookType7 = new BookType();
        bookType7.setBookName("Python- As a snake");
        bookType7.setSubject("Python");
        bookType7.setAuthor("ABCRD");
        bookType7.setPrice(610);
        bookType7.setBookQuantity(20);
        bookType7.setBookId("book7id");

        BookType bookType8 = new BookType();
        bookType8.setBookName("Learn Algorithm");
        bookType8.setSubject("Algorithm");
        bookType8.setAuthor("ABCDE");
        bookType8.setPrice(730);
        bookType8.setBookQuantity(15);
        bookType8.setBookId("book8id");

        BookType bookType9 = new BookType();
        bookType9.setBookName("Learn Data Structures");
        bookType9.setSubject("Data Structures");
        bookType9.setAuthor("AB");
        bookType9.setPrice(340);
        bookType9.setBookQuantity(70);
        bookType9.setBookId("book9id");

        BookType bookType10 = new BookType();
        bookType10.setBookName("Fundamentals of C++");
        bookType10.setAuthor("AS");
        bookType10.setSubject("C++");
        bookType10.setPrice(650);
        bookType10.setBookQuantity(60);
        bookType10.setBookId("book10id");

        booksList.add(bookType1);
        booksList.add(bookType2);
        booksList.add(bookType3);
        booksList.add(bookType4);
        booksList.add(bookType5);

        booksList.add(bookType6);
        booksList.add(bookType7);
        booksList.add(bookType8);
        booksList.add(bookType9);
        booksList.add(bookType10);


        /*Session session = sessionFactory.openSession();
        session.beginTransaction();

        for(BookType bookEntity : booksList){
            session.save(bookEntity);
        }
        session.close();

         */
    }


    public void generateVendorData(SessionFactory sessionFactory) {
        Vendor vendor1 = new Vendor("Vendor1", "vendor1");
        vendor1.setVendorBookTypeList(getBooksList().subList(0,2));

        Vendor vendor2 = new Vendor("Vendor2", "vendor2");
        vendor2.setVendorBookTypeList(getBooksList().subList(2, 4));

        Vendor vendor3 = new Vendor("Vendor3", "vendor3");
        vendor3.setVendorBookTypeList( getBooksList().subList(4,6));

        Vendor vendor4 = new Vendor("Vendor4", "vendor4");
        vendor4.setVendorBookTypeList(getBooksList().subList(6,8));

        Vendor vendor5 = new Vendor("Vendor5", "vendor5");
        vendor5.setVendorBookTypeList(getBooksList().subList(8,10));

        vendorArrayList.add(vendor1);
        vendorArrayList.add(vendor2);
        vendorArrayList.add(vendor3);
        vendorArrayList.add(vendor4);
        vendorArrayList.add(vendor5);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for(Vendor vendor : vendorArrayList){
            session.save(vendor);
        }
        session.close();
    }

    public void addVendorWithBook(SessionFactory sessionFactory, Vendor vendor, BookType bookType){
        if(vendorArrayList.contains(vendor)){
            vendor.getVendorBookTypeList().add(bookType);

        }
        else getVendorArrayList().add(vendor);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(vendor);
        session.close();
    }

}
