package com.info.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class LibraryUtility {
    Librarian libray = new Librarian();


    Scanner scan = new Scanner(System.in);

    private static int totalbooks = 0;
    private static int totalCustomer = 0;

    private static ArrayList<Customer> customerList = new ArrayList<>();
    private static ArrayList<BookEntity> bookEntityList = new ArrayList<>();
    private static Map<String, ArrayList<BookEntity>> searchBookByBookName = new HashMap<>();


    // mapping for which customer took which books
    private static Map<Customer, ArrayList<Book>> map = new HashMap<>();
    //  private static Map<String, Integer> bookmap = new HashMap<>();


    public int getTotalBooks() {
        return totalbooks;
    }

    public int getTotalCustomers() {
        return totalCustomer;
    }


    public ArrayList<BookEntity> getBookList() {
        return bookEntityList;
    }

    public Map<String, ArrayList<BookEntity>> getSearchBookByBookNameList() {
        return searchBookByBookName;
    }

    public void setSearchBookByBookNameList(Map<String, ArrayList<BookEntity>> searchBookByBookName){
        this.searchBookByBookName=searchBookByBookName;
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

    public void addCustomer(Customer customer) {
        boolean isCustomerAdd = true;
        for (Customer c : getCustomerList()) {
            if (c.getId().equals(customer.getId())) {
                System.out.println("Customer already exist. ");
                isCustomerAdd = false;
                return;
            }
        }
        if (isCustomerAdd) customerList.add(customer);
        totalCustomer++;
        if (customerList.size() == totalCustomer) System.out.println("Customer Added Successfully. \n");
        System.out.println("Id: " + customer.getId() + " password: " + customer.getPassword());
    }

    public void addEntityBook(BookEntity bookEntity) {
        boolean isBookAdd = true;
        for (BookEntity b : bookEntityList) {
            if (b.getBookId().equals(bookEntity.getBookId()) && b.getBookName().equals(bookEntity.getBookName()) && b.getAuthor().equals(bookEntity.getAuthor())) {
                b.setBookQuantity(b.getBookQuantity() + bookEntity.getBookQuantity());
                isBookAdd = false;
            }
        }
        if (isBookAdd) bookEntityList.add(bookEntity);
        // adding all book objects one by one in bookArrayListLibrary
        addBooks(bookEntity);

        System.out.println("BookType Added Successfully. ");

        // check if bookEntity name is already in searchbookbyname map or not.
        // if it is, then add new bookEntity for the same key
        // else add new <key, pair>
        // System.out.println(searchBookByBookName.containsKey(bookEntity.getBookName()));
        if (!searchBookByBookName.containsKey(bookEntity.getBookName())) {
            ArrayList<BookEntity> b = new ArrayList<>();
            b.add(bookEntity);
            searchBookByBookName.put(bookEntity.getBookName(), b);
        }
        /*else {
            ArrayList<BookType> b = searchBookByBookName.get(bookEntity.getBookName());
            b.add(bookEntity);
            setSearchBookByBookNameList(searchBookByBookName);
           // searchBookByBookName.put(bookEntity.getBookName(), b);
        }
        */
    }

    public void issueBook(String cusId, String barCode) {
        boolean bookFound = false;

        //getting objects from given id's
        Book bookObj = getBookObjectByBarCode(barCode);
        BookEntity bookEntity = getBookObjectById(bookObj.getBookId());
        Customer customerObj = getCustomerObjectById(cusId);

        if (isValidBarCode(barCode) && isValidCustomerId(cusId) && customerObj.getNoBooksCanBeIssued() == 0) {
            System.out.println("You have already issued maximum no of books. ");
            return;
        }

        //check validation
        // if true --> book marked as found else not found.
        if (isValidBarCode(barCode) && isValidCustomerId(cusId) && bookObj.getBookQuantity() > 0 && bookInLibraryList.contains(bookObj) && bookObj.getCanBeIssued()) {

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

        // update object book quantity

        bookObj.setBookQuantity(bookEntityList.get(bookEntityList.indexOf(bookEntity)).getBookQuantity() - 1);

        // update book object in ------------> bookEntityList
        bookEntityList.set(bookEntityList.indexOf(bookEntity), bookObj);

        //update customer's no of book can be issued in ---------------------> customers list
        customerList.get(customerList.indexOf(customerObj)).setNoBooksCanBeIssued(customerList.get(customerList.indexOf(customerObj)).getNoBooksCanBeIssued() - 1);

        // check if map contains customer --> check if customer has already issued any book or not
        if (map.containsKey(customerObj)) {
            map.get(customerObj).add(bookObj);
            map.put(customerObj, map.get(customerObj));
        } else {
            ArrayList<Book> bookEntityArrayList = new ArrayList<>();
            bookEntityArrayList.add(bookObj);
            map.put(customerObj, bookEntityArrayList);
        }
        customerObj.getMapBookDate().put(bookObj, new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime()));
        customerObj.setMapBookDate(customerObj.getMapBookDate());

        System.out.println("BookType issued Successfully ..... ");
        // setting book can be issued or not and book occupied by which customer.
        bookObj.setCanBeIssued(false);
        bookObj.setOccupiedBy(customerObj);
        bookObj.setDateOfIssue(new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime()));
         // System.out.println(customerObj.getMapBookDate());
    }

    public boolean returnBook(String customerId, String barCode) {
        Scanner scan = new Scanner(System.in);
        Customer customer = getCustomerObjectById(customerId);
        Book book = getBookObjectByBarCode(barCode);
        BookEntity bookEntity = getBookObjectById(book.getBookId());
        if (!map.containsKey(customer)) {
            System.out.println("You have not issued any bookEntity. ");
            map.remove(customer);
        }
      //  System.out.println(map);

        boolean isFine = false;

        if (isValidCustomerId(customerId) && isValidBarCode(barCode) && map.containsKey(customer)&& isValidBookId(bookEntity.getBookId()) && map.get(customer).contains(book)){
            if (map.get(customer).size() == 1) map.remove(customer);
            else map.get(customer).remove(book);

           /* for(BookType b : map.get(customer)){
                System.out.println(b.getBookId());
            }

            */

            double fineOnBook = calculateFineOnBook(customerId, barCode);
            System.out.println("Fine on this bookEntity: " + fineOnBook);

            double totalFine = calculateTotalFine(customerId);
            System.out.println("Total fine on this customer : " + totalFine);

            if (customer.getMapBookDate().containsKey(book))
                customer.getMapBookDate().remove(book);

            if (fineOnBook > 0) {
                System.out.println("Submit the fine money first : ");
                double money = scan.nextDouble();
                fineOnBook = fineOnBook - money;
                if (fineOnBook > 0) isFine = true;
            }

            if (!isFine) {
                // update bookEntity quantity in bookEntityList
                bookEntityList.get(bookEntityList.indexOf(book)).setBookQuantity(bookEntityList.get(bookEntityList.indexOf(book)).getBookQuantity() + 1);

                // update customer's no of bookEntity can be issued
                customerList.get(customerList.indexOf(customer)).setNoBooksCanBeIssued(customerList.get(customerList.indexOf(customer)).getNoBooksCanBeIssued() + 1);
                // System.out.println("Number of books customer can issue: " + customer.getNoBooksCanBeIssued());

                // setting book can be issued by some other
                book.setCanBeIssued(true);
                return true;
            }
            return false;
        }
        else return false;
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
        for (BookEntity bookEntity : bookEntityList) {
            if (bookEntity.getBookId().equals(bookId)) return true;
        }
        return false;
    }

    public BookEntity getBookObjectById(String bookId) {
        for (BookEntity bookEntity : bookEntityList) {
            if (bookEntity.getBookId().equals(bookId)) return bookEntity;
        }
        return null;
    }

    public Customer getCustomerObjectById(String customerId) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(customerId)) return customer;
        }
        return null;
    }

    public void showAllCustomerInfo() {
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
        return mobNo % 100000 + dob.replaceAll("\\s", "") + String.valueOf(mobNo).substring(0, 5);
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
    public double calculateFineOnBook(String cusId, String barCode) {
        Customer customerObj = getCustomerObjectById(cusId);
        Book bookObj = getBookObjectByBarCode(barCode);
        String currentDate = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        /*Map<BookType, String> mapBookDate = customerObj.getMapBookDate();
        String issueDate = mapBookDate.get(bookObj).substring(0, 2) + " " + mapBookDate.get(bookObj).substring(2, 4) + " " + mapBookDate.get(bookObj).substring(4);
        */

        String issueDate = bookObj.getDateOfIssue().substring(0, 2) + " " +  bookObj.getDateOfIssue().substring(2, 4) + " " + bookObj.getDateOfIssue().substring(4);

        long days = 0;
        double fine = 0;

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");

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
        return fine;
    }

    // total fine of customer
    public double calculateTotalFine(String cusId) {
        Customer customerObj = getCustomerObjectById(cusId);
        String currentDate = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String issueDate = "";
        double fine = 0;
        double finePerBook = 0;
        long days = 0;
        for (Map.Entry<Book, String> entry : customerObj.getMapBookDate().entrySet()) {
            finePerBook = 0;
            issueDate = entry.getValue().substring(0, 2) + " " + entry.getValue().substring(2, 4) + " " + entry.getValue().substring(4);
            try {
                Date date1 = myFormat.parse(issueDate);
                Date date2 = myFormat.parse(currentDate);
                long diff = date2.getTime() - date1.getTime();
                days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            } catch (ParseException e) {
                System.out.println("Some error occurred....please try again. ");
            }
            if (days >60) {
                finePerBook = entry.getKey().getPrice();
            } else if (days > 15 && days <= 60) finePerBook = (days - 15) * 15;
            else finePerBook = 0;

            fine += finePerBook;
        }
        return fine;
    }

    VendorList vendorList = new VendorList();

    public void addVendorData(){
        vendorList.bookListData();
        vendorList.vendorListData();
    }
    public boolean orderBook(String vendorId, String bookName, String bookAuthor, int quantity) {
        for(Vendor vendor : vendorList.getVendorArrayList()){
            if(vendor.getId().equals(vendorId)){
                for(BookEntity bookEntity : vendor.getVendorBookEntityList()){
                    if(bookEntity.getBookName().equals(bookName) && bookEntity.getAuthor().equals(bookAuthor) && quantity > bookEntity.getBookQuantity()){
                        System.out.println("This quantity of bookEntity is not available. ");
                        return false;
                    }
                    if(bookEntity.getBookName().equals(bookName) && bookEntity.getAuthor().equals(bookAuthor) && bookEntity.getBookQuantity()>=quantity){
                        BookEntity newBookEntity = new BookEntity(bookEntity);
                        // update data in vendor list
                        bookEntity.setBookQuantity(bookEntity.getBookQuantity()-quantity);

                        // add books in library
                        newBookEntity.setBookQuantity(quantity);
                        // System.out.println(bookEntity.getBookQuantity());
                        addEntityBook(newBookEntity);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void showVendorList(){
        System.out.println("Vendor Details: ");
        System.out.format("%16s%16s", "Name", "Id");
        System.out.println();
        for(Vendor vendor : vendorList.getVendorArrayList()){
            System.out.format("%16s%16s", vendor.getName(), vendor.getId());
            System.out.println();
        }
    }

    public void checkStockInVendor(String vendorId){
        boolean isValidVendor = false;
        Vendor vendorObj = null;
        for(Vendor vendor : vendorList.getVendorArrayList()){
            if(vendor.getId().equals(vendorId)){
                isValidVendor = true;
                vendorObj=vendor;
                break;
            }
        }

        if(isValidVendor){
            System.out.format("%16s%16s%16s%32s", "Name", "Author", "BookType Id", "Number of Books");
            System.out.println();
            for(BookEntity bookEntity : vendorObj.getVendorBookEntityList()){
                System.out.format("%16s%16s%16s%32s", bookEntity.getBookName(), bookEntity.getAuthor(), bookEntity.getBookId(), bookEntity.getBookQuantity());
                System.out.println();
            }
        }
        else{
            System.out.println("Please enter correct vendor Id. ");
        }
    }

    static int bookIndex = 0;
    private static List<Book> bookInLibraryList = new ArrayList<>(); // all seperate book objects

    public List<Book> getBookInLibraryList(){
        return bookInLibraryList;
    }

    public void addBooks(BookEntity bookEntity){
        int quantity = bookEntity.getBookQuantity();
        for(int i=1;i<=quantity;i++){
            Book book = new Book();
            String barCode = generateBarCode(bookEntity.getBookName(),bookEntity.getAuthor(), bookEntity.getPrice(), bookIndex);
            book.setBarcode(barCode);
            book.setCanBeIssued(true);
            book.setBookQuantity(quantity);
            book.setAuthor(bookEntity.getAuthor());
            book.setBookName(bookEntity.getBookName());
            book.setBookId(bookEntity.getBookId());
            book.setPrice(bookEntity.getPrice());
            bookInLibraryList.add(book);
            bookIndex++;
            System.out.println("Bar Codes: " + barCode);
        }
    }
    public String generateBarCode(String bookName, String authorName, double price, int bookIndex){
        return (bookName.substring(0,2) + authorName.substring(0, authorName.length()/2) + (int)price + bookIndex*10).replaceAll("\\s","");
    }

    public String generatePassword(String name, String dob){
        return name + dob.replaceAll("\\s","");
    }

    public boolean isValidVendorId(String id){
        if(id.trim().equals("")) return false;
        for(Vendor vendor : vendorList.getVendorArrayList()){
            if(vendor.getId().equals(id)) return true;
        }
        return false;
    }

    public boolean isValidBarCode(String barCode){
        if(barCode.trim().equals("")) return false;
        for(Book book : getBookInLibraryList()){
            if(book.getBarcode().equals(barCode)){
                return true;
            }
        }
        return false;
    }

    public Book getBookObjectByBarCode(String barCode){
        for(Book book : getBookInLibraryList()){
            if(book.getBarcode().equals(barCode)){
                return book;
            }
        }
        return null;
    }

}
