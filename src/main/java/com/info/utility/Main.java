package com.info.utility;

import com.info.bean.Librarian;
import com.info.view.CustomerView;
import com.info.view.LibraryView;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        SessionClass sessionClass = new SessionClass();

        sessionClass.createSessionFactoryObject();

        SessionFactory sessionFactory = sessionClass.getSessionFactoryObject();

        // reading vendors bookData
        ParseVendorData parseVendorData = new ParseVendorData();


        LibraryView libraryView = new LibraryView();
        CustomerView customerView = new CustomerView();
        Librarian librarian = new Librarian();
        LibraryUtility libraryUtility = new LibraryUtility();
        librarian.setId("admin");
        librarian.setPassword("password");

        Scanner scan = new Scanner(System.in);

        System.out.println("_______________________Digital Library_______________________________\n");

        LibraryUtility lib = new LibraryUtility();
        //to be commented once data is loaded
        lib.addVendorData(sessionFactory);   // to load vendor data only 1 time

        int option = 0;

        do {
            System.out.println();
            System.out.println("Please login to system first: ");
            System.out.println("You Are? :");
            System.out.println("1. Librarian\n" +
                    "2. Customer\n" +
                    "3. Exit\n");

            System.out.println("Enter a option: ");

            try {
                option = Integer.parseInt(scan.next());
            } catch (Exception e) {
                System.out.println("Please enter a valid option. ");
            }

            switch (option) {
                case 1:
                    System.out.print("Enter Id: ");
                    String id = scan.next().trim();
                    System.out.print("Enter password: ");
                    String password = scan.next().trim();
                    if (librarian.getId().equals(id) && librarian.getPassword().equals(password))
                        libraryView.view(id, sessionFactory);
                    else System.out.println("wrong id/password");
                    break;

                case 2 :
                    System.out.print("Enter Id: ");
                    id= scan.next().trim();

                    System.out.print("Enter password: ");
                    password = scan.next().trim();

                    if(libraryUtility.isValidCustomerId(id, sessionFactory) && libraryUtility.isValidCustomerPassword(id, password, sessionFactory)){
                        customerView.customerView(id, sessionFactory);
                    }
                    else{
                        System.out.println("Wrong id/password");
                    }
                    break;
            }
        } while (option != 3);

        if (option == 3) System.out.println("Exit Successfully. ");

    }
}
