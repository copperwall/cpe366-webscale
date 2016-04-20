
import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        /* Uncomment this to throw an error every time
        currentInstance.addMessage(null,
         new FacesMessage(FacesMessage.SEVERITY_ERROR,
          "Incorrect login", "Please enter a valid login"));
        */
        return "home";
    }

    public String logout() {
        // Get the session, invalidate it.
        return "login";
    }
}
