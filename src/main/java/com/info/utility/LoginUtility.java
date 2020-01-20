package com.info.utility;

import com.info.pageSource.CustomerPageSource;
import org.hibernate.SessionFactory;

import javax.ejb.Stateless;

@Stateless
public class LoginUtility{
    private static final SessionFactory sessionFactory;

    static {
        SessionClass sessionClass = new SessionClass();

        sessionClass.createSessionFactoryObject();

        sessionFactory = sessionClass.getSessionFactoryObject();
    }

    public SessionFactory getSessionfactory(){
        return sessionFactory;
    }


    public String login(String username, String password, String userType){
        LibraryUtility libraryUtility = new LibraryUtility();
        CustomerPageSource customerPageSource = new CustomerPageSource();
        customerPageSource.setUserName(username);
        customerPageSource.setPassword(password);



        SessionFactory sessionFactory = getSessionfactory();

        System.out.println("Nitin2 " + username + " " + password);

        if(userType.equals("Customer") && libraryUtility.isValidCustomerPassword(username, password, sessionFactory)){
            System.out.println("login method called");
            return "customerview";
        }
        else if(username.equals("admin") && password.equals("admin") && userType.equals("Librarian")){
             return "librarianview";
        }
        return "#";
    }
}
