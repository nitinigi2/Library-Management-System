package com.info.utility;

import com.info.bean.Book;
import com.info.bean.Customer;

import java.util.ArrayList;
import java.util.Map;

public class CustomerUtility {
    Customer customer = new Customer();
    LibraryUtility LibraryUtility = new LibraryUtility();
    // LoginView loginView = new LoginView();
    // Customer c = loginView.getObjectData();


    public int noOfBooksAvailable() {
        return LibraryUtility.getTotalBooks();
    }

    public void booksIssuedByMe(String customerId) {
        Customer customer = LibraryUtility.getCustomerObjectById(customerId);
        Map<Customer, ArrayList<Book>> map = LibraryUtility.getAllBookCustomerMap();

        //  System.out.println(map);

        // some error is occurring here
        if (!map.containsKey(customer) || map.get(customer).isEmpty()) {
            System.out.println("No book Issued. ");
            return;
        }
        // System.out.println(customer.getMapBookDate());

        System.out.println("Total Number of book issued by me : " + map.get(customer).size());
        System.out.println("BookType Details: ");
        System.out.format("%16s%16s%16s%16s%16s", "Name", "Author", "BookType Id", "Date of Issue", "Bar Code");
        System.out.println();
        /*
        for (BookType bookEntity : map.get(customer)) {
            // System.out.println(bookEntity);
            String date = customer.getMapBookDate().get(bookEntity);
            date = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4);
            System.out.format("%16s%16s%16s%16s", bookEntity.getBookName(), bookEntity.getAuthor(), bookEntity.getBookId(), date);
            System.out.println();
        }
        */

        for(Map.Entry<Book, String> map1: customer.getMapBookDate().entrySet()){
            String date = map1.getValue();
            date = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4);
            System.out.format("%16s%16s%16s%16s", map1.getKey().getBookName(), map1.getKey().getAuthor(), map1.getKey().getBookId(), date, map1.getKey().getBarcode());
            System.out.println();
        }

    }

    // print profile for Cusomter
    public void showProfile(String id) {
        Customer c = LibraryUtility.getCustomerObjectById(id);
        System.out.println("Name :" + c.getName()
                + "\n Mobile Number: " + c.getMobNumber()
                + "\n Address: " + c.getAddress()
                + "\n No of books i can issue: " + c.getNoBooksCanBeIssued());
    }

}
