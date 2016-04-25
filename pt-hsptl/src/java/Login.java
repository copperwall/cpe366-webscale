
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Scott Vanderlind
 */
@Named(value = "login")
@SessionScoped
@ManagedBean
public class Login implements Serializable {

    private String login;
    private String password;
 
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String checkCredentials() {
        System.out.println("checking");

        if (this.validate(login, password)) {
            SessionBean.setUserLogin(login);
            System.out.println("Login success");
            return "home";
        } else {
            System.out.println("Fail");
            FacesContext currentInstance = FacesContext.getCurrentInstance();
            currentInstance.addMessage(null,
             new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "Incorrect login", "Please enter a valid login"));
        }
        return "login";
    }

    /**
     * This should probably move to an Auth class, or maybe into a User
     * object.
     */
    private boolean validate(String login, String password) {
        DB db = new DB();
        Connection conn = db.getConnection();

        PreparedStatement checkCredentials;
        String check = "SELECT COUNT(*) FROM employees WHERE login = ? and password = ?";
        ResultSet rs;
        Boolean isValid = false;

        try {
            checkCredentials = conn.prepareStatement(check);
            checkCredentials.setString(1, login);
            checkCredentials.setString(2, password);

            rs = checkCredentials.executeQuery();
            rs.next();
            isValid = (rs.getInt(1) == 1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return isValid;
    }

    public String logout() {
        // Get the session, invalidate it.
        SessionBean.getSession().invalidate();
        return "login";
    }
}
