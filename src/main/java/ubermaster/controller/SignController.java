package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import ubermaster.entity.model.Master;
import ubermaster.entity.model.Poke;
import ubermaster.entity.model.User;
import ubermaster.entity.security.JwtAuthenticationRequest;
import ubermaster.entity.security.JwtUser;
import ubermaster.errorHandler.ErrorHandler;
import ubermaster.errorHandler.Errors;
import ubermaster.persistence.facade.Facade;
import ubermaster.security.service.JwtAuthenticationProvider;
import ubermaster.security.service.JwtTokenUtil;
import ubermaster.security.service.JwtUserDetailsServiceImpl;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping
public class SignController<T extends User> {

    @Autowired
    private Facade facade;

    @RequestMapping(value = "/login",
            method = RequestMethod.POST)
    public T logInUser(@RequestBody JwtAuthenticationRequest loginUser) throws SQLException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, ServletException {
        if (loginUser == null || loginUser.getPhoneNumber() == null || loginUser.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String phoneNumber = loginUser.getPhoneNumber();
        String password = new String(Base64Utils.decode(loginUser.getPassword().getBytes()));

        T user = facade.getUserByPhone(phoneNumber);
        if (user != null) {
            if (new String(Base64Utils.decode(user.getPassword().getBytes())).equals(password)) {
                return user;
            }
            //throw new ServletException("Wrong password");
            throw ErrorHandler.createServletException(Errors.ser_10);
        }
        throw ErrorHandler.createServletException(Errors.ser_2);
    }

    /*@RequestMapping(value = "/register",
            method = RequestMethod.POST)
    public void registerUser(@RequestBody T loginUser) throws ServletException {
        if (loginUser != null && loginUser.getPhoneNumber() != null && loginUser.getPassword() != null) {
            String phoneNumber = loginUser.getPhoneNumber();

            if (facade.getUserByPhone(phoneNumber) == null) {
                facade.createEntity(loginUser);
            } else {
                throw new ServletException("There is user with such phone number");
            }
        }
        throw new ServletException("Wrong request!");
    }*/

    @RequestMapping
            (
                    value = "/registerPoke",
                    method = RequestMethod.POST,
                    produces = "application/json"
            )
    public String addPoke
            (
                    @RequestBody Poke poke
            ) throws SQLException, ParserConfigurationException, SAXException, IOException, ServletException {
        try{
            facade.createEntity(poke);
        }
        catch(SQLException exc){
            throw ErrorHandler.createServletException(Errors.ser_1);
        }


        return "DONE";
    }

    @RequestMapping
            (
                    value = "/registerMaster",
                    method = RequestMethod.POST,
                    produces = "application/json"
            )
    public String addMaster
            (
                    @RequestBody Master master
            ) throws SQLException, ParserConfigurationException, SAXException, IOException, ServletException {
        try{
            facade.createEntity(master);
        }
        catch(SQLException exc){
            throw ErrorHandler.createServletException(Errors.ser_1);
        }

        return "DONE";
    }
}
