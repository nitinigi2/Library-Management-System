package com.info.view;

import com.info.utility.CommonUtility;
import com.info.utility.CustomerUtility;
import com.info.utility.LoginUtility;
import org.hibernate.SessionFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Scanner;

@ManagedBean
@SessionScoped
public class CustomerView implements Serializable {
    CommonUtility commonUtility = new CommonUtility();
    LoginUtility loginUtility = new LoginUtility();
    CustomerUtility customerUtility = new CustomerUtility();
    Scanner scan = new Scanner(System.in);

    public void showProfile(String username){
        SessionFactory sessionFactory = loginUtility.getSessionfactory();
        customerUtility.showProfile(username, sessionFactory);
    }

    public void customerView(String id, SessionFactory sessionFactory) {

        System.out.println("Logged in as Customer Successfully.....");


        long option = 0;

        do {
            System.out.println("Choose an option to perform:\n " +
                    "1. Display Profile\n " +
                    "2. Show All available books in Library\n " +
                    "3. Search book by book name: \n " +
                    "4. Search book by author Name:\n " +
                    "5. Search book by Subject name:\n " +
                    "6. Exit\n ");


            System.out.println("Enter an option:");

            try {
                option = Long.parseLong(scan.next().trim());
            } catch (Exception e) {
                System.out.println("Please enter a valid number. ");
            }
            switch ((int) option) {
                case 1:
                    customerUtility.showProfile(id, sessionFactory);
                    break;
                case 2:
                    commonUtility.showAllAvailableBooks(sessionFactory);
                    break;
                case 3:
                    scan.nextLine();
                    System.out.print("Enter book Name: ");
                    String bookName = scan.nextLine().trim();
                    commonUtility.searchBookByName(bookName, sessionFactory);
                    break;

                case 4:
                    scan.nextLine();
                    System.out.print("Enter book Author: ");
                    String author = scan.nextLine().trim();
                    commonUtility.searchByAuthor(author, sessionFactory);
                    break;

                case 5:
                    scan.nextLine();
                    System.out.print("Enter book Subect: ");
                    String subject = scan.nextLine().trim();
                    commonUtility.searchBySubject(subject, sessionFactory);
                    break;
                default:
                    break;
            }
        } while (option != 6);

        if (option == 6) System.out.println("Logout Successfully. ");

    }
}
