package ubermaster.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ubermaster.entity.model.BlockedUser;
import ubermaster.entity.model.User;
import ubermaster.entity.security.JwtUser;
import ubermaster.persistence.facade.Facade;

@Service
public class JwtUserDetailsServiceImpl<T extends User> implements UserDetailsService {

    @Autowired
    private Facade facade;

    @Override
    public JwtUser loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        T user = facade.getUserByPhone(phoneNumber);

        if (user != null) {
            if (BlockedUser.class.isAssignableFrom(user.getClass())) {
                if (((BlockedUser) user).getBlocked()) {
                    throw new UsernameNotFoundException("User is blocked ");
                }
                return new JwtUser(user);
            }
            return new JwtUser(user);
        }
        throw new UsernameNotFoundException("No user found with phone number: " + phoneNumber);
    }
}
