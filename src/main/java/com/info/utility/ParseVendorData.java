package com.info.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.info.bean.BookType;
import com.info.bean.Vendor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.Stateless;
import java.awt.print.Book;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Stateless
public class ParseVendorData {

    Map<String, List<String>> vendorBooks = new LinkedHashMap<>();

    public Map<String, List<BookType>> getVendorsBook() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<BookType>> bookData = mapper.readValue(new File(
                "C:\\Users\\Abyeti\\eclipse-workspace\\Library Management System\\out\\artifacts\\LibraryManagementSystem\\WEB-INF\\vendorData.json"), new TypeReference<Map<String, List<BookType>>>() {
        });

        for(Map.Entry<String, List<BookType>> map : bookData.entrySet()){
            List<String> books = new ArrayList<>();
            for(BookType bookType : map.getValue()){
                books.add(bookType.getBookName());
            }

            vendorBooks.put(map.getKey(), books);
        }

        return bookData;
    }

    public Map<String, List<String>> getVendorBooksName() throws IOException {
        getVendorsBook();
        return vendorBooks;
    }

    public void setVendorsInDB(SessionFactory sessionFactory) throws IOException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<BookType>> bookData = mapper.readValue(new File(
                "vendorData.json"), new TypeReference<Map<String, List<BookType>>>() {
        });

        try{
            for(Map.Entry<String, List<BookType>> map : bookData.entrySet()){
                Vendor newVendor = new Vendor(map.getKey());
                session.save(newVendor);
            }
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();;
        }finally {
            session.close();
        }
    }
}
