package com.info.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.info.bean.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class LibraryUtility {

    private static int totalbooks = 0;
    private static int totalCustomer = 0;

    private static ArrayList<Customer> customerList = new ArrayList<Customer>();
    private static ArrayList<BookType> bookTypeList = new ArrayList<BookType>();
    private static Map<String, ArrayList<BookType>> searchBookByBookName = new HashMap<String, ArrayList<BookType>>();


    // mapping for which customer took which books
    private static Map<Customer, ArrayList<Book>> map = new HashMap<Customer, ArrayList<Book>>();
    //  private static Map<String, Integer> bookmap = new HashMap<>();


    public int getTotalBooks() {
        return totalbooks;
    }

    public int getTotalCustomers() {
        return totalCustomer;
    }


    public ArrayList<BookType> getBookList() {
        return bookTypeList;
    }

    public Map<String, ArrayList<BookType>> getSearchBookByBookNameList() {
        return searchBookByBookName;
    }

    public void setSearchBookByBookNameList(Map<String, ArrayList<BookType>> searchBookByBookName) {
        this.searchBookByBookName = searchBookByBookName;
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public Map<Customer, ArrayList<Book>> getAllBookCustomerMap() {
        return this.map;
    }
    /*
    public void changeId() {
        String id = libray.getId();
        String password = libray.getPassword();

        System.out.print("Enter current ID: ");
        String previousID = scan.next();
        if (!previousID.equals(id)) {
            System.out.println("Please Enter Correct Id.");
        } else {
            System.out.print("\nEnter Password: ");

            if (password.equals(scan.next()) && previousID.equals(id)) {
                System.out.println("Enter New ID");
                libray.setId(scan.next());
                System.out.println("Id Updated Successfully......");
            } else {
                System.out.println("Please Enter Correct Password.");
            }
        }
    }


    public void changePassword() {
        String id = libray.getId();
        String password = libray.getPassword();

        System.out.print("Enter current ID: ");
        String previousID = scan.next();
        String pwd = "";
        System.out.print("\nEnter Password: ");

        if (password.equals(scan.next()) && previousID.equals(id)) {
            System.out.println("Enter New Password");
            pwd = scan.next();
        }
        if (pwd.equals(password) && !pwd.isEmpty()) {
            libray.setPassword(pwd);
            System.out.println("Password Updated Successfully. ");
        }
    }

     */

    public void addCustomer(Customer customer, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        boolean isCustomerAdd = true;
        for (Customer c : getCustomerList()) {
            if (c.getId().equals(customer.getId())) {
                System.out.println("Customer already exist. ");
                isCustomerAdd = false;
                return;
            }
        }
        if (isCustomerAdd) {
            try {
                tx = session.beginTransaction();
                session.save(customer);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }

            customerList.add(customer);
        }
        totalCustomer++;
        if (customerList.size() == totalCustomer) System.out.println("Customer Added Successfully. \n");
        System.out.println("Id: " + customer.getId() + " password: " + customer.getPassword());
    }

    public void addBookType(BookType bookType, SessionFactory sessionFactory, Vendor vendor) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean isBookAdd = true;
        for (BookType b : bookTypeList) {
            if (b.getBookId().equals(bookType.getBookId()) && b.getBookName().equals(bookType.getBookName()) && b.getAuthor().equals(bookType.getAuthor())) {
                b.setBookQuantity(b.getBookQuantity() + bookType.getBookQuantity());
                try {
                    tx = session.beginTransaction();
                    session.update(b);
                    tx.commit();
                } catch (HibernateException e) {
                    if (tx != null) tx.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
                isBookAdd = false;
            }
        }
        if (isBookAdd) {
            bookType.setVendor(vendor);
            try {
                tx = session.beginTransaction();
                session.save(bookType);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }

            bookTypeList.add(bookType);
        }
        // adding all book objects one by one in bookArrayListLibrary
        addBooks(bookType, sessionFactory);


        System.out.println("BookType Added Successfully. ");

        // check if bookType name is already in searchbookbyname map or not.
        // if it is, then add new bookType for the same key
        // else add new <key, pair>
        // System.out.println(searchBookByBookName.containsKey(bookType.getBookName()));
        if (!searchBookByBookName.containsKey(bookType.getBookName())) {
            ArrayList<BookType> b = new ArrayList<BookType>();
            b.add(bookType);
            searchBookByBookName.put(bookType.getBookName(), b);
        }
        /*else {
            ArrayList<BookType> b = searchBookByBookName.get(bookType.getBookName());
            b.add(bookType);
            setSearchBookByBookNameList(searchBookByBookName);
           // searchBookByBookName.put(bookType.getBookName(), b);
        }
        */
    }

    public void issueBook(String cusId, int barCode, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        boolean bookFound = false;

        //getting objects from given id's
        Book bookObj = getBookObjectByBarCode(barCode);
        BookType bookType = getBookObjectById(bookObj.getBookId());
        Customer customerObj = getCustomerObjectById(cusId);

        if (isValidBarCode(barCode) && isValidCustomerId(cusId) && customerObj.getNoBooksCanBeIssued() == 0) {
            System.out.println("You have already issued maximum no of books. ");
            return;
        }

        //check validation
        // if true --> book marked as found else not found.
        if (isValidBarCode(barCode) && isValidCustomerId(cusId) && bookInLibraryList.contains(bookObj) && bookObj.getCanBeIssued()) {

            // bookEntityObj.setBookQuantity(bookEntityObj.getBookQuantity()-1);
            bookFound = true;
        }
        //  System.out.println(isValidBarCode(barCode) +" " + isValidCustomerId(cusId) +" " + bookObj.getBookQuantity() + " " + bookInLibraryList.contains(bookObj) +" " +  bookObj.getCanBeIssued());

        if (!bookFound) {
            System.out.println("Book not available. So cannot issue this book....");
            return;
        }

        if (customerObj.getNoBooksCanBeIssued() == 0) {
            System.out.println("You have already issued maximum books. ");
            return;
        }
        //  else customerObj.setNoBooksCanBeIssued(customerObj.getNoBooksCanBeIssued()-1);

        // update object bookType quantity

        bookType.setBookQuantity(bookTypeList.get(bookTypeList.indexOf(bookType)).getBookQuantity() - 1);


        // update book object in ------------> bookTypeList
        bookTypeList.set(bookTypeList.indexOf(bookType), bookType);

        //update customer's no of book can be issued in ---------------------> customers list
        customerList.get(customerList.indexOf(customerObj)).setNoBooksCanBeIssued(customerList.get(customerList.indexOf(customerObj)).getNoBooksCanBeIssued() - 1);

        // updating database
        int id = customerObj.getUser_id();
        Customer customer = session.get(Customer.class, id);
        BookType bookType1 = session.get(BookType.class, bookType.getBook_entity_id());
        Book book = session.get(Book.class, bookObj.getId());

        customer.setNoBooksCanBeIssued(customer.getNoBooksCanBeIssued() - 1);

        bookType1.setBookQuantity(bookType1.getBookQuantity() - 1);
        book.setCanBeIssued(!book.getCanBeIssued());
        book.setDateOfIssue(new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime()));
        book.setIssuedby(customer);

        //customer.getMapBookDate().put(book, new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime()));
        //customer.setMapBookDate(customer.getMapBookDate());

        customer.getBooksIssuedByCustomer().add(book);
        customer.setBooksIssuedByCustomer(customer.getBooksIssuedByCustomer());

        session.update(customer);
        session.update(bookType1);
        session.update(book);
        session.getTransaction().commit();
        session.close();

        // check if map contains customer --> check if customer has already issued any book or not
        if (map.containsKey(customerObj)) {
            map.get(customerObj).add(bookObj);
            map.put(customerObj, map.get(customerObj));
        } else {
            ArrayList<Book> bookEntityArrayList = new ArrayList<Book>();
            bookEntityArrayList.add(bookObj);
            map.put(customerObj, bookEntityArrayList);
        }
        customerObj.getBooksIssuedByCustomer().add(book);
        customerObj.setBooksIssuedByCustomer(customerObj.getBooksIssuedByCustomer());

        System.out.println("BookType issued Successfully ..... ");
        // setting book can be issued or not and book occupied by which customer.
        bookObj.setCanBeIssued(false);
        bookObj.setIssuedby(customerObj);
        bookObj.setDateOfIssue(new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime()));
        // System.out.println(customerObj.getMapBookDate());
    }

    public boolean returnBook(String customerId, int barCode, SessionFactory sessionFactory) {
        // opening a session
        Session session = sessionFactory.openSession();
        session.beginTransaction();


        Scanner scan = new Scanner(System.in);
        Customer customer = getCustomerObjectById(customerId);
        Book book = getBookObjectByBarCode(barCode);
        BookType bookType = getBookObjectById(book.getBookId());
        if (!map.containsKey(customer)) {
            System.out.println("You have not issued any bookType. ");
            map.remove(customer);
        }

        boolean isFine = false;

        if (isValidCustomerId(customerId) && isValidBarCode(barCode) && map.containsKey(customer) && isValidBookId(bookType.getBookId()) && map.get(customer).contains(book)) {
            if (map.get(customer).size() == 1) map.remove(customer);
            else map.get(customer).remove(book);

            double fineOnBook = calculateFineOnBook(customerId, barCode, sessionFactory);
            System.out.println("Fine on this bookType: " + fineOnBook);

            double totalFine = calculateTotalFine(customerId, sessionFactory);
            System.out.println("Total fine on this customer : " + totalFine);

            if (customer.getBooksIssuedByCustomer().contains(book))
                customer.getBooksIssuedByCustomer().remove(book);

            if (fineOnBook > 0) {
                System.out.println("Submit the fine money first : ");
                double money = scan.nextDouble();
                fineOnBook = fineOnBook - money;
                if (fineOnBook > 0) isFine = true;
            }

            if (!isFine) {

                int id = customer.getUser_id();
                Customer customer1 = session.get(Customer.class, id);
                BookType bookType1 = session.get(BookType.class, bookType.getBook_entity_id());
                Book book1 = session.get(Book.class, book.getId());

                customer1.setNoBooksCanBeIssued(customer1.getNoBooksCanBeIssued() + 1);

                bookType1.setBookQuantity(bookType1.getBookQuantity() + 1);
                book1.setCanBeIssued(!book1.getCanBeIssued());
                book1.setIssuedby(null);
                book1.setDateOfIssue(null);

                customer1.getBooksIssuedByCustomer().remove(book);
                customer1.setBooksIssuedByCustomer(customer1.getBooksIssuedByCustomer());

                session.update(customer1);
                session.update(bookType1);
                session.update(book1);
                session.getTransaction().commit();
                session.close();



                /*
                // update bookType quantity in bookTypeList
                bookTypeList.get(bookTypeList.indexOf(book)).setBookQuantity(bookTypeList.get(bookTypeList.indexOf(book)).getBookQuantity() + 1);

                // update customer's no of bookType can be issued
                customerList.get(customerList.indexOf(customer)).setNoBooksCanBeIssued(customerList.get(customerList.indexOf(customer)).getNoBooksCanBeIssued() + 1);
                // System.out.println("Number of books customer can issue: " + customer.getNoBooksCanBeIssued());

                // setting book can be issued by some other
                book.setCanBeIssued(true);
                 */
                return true;
            }
            return false;
        } else return false;
    }

    public int NumberOfBookOccupiedByCustomer(String customerId) {
        if (isValidCustomerId(customerId)) {
            return map.get(customerId).size();
        }
        return -1;
    }


    public boolean isValidCustomerId(String id) {
        for (Customer cus : getCustomerList()) {
            if (cus.getId().equals(id)) return true;
        }
        return false;
    }

    public boolean isValidBookId(String bookId) {
        for (BookType bookType : bookTypeList) {
            if (bookType.getBookId().equals(bookId)) return true;
        }
        return false;
    }

    public BookType getBookObjectById(String bookId) {
        for (BookType bookType : bookTypeList) {
            if (bookType.getBookId().equals(bookId)) return bookType;
        }
        return null;
    }

    public Customer getCustomerObjectById(String customerId) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(customerId)) return customer;
        }
        return null;
    }

    public void showAllCustomerInfo(SessionFactory sessionFactory) {

        Session session = sessionFactory.openSession();

        Transaction t = session.beginTransaction();

        Query query = session.createQuery("from Customer");
        List list = query.list();
        System.out.println(list);
        t.commit();
        session.close();


        // System.out.println(map);
        System.out.println("Customers List: ");
        System.out.format("%16s%32s%32s%48s%48s", "Name", "Mobile Number", "Address", "Number of Books can be issued", "List of books Issued");
        System.out.println();
        for (Customer c : getCustomerList()) {
            StringBuffer bookList = new StringBuffer("");
            if (map.containsKey(c)) {
                for (int i = 0; i < map.get(c).size() - 1; i++) {
                    bookList.append(map.get(c).get(i).getBookId());
                    bookList.append(", ");
                }
                bookList.append(map.get(c).get(map.get(c).size() - 1).getBookId());
            } else {
                bookList.append("No book issued. ");
            }
            System.out.format("%16s%32d%32s%48d%48s", c.getName(), c.getMobNumber(), c.getAddress(), c.getNoBooksCanBeIssued(), bookList);
            System.out.println();
        }
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
        return true;
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


    // total fine for per for customer
    public double calculateFineOnBook(String cusId, int barCode, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Book bookObj = getBookObjectByBarCode(barCode);
        String currentDate = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        /*Map<BookType, String> mapBookDate = customerObj.getMapBookDate();
        String issueDate = mapBookDate.get(bookObj).substring(0, 2) + " " + mapBookDate.get(bookObj).substring(2, 4) + " " + mapBookDate.get(bookObj).substring(4);
        */

        String issueDate = bookObj.getDateOfIssue().substring(0, 2) + " " + bookObj.getDateOfIssue().substring(2, 4) + " " + bookObj.getDateOfIssue().substring(4);

        long days = 0;
        double fine = 0;

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        /*
        try {
            Date date1 = myFormat.parse(issueDate);
            Date date2 = myFormat.parse(currentDate);
            long diff = date2.getTime() - date1.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
           // System.out.println(days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (days > 60) {
            fine = getBookObjectByBarCode(barCode).getPrice();
        }
        if (days > 15 && days <= 60) {
            fine = 15 * ((int) days - 15);
            return fine;
        } else fine=0;
        */

        // hql query to fetch customer obj from database by id=barcode

        String hql2 = "from Book where barcode = :barcode";
        Query query2 = session.createQuery(hql2);
        query2.setParameter("barcode", barCode);
        Book book = (Book) query2.getSingleResult();


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
            fine = book.getPrice();
        }
        if (days > 15 && days <= 60) {
            fine = 15 * ((int) days - 15);
            return fine;
        } else fine = 0;


        return fine;
    }

    // total fine of customer
    public double calculateTotalFine(String cusId, SessionFactory sessionFactory) {
        Customer customerObj = getCustomerObjectById(cusId);
        String currentDate = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String issueDate = "";
        double fine = 0;
        double finePerBook = 0;
        long days = 0;
        for (Book book : customerObj.getBooksIssuedByCustomer()) {
            finePerBook = 0;
            //issueDate = entry.getValue().substring(0, 2) + " " + entry.getValue().substring(2, 4) + " " + entry.getValue().substring(4);
            issueDate = book.getDateOfIssue().substring(0, 2) + " " + book.getDateOfIssue().substring(2, 4) + " " + book.getDateOfIssue().substring(4);
            try {
                Date date1 = myFormat.parse(issueDate);
                Date date2 = myFormat.parse(currentDate);
                long diff = date2.getTime() - date1.getTime();
                days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            } catch (ParseException e) {
                System.out.println("Some error occurred....please try again. ");
            }
            if (days > 60) {
                finePerBook = book.getPrice();
            } else if (days > 15 && days <= 60) finePerBook = (days - 15) * 15;
            else finePerBook = 0;

            fine += finePerBook;
        }
        return fine;
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
                        System.out.println(bookType.getSubject());
                        System.out.println(newBookType.getSubject());
                        // add books in library
                        newBookType.setBookQuantity(quantity);
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


    private List<Book> bookInLibraryList; // all separate book objects

    public List<Book> getBookInLibraryList() {
        return bookInLibraryList;
    }

    public void addBooks(BookType bookType, SessionFactory sessionFactory) {
        int bookIndex = 0;
        bookInLibraryList = new ArrayList<Book>(); // all seperate book objects
        Session session = sessionFactory.openSession();
        Transaction tx = null;


        int quantity = bookType.getBookQuantity();
        for (int i = 1; i <= quantity; i++) {
            Book book = new Book();
            int barCode = generateBarCode(bookType.getBookName(), bookType.getAuthor(), bookType.getPrice(), bookIndex);
            book.setBarcode(barCode);
            book.setCanBeIssued(true);
            book.setAuthor(bookType.getAuthor());
            book.setBookName(bookType.getBookName());
            book.setBookId(bookType.getBookId());
            book.setPrice(bookType.getPrice());

            try {
                tx = session.beginTransaction();
                session.save(book);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            }

            bookInLibraryList.add(book);
            bookIndex++;
            System.out.println("Bar Codes: " + barCode);
        }
        session.close();
        //bookType.setBookList(getBookInLibraryList());
    }

    public int generateBarCode(String bookName, String authorName, double price, int bookIndex) {
        Random random = new Random();
        int randNum = random.nextInt(1000);
        return (randNum + (int) price + bookIndex * 10) * 500;
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

    public boolean isValidBarCode(int barCode) {
        for (Book book : getBookInLibraryList()) {
            if (book.getBarcode() == (barCode)) {
                return true;
            }
        }
        return false;
    }

    public Book getBookObjectByBarCode(int barCode) {
        for (Book book : getBookInLibraryList()) {
            if (book.getBarcode() == (barCode)) {
                return book;
            }
        }
        return null;
    }

}
