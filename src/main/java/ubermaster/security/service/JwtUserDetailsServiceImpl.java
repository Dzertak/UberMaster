package ubermaster.security.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import ubermaster.entity.model.User;
import ubermaster.entity.security.JwtUser;
import ubermaster.errorHandler.ErrorHandler;
import ubermaster.errorHandler.Errors;
import ubermaster.persistence.facade.Facade;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl<T extends User> implements UserDetailsService {

    private static Logger log = Logger.getLogger(ErrorHandler.class.getName());

    @Autowired
    private Facade facade;

    @Override
    public JwtUser loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        try {
        T user = facade.getUserByPhone(phoneNumber);

        if (user == null) {
            //throw new UsernameNotFoundException("No user found with phone number: " + phoneNumber);
           throw  ErrorHandler.createUsernameNotFoundException(Errors.ser_1);
        } else {
            return new JwtUser<>(user);
        }
        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }
}
