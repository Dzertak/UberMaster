package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ubermaster.entity.model.User;
import ubermaster.entity.security.JwtAuthenticationRequest;
import ubermaster.entity.security.JwtUser;
import ubermaster.persistence.facade.Facade;
import ubermaster.security.service.JwtAuthenticationProvider;
import ubermaster.security.service.JwtTokenUtil;
import ubermaster.security.service.JwtUserDetailsServiceImpl;

import javax.servlet.ServletException;

@RestController
@RequestMapping
public class SignController<T extends User> {

    @Autowired
    private Facade facade;

    @RequestMapping(value = "/login",
            method = RequestMethod.POST)
    public T logInUser(@RequestBody JwtAuthenticationRequest loginUser) throws ServletException {
        if (loginUser == null || loginUser.getPhoneNumber() == null || loginUser.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String phoneNumber = loginUser.getPhoneNumber();
        String password = loginUser.getPassword();

        T user = facade.getUserByPhone(phoneNumber);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
            throw new ServletException("Wrong password");
        }
        throw new ServletException("There is no such user");
    }

    @RequestMapping(value = "/register",
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
    }
}
