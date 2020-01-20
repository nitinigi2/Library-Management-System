package com.info.pageSource;

import com.info.bean.BookType;
import com.info.bean.Customer;
import com.info.bean.Search;
import com.info.utility.CommonUtility;
import com.info.utility.CustomerUtility;
import com.info.utility.LoginUtility;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class  CustomerPageSource implements Serializable {

    @EJB
    private CustomerUtility customerUtility;
    @EJB
    private LoginUtility loginUtility;

    @EJB
    private Search search;

    private String userName;
    private String password;

    private String bookProp;
    private String bookType;
    private String message="";
    private boolean show=false;
    private boolean showSearch=false;
    private boolean showBook = false;

    public String getBookProp() {
        return bookProp;
    }

    public void setBookProp(String bookProp) {
        this.bookProp = bookProp;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public CustomerPageSource(){}

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }

    public Customer showProfile(){
        loginPageSource loginPageSource  = new loginPageSource();
        CustomerPageSource customerPageSource = loginPageSource.getUserInfo();
        System.out.println("Nitin UserName: " + customerPageSource.getUserName() );
        return customerUtility.showProfile(customerPageSource.getUserName(), loginUtility.getSessionfactory());
    }

    private String bookName;
    private String bookAuthor;
    private int quantity;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void searchBooks(){
        System.out.println("Search book : " + "Book Prop : " + bookProp + " BookType "  + bookType);
        BookType bookTypes = search.searchBook(bookProp, bookType);
        if(bookTypes==null){message="No Book Found";}
        if(bookTypes!=null) {
            setBookName(bookTypes.getBookName());
            setBookAuthor(bookTypes.getAuthor());
            setQuantity(bookTypes.getBookQuantity());
        }
    }

    public void showMe(ActionEvent actionEvent){
        System.out.println("1 " + show);
        setShow(true);
        System.out.println("2 " + show);
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
        System.out.println(show);
    }


    public boolean isShowBook() {
        return showBook;
    }

    public void setShowBook(boolean showBook) {
        this.showBook = showBook;
    }

    public void showBooks(ActionEvent e){
        System.out.println(showBook);
        setShowBook(true);
    }

    public boolean isShowSearch() {
        return showSearch;
    }

    public void setShowSearch(boolean showSearch) {
        this.showSearch = showSearch;
    }

    public void showSearchAction(ActionEvent event){
        setShowSearch(true);
    }

    public String getMessage() {
        return message;
    }


    public void searchBookAction(ActionEvent event){

    }
}
