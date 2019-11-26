package com.info.view;

import com.info.bean.Customer;
import com.info.utility.CommonUtility;
import com.info.utility.CustomerUtility;

import java.util.Scanner;

public class CustomerView {
    CommonUtility commonUtility = new CommonUtility();
    Scanner scan = new Scanner(System.in);

    public void customerView(String id) {
        CustomerUtility customerUtility = new CustomerUtility();
        System.out.println("Logged in as Customer Successfully.....");


        long option = 0;

        do {
            System.out.println("Choose an option to perform:\n " +
                    "1. Display Profile\n " +
                    "2. Show All available books in Library\n " +
                    "3. Search for a book by name: \n " +
                    "4. Exit\n ");


            System.out.println("Enter an option:");

            try {
                option = Long.parseLong(scan.next().trim());
            } catch (Exception e) {
                System.out.println("Please enter a valid number. ");
            }
            switch ((int) option) {
                case 1:
                    customerUtility.showProfile(id);
                    break;
                case 2:
                    commonUtility.showAllAvailableBooks();
                    break;
                case 3:
                    System.out.print("Enter book Name: ");
                    String bookName = scan.next().trim();
                    commonUtility.searchBookByName(bookName);
                    break;
                default:
                    break;
            }
        } while (option != 4);

        if (option == 4) System.out.println("Logout Successfully. ");

    }
}
