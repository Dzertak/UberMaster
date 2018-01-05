package ubermaster.security.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ubermaster.entity.model.User;
import ubermaster.entity.security.JwtUser;
import ubermaster.persistence.facade.Facade;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl<T extends User> implements UserDetailsService {

    @Autowired
    private Facade facade;

    @Override
    public JwtUser loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        T user = facade.getUserByPhone(phoneNumber);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with phone number: " + phoneNumber));
        } else {
            return new JwtUser<>(user);
        }
    }
}
