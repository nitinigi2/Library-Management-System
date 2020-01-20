package com.info.pageSource;

import com.info.utility.LoginUtility;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class loginPageSource implements Serializable {

    private static CustomerPageSource customerPageSource = new CustomerPageSource();

    private String name;
    private String password;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String login(){
        LoginUtility loginUtility = new LoginUtility();
        setUserInfo(name, password);

        System.out.print("Name: " + name + " Password: " + password);
        return loginUtility.login(name, password, type);
    }

    public String logOut(){
        System.out.println("Logout called");
        customerPageSource = new CustomerPageSource();
        return "index";
    }


    public  void setUserInfo(String userName, String password){
        customerPageSource.setUserName(userName);
        customerPageSource.setPassword(password);
    }

    public CustomerPageSource getUserInfo(){
        return customerPageSource;
    }


}
