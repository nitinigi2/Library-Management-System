package com.info.pageSource;

import com.info.utility.LoginUtility;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class indexPageSource implements Serializable {
    LoginUtility loginUtility = new LoginUtility();
    public void login(String userName, String password, String type){
        System.out.println("Nitin " + userName + " " + password);
        loginUtility.login(userName, password, type);
    }
}
