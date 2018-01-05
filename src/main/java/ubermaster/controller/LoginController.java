package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ubermaster.entity.model.User;
import ubermaster.persistence.facade.Facade;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/login")
public class LoginController<T extends User> {

    @Autowired
    private Facade facade;

    @RequestMapping(
            method = RequestMethod.POST,
            produces = "application/json")
    public T getUsersByPhone(@RequestBody T loginUser) throws ServletException {
        if (loginUser.getPhoneNumber() == null || loginUser.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String phoneNumber = loginUser.getPhoneNumber();
        String password = loginUser.getPassword();
        T user = facade.getUser(phoneNumber, password);

        if (user == null) {
            throw new ServletException("There is no such user");
        }
        return user;
    }
}
