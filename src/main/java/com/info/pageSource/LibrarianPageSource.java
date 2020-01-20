package com.info.pageSource;

import com.info.bean.Book;
import com.info.bean.BookType;
import com.info.bean.Customer;
import com.info.bean.Vendor;
import com.info.utility.CustomerUtility;
import com.info.utility.LibraryUtility;
import com.info.utility.LoginUtility;
import com.info.utility.ParseVendorData;
import org.hibernate.SessionFactory;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean
@SessionScoped
public class LibrarianPageSource implements Serializable {
    private String name;
    private String dob;
    private long mobileNumber;
    private String address;
    private boolean addNewCustomer = false;
    private String message = "";

    @EJB
    LibraryUtility libraryUtility;
    @EJB
    LoginUtility loginUtility;
    @EJB
    CustomerUtility customerUtility;
    @EJB
    ParseVendorData parseVendorData;


    private boolean submitClicked = false;
    private boolean orderNewBooks = false;
    private boolean showBooksIssuedByCustomer = false;

    private String vendorId;
    private String bookName;
    private String bookAuthor;
    private int quant;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println(name);
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
        System.out.println(this.dob);
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAddNewCustomer() {
        return addNewCustomer;
    }

    public void setAddNewCustomer(boolean addNewCustomer) {
        this.addNewCustomer = addNewCustomer;
    }

    public void addCustomerAction(ActionEvent actionEvent) {
        setAddNewCustomer(true);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String addCustomer() {
        Customer customer = new Customer();
        dob = dob.replaceAll("-", " ");
        if (!libraryUtility.isValidName(name) || name == null || !libraryUtility.isValidAddress(address) || address == null || !libraryUtility.isValidDob(dob) || dob == null)
            return message = "Please Enter Valid values";
        if (!libraryUtility.isValidMobNo(mobileNumber)) return message = "Please enter correct mobile number";
        System.out.println(dob);
        customer.setName(name);
        customer.setAddress(address);
        customer.setDob(dob);
        customer.setMobNumber(mobileNumber);
        System.out.println(customer);
        return message = libraryUtility.addCustomer(customer, loginUtility.getSessionfactory());
    }

    public boolean orderBook() throws IOException {
        boolean isBookAdd = libraryUtility.orderBook(vendorId, bookName, bookAuthor, quant, loginUtility.getSessionfactory());
        message = isBookAdd == true ? "Books ordered successfully" : "please enter correct details ";
        return isBookAdd;
    }


    public boolean isOrderNewBooks() {
        return orderNewBooks;
    }

    public void setOrderNewBooks(boolean orderNewBooks) {
        this.orderNewBooks = orderNewBooks;
    }

    public void orderBooksAction(ActionEvent event) {
        setOrderNewBooks(true);
    }

    private boolean bookIssueboolean = false;

    public boolean isBookIssueboolean() {
        return bookIssueboolean;
    }

    public void setBookIssueboolean(boolean bookIssueboolean) {
        this.bookIssueboolean = bookIssueboolean;
    }

    public void issueBookAction(ActionEvent event) {
        setBookIssueboolean(true);
    }

    private String customerId;
    private int barCode;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public void issueBook() {
        message = libraryUtility.issueBook(customerId, barCode, loginUtility.getSessionfactory());
    }

    private boolean returnBookBoolean = false;

    public boolean isReturnBookBoolean() {
        return returnBookBoolean;
    }

    public void setReturnBookBoolean(boolean returnBookBoolean) {
        this.returnBookBoolean = returnBookBoolean;
    }

    public void returnBookAction(ActionEvent event) {
        setReturnBookBoolean(true);
    }

    public void returnBook() {
        message = libraryUtility.returnBook(customerId, barCode, loginUtility.getSessionfactory(), money);
    }

    private boolean fineOnBook = false;
    private double money;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isFineOnBook() {
        return fineOnBook;
    }

    public void setFineOnBook(boolean fineOnBook) {
        this.fineOnBook = fineOnBook;
    }


    public void fineAction(ActionEvent event) {
        double fine = checkFine();
        if (fine > 0) {
            message = "Fine on this book " + fine;
            setFineOnBook(true);
        }
    }

    public double checkFine() {
        return libraryUtility.fineOnBook(customerId, barCode, loginUtility.getSessionfactory());
    }

    public boolean isShowBooksIssuedByCustomer() {
        return showBooksIssuedByCustomer;
    }

    public void setShowBooksIssuedByCustomer(boolean showBooksIssuedByCustomer) {
        this.showBooksIssuedByCustomer = showBooksIssuedByCustomer;
    }

    public void showBooksIssuedByCustomerAction(ActionEvent actionEvent) {
        setShowBooksIssuedByCustomer(true);
    }

    public List<Book> showBooksByCustomer() {
        List<Book> bookList = customerUtility.booksIssuedByMe(customerId, loginUtility.getSessionfactory());
        if (bookList.size() == 0) message = "This customer has not issued any book. ";
        return bookList;
    }

    public boolean isSubmitClicked() {
        return submitClicked;
    }

    public void setSubmitClicked(boolean submitClicked) {
        this.submitClicked = submitClicked;
    }

    public void submitClickedAction(ActionEvent event) {
        setSubmitClicked(true);
    }

    public List<Vendor> showVendors() throws IOException {
        List<Vendor> vendorList = libraryUtility.showVendorList(loginUtility.getSessionfactory());
        if (vendorList.size() == 0) message = "No vendors Found";
        return vendorList;
    }

    public List<String> showVendorsName() throws IOException {
        List<Vendor> list = showVendors();
        List<String> names = new ArrayList<>();
        for (Vendor vendor : list) {
            names.add(vendor.getId());
        }
        return names;
    }

    public List<String> showVendorBooks() throws IOException {
        Map<String, List<String>> map = parseVendorData.getVendorBooksName();
        if (map.containsKey(vendorId)) {
            return map.get(vendorId);
        }
        return null;
    }

}
