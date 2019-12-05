package com.info.utility;

import com.info.bean.Book;
import com.info.bean.BookType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonUtility{
    LibraryUtility libraryUtility = new LibraryUtility();


    private Map<String, ArrayList<BookType>> bookListByName = libraryUtility.getSearchBookByBookNameList();

    public void showAllAvailableBooks(SessionFactory sessionFactory) {

        Session session = sessionFactory.openSession();

        // creating transaction object
        Transaction t = session.beginTransaction();

        Query query = session.createQuery("from BookType");
        List list = query.list();

        System.out.println(list);
        t.commit();
        session.close();

        ArrayList<BookType> bookEntities = libraryUtility.getBookList();
        // System.out.println(bookmap);
        System.out.println("All available Books in Library : ");
        System.out.format("%16s%16s%16s%32s", "Name", "Author", "BookType Id", "Number of Books");
        System.out.println();
        for (BookType bookType : bookEntities) {
            System.out.format("%16s%16s%16s%32s", bookType.getBookName(), bookType.getAuthor(), bookType.getBookId(), bookType.getBookQuantity());
            System.out.println();
        }

    }

    public void searchBookBySUbject(String bookName, SessionFactory sessionFactory){

        Session session = sessionFactory.openSession();

        // creating transaction object
        Transaction t = session.beginTransaction();

        Query query = session.createQuery("from BookType where bookname = :bookname");
        query.setParameter("bookname", bookName);
        List list = query.list();
        System.out.println(list);
        t.commit();
        session.close();



        if(!bookListByName.containsKey(bookName)){
            System.out.println(bookName + " book is not available. ");
            return;
        }

        System.out.format("%16s%16s%16s%32s", "Name", "Author", "BookType Id", "Number of Books");
        System.out.println();
        if(bookListByName.containsKey(bookName)) {
            for(BookType bookType : bookListByName.get(bookName)){
                System.out.format("%16s%16s%16s%32s", bookType.getBookName(), bookType.getAuthor(), bookType.getBookId(), bookType.getBookQuantity());
                System.out.println();
            }
        }
    }


}
