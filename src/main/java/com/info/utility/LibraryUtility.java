package com.info.utility;

import com.info.bean.Book;
import com.info.bean.BookType;
import com.info.bean.Customer;
import com.info.bean.Vendor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryUtility {

    private static int totalbooks = 0;

    public void addCustomer(Customer customer, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        boolean isCustomerAdd = true;

        Query query = session.createQuery("from Customer where id = :id");
        query.setParameter("id", customer.getId());

        Customer customer1=null;
        try {
            customer1 = (Customer) query.getSingleResult();
        }catch (Exception e){
            System.out.println("Cannot add this customer. Please try again. ");
        }

        if(customer1!=null){
            isCustomerAdd=false;
            System.out.println("Customer already exist");
            System.out.println("Id: " + customer1.getId() + " " + "password: " + customer1.getPassword());
            return;
        }

        if (isCustomerAdd) {
            try {
                tx = session.beginTransaction();
                session.save(customer);
                tx.commit();
                System.out.println("Customer Added Successfully. \n");
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        System.out.println("Id: " + customer.getId() + " password: " + customer.getPassword());
    }

    public void addBookType(BookType bookType, SessionFactory sessionFactory, Vendor vendor) {
        bookType.setVendor(vendor);
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean isBookAdd = true;

        Query query = session.createQuery("from BookType where bookId = :bookId");
        query.setParameter("bookId", bookType.getBookId());

        BookType bookType1=null;

        try {
            bookType1 = (BookType) query.getSingleResult();
        }catch (Exception e){
            System.out.println("Some error occurred. please try again.");
        }

        if(bookType1==null){
            try {
                tx = session.beginTransaction();
                session.save(bookType);
                tx.commit();
                System.out.println("BookType Added Successfully. ");
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            addBooks(bookType, sessionFactory);
        }
        else{
            System.out.println("BookType already exist.");
            tx = session.beginTransaction();
            bookType1.setBookQuantity(bookType1.getBookQuantity() + bookType.getBookQuantity());
            session.update(bookType1);
            session.save(bookType1);
            tx.commit();
            session.close();
            System.out.println("BookType Quantity Updated Successfully. ");
            bookType1.setBookQuantity(bookType.getBookQuantity());
            addBooks(bookType1, sessionFactory);
        }

    }

    public void addBooks(BookType bookType, SessionFactory sessionFactory) {
        int bookIndex = 0;
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        int quantity = bookType.getBookQuantity();
        for (int i = 1; i <= quantity; i++) {
            Book book = new Book();
            int barCode = generateBarCode(bookType.getBookName(), bookType.getAuthor(), bookType.getPrice(), bookIndex);
            book.setBarcode(barCode);
            book.setCanBeIssued(true);
            book.setBookType(bookType);

            try {
                session.saveOrUpdate(book);

            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            }
            bookIndex++;
            System.out.println("Bar Codes: " + barCode);
        }
        session.close();
    }

    public void issueBook(String cusId, int barCode, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        // getting book from bok table
        Query query = session.createQuery("from Book where barcode = :barcode");
        query.setParameter("barcode", barCode);
        Book book=null;
        try {
            book = (Book) query.getSingleResult();
        }catch (Exception e){
            System.out.println("Some error occurred. Cannot issue book. Please try again....");
        }

        // getting customer from Customer table
        Query query1 = session.createQuery("from User where id = :id");
        query1.setParameter("id", cusId);
        Customer customer=null;
        try{
            customer = (Customer)query1.getSingleResult();
        }catch (Exception e){

        }

        if(book!=null && book.getCanBeIssued() && customer!=null && customer.getNoBooksCanBeIssued()>0){    // can issue this book
            // updating book
            book.setIssuedby(customer);
            book.setDateOfIssue(new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime()));
            book.setCanBeIssued(false);

            // updating customer
            customer.setNoBooksCanBeIssued(customer.getNoBooksCanBeIssued()-1);
            customer.getBooksIssuedByCustomer().add(book);
            customer.setBooksIssuedByCustomer(customer.getBooksIssuedByCustomer());

            // updating bookType
            BookType bookType = book.getBookType();
            bookType.setBookQuantity(bookType.getBookQuantity()-1);

            // updating session
            session.update(book);
            session.update(bookType);
            session.update(customer);
            tx.commit();
            session.close();

            System.out.println("Book issued successfully. ");
        }
        else if(customer==null){
            System.out.println("This customer doesn't exist");
            return;
        }
        else if(book==null || book.getCanBeIssued()==false){                                                                                              // cannot issue this book
            System.out.println("This book is not present in library.");
            return;
        }
        else if(customer.getNoBooksCanBeIssued()==0){
            System.out.println("You have already issued maximum no of books. ");
        }

    }

    public void returnBook(String customerId, int barCode, SessionFactory sessionFactory) {
        Scanner scan = new Scanner(System.in);
        // opening a session
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Book where barcode = :barcode");
        query.setParameter("barcode", barCode);
        Book book=null;
        try {
            book = (Book) query.getSingleResult();
        }catch (Exception e){
            System.out.println("Cannot return book. Please try again");
        }
        BookType bookType = book.getBookType();

        Query query1 = session.createQuery("from Customer where id = :id");
        query1.setParameter("id", customerId);
        Customer customer = (Customer) query1.getSingleResult();

        if(book==null){
            System.out.println("This book doesn't belong to this library. ");
            return;
        }
        else if(customer==null){
            System.out.println("Customer doesn't exist. ");
        }
        else if(!book.getIssuedby().equals(customer)) {
            System.out.println("You have not issued this book");
            return;
        }
        double fine = calculateFineOnBook(customerId, barCode, sessionFactory);
        System.out.println("Fine on this book: " + fine);
        if(fine>0) {
            System.out.println("Enter money to submit this book: ");
            double money = scan.nextDouble();
            if (money >= fine) {
                bookType.setBookQuantity(bookType.getBookQuantity()+1);
                book.setDateOfIssue(null);
                book.setCanBeIssued(true);
                book.setIssuedby(null);
                customer.setNoBooksCanBeIssued(customer.getNoBooksCanBeIssued()+1);
                customer.getBooksIssuedByCustomer().remove(book);
                customer.setBooksIssuedByCustomer(customer.getBooksIssuedByCustomer());
                try {
                    session.update(book);
                    session.update(bookType);
                    session.update(customer);
                    System.out.println("Book returned successfully.");
                    tx.commit();
                }catch (Exception e){
                    System.out.println("Some error occurred, please try again........");
                    tx.rollback();
                }finally {
                    session.close();
                }
            }
        }
        else {
            bookType.setBookQuantity(bookType.getBookQuantity() + 1);
            book.setCanBeIssued(true);
            book.setIssuedby(null);
            book.setDateOfIssue(null);
            customer.setNoBooksCanBeIssued(customer.getNoBooksCanBeIssued() + 1);
            customer.getBooksIssuedByCustomer().remove(book);
            customer.setBooksIssuedByCustomer(customer.getBooksIssuedByCustomer());
            try {
                session.update(book);
                session.update(bookType);
                session.update(customer);
                System.out.println("Book returned successfully.");
                tx.commit();
            } catch (Exception e) {
                System.out.println("Some error occurred, please try again........");
                tx.rollback();
            } finally {
                session.close();
            }
        }
    }
    // total fine for per for customer
    public double calculateFineOnBook(String cusId, int barCode, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String currentDate = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());

        long days = 0;
        double fine = 0;

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");

        // hql query to fetch customer obj from database by id=barcode
        String hql2 = "from Book where barcode = :barcode";
        Query query2 = session.createQuery(hql2);
        query2.setParameter("barcode", barCode);
        Book book = (Book) query2.getSingleResult();
        BookType bookType = book.getBookType();

        try {
            Date date1 = myFormat.parse(book.getDateOfIssue());
            Date date2 = myFormat.parse(currentDate);
            long diff = date2.getTime() - date1.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            // System.out.println(days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (days > 60) {
            fine = bookType.getPrice();
        }
        if (days > 15 && days <= 60) {
            fine = 15 * ((int) days - 15);
            return fine;
        } else fine = 0;


        return fine;
    }


    public void showAllCustomerInfo(SessionFactory sessionFactory) {

        Session session = sessionFactory.openSession();

        Transaction t = session.beginTransaction();

        Query query = session.createQuery("from Customer");
        List<Customer> list = query.list();

        System.out.println("Customers List: ");
        System.out.format("%16s%32s%32s%48s", "Name", "Mobile Number", "Address", "Number of Books can be issued");
        System.out.println();

        for(Customer c : list){
            System.out.format("%16s%32d%32s%48d", c.getName(), c.getMobNumber(), c.getAddress(), c.getNoBooksCanBeIssued());
            System.out.println();
        }
        t.commit();
        session.close();
    }

    public String generateId(String name, long mobNo, String dob, String address) {
        return name.substring(0,2) + address.substring(0,2) + dob.replaceAll("\\s", "") + String.valueOf(mobNo).substring(0, 2);
    }

    public boolean isValidAddress(String address) {
        if (address.trim().equals("")) return false;
        return true;
    }

    public boolean isValidMobNo(long mobNo) {
        if (String.valueOf(mobNo).length() == 10) return true;
        return false;
    }

    public boolean isValidName(String name) {
        if (name.trim().equals("")) return false;

        CharSequence inputStr = name;
        Pattern pattern = Pattern.compile(new String ("^[a-zA-Z\\s]*$"));
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches())
        {
            return true;
        }
        return false;
    }

    public boolean isValidDob(String dob) {
        if (dob.length() == 10 && dob.split(" ").length == 3) {
            String[] dd = dob.split(" ");
            return isDateValid(Integer.parseInt(dd[2]), Integer.parseInt(dd[1]), Integer.parseInt(dd[0]));
        }
        return false;
    }

    public boolean isDateValid(int year, int month, int day) {
        boolean dateIsValid = true;
        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            dateIsValid = false;
        }
        return dateIsValid;
    }

    VendorData vendorData = new VendorData();

    public void addVendorData(SessionFactory sessionFactory) {
        vendorData.generateBookList(sessionFactory);
        vendorData.generateVendorData(sessionFactory);
    }

    public boolean orderBook(String vendorId, String bookName, String bookAuthor, int quantity, SessionFactory sessionFactory) {
        for (Vendor vendor : vendorData.getVendorArrayList()) {
            if (vendor.getId().equals(vendorId)) {
                for (BookType bookType : vendor.getVendorBookTypeList()) {
                    if (bookType.getBookName().equals(bookName) && bookType.getAuthor().equals(bookAuthor) && quantity > bookType.getBookQuantity()) {
                        System.out.println("This quantity of bookType is not available. ");
                        return false;
                    }
                    if (bookType.getBookName().equals(bookName) && bookType.getAuthor().equals(bookAuthor) && bookType.getBookQuantity() >= quantity) {
                        BookType newBookType = new BookType(bookType);
                        // update data in vendor list
                        bookType.setBookQuantity(bookType.getBookQuantity() - quantity);

                        // add books in library
                        newBookType.setBookQuantity(quantity);
                        newBookType.setSubject(bookType.getSubject());
                        // System.out.println(bookType.getBookQuantity());
                        addBookType(newBookType, sessionFactory, vendor);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void showVendorList(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Vendor");
        List list = query.list();
        System.out.println(list);
        tx.commit();
        session.close();

        System.out.println("Vendor Details: ");
        System.out.format("%16s%16s", "Name", "Id");
        System.out.println();
        for (Vendor vendor : vendorData.getVendorArrayList()) {
            System.out.format("%16s%16s", vendor.getName(), vendor.getId());
            System.out.println();
        }
    }

    public void checkStockInVendor(String vendorId) {
        boolean isValidVendor = false;
        Vendor vendorObj = null;
        for (Vendor vendor : vendorData.getVendorArrayList()) {
            if (vendor.getId().equals(vendorId)) {
                isValidVendor = true;
                vendorObj = vendor;
                break;
            }
        }

        if (isValidVendor) {
            System.out.format("%16s%16s%16s%32s", "Name", "Author", "BookType Id", "Number of Books");
            System.out.println();
            for (BookType bookType : vendorObj.getVendorBookTypeList()) {
                System.out.format("%16s%16s%16s%32s", bookType.getBookName(), bookType.getAuthor(), bookType.getBookId(), bookType.getBookQuantity());
                System.out.println();
            }
        } else {
            System.out.println("Please enter correct vendor Id. ");
        }
    }


    public int generateBarCode(String bookName, String authorName, double price, int bookIndex) {
        Random random = new Random();
        int randNum = random.nextInt(1000000);
        return (randNum + (int) price + bookIndex * 10)*10;
    }

    public String generatePassword(String name, String dob) {
        return name + dob.replaceAll("\\s", "");
    }

    public boolean isValidVendorId(String id) {
        if (id.trim().equals("")) return false;
        for (Vendor vendor : vendorData.getVendorArrayList()) {
            if (vendor.getId().equals(id)) return true;
        }
        return false;
    }

    public boolean isValidCustomerId(String customerId, SessionFactory sessionFactory){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Customer customer=null;
        try {
            Query query = session.createQuery("from Customer where id = :id");
            query.setParameter("id", customerId);
            customer = (Customer) query.getSingleResult();
            tx.commit();
        }catch (Exception e) {
            System.out.println("Some error occurred. ");
        }finally {
            session.close();
        }
        if(customer!=null) return true;
        return false;
    }

    public boolean isValidCustomerPassword(String customerId, String password, SessionFactory sessionFactory){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Customer customer=null;
        try {
            Query query = session.createQuery("from Customer where id = :id");
            query.setParameter("id", customerId);
            customer = (Customer) query.getSingleResult();
            tx.commit();
        }catch (Exception e) {
            System.out.println("Some error occurred. ");
        }finally {
            session.close();
        }
        if(customer.getPassword().equals(password)) return true;
        return false;
    }
}
