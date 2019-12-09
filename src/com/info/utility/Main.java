package com.info.utility;

import com.info.bean.Librarian;
import com.info.view.CustomerView;
import com.info.view.LibraryView;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        LibraryView libraryView = new LibraryView();
        CustomerView customerView = new CustomerView();
        Librarian librarian = new Librarian();
        LibraryUtility libraryUtility = new LibraryUtility();
        librarian.setId("1234");
        librarian.setPassword("password");

        Scanner scan = new Scanner(System.in);

        System.out.println("_______________________Digital Library_______________________________\n");

        LibraryUtility lib = new LibraryUtility();
        lib.addVendorData();   // to load vendor data only 1 time

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
                        libraryView.view(id);
                    else System.out.println("wrong id/password");
                    break;

                case 2 :
                    System.out.print("Enter Id: ");
                    String cusId = scan.next().trim();

                    System.out.print("Enter password: ");
                    String pwd = scan.next().trim();

                    if(libraryUtility.isValidCustomerId(cusId) && libraryUtility.getCustomerObjectById(cusId).getPassword().equals(pwd)){
                        customerView.customerView(cusId);
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
