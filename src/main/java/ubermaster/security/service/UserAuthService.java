package ubermaster.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ubermaster.entity.model.User;
import ubermaster.entity.security.JwtUser;

@Service
public class UserAuthService<T extends User> {

    @Autowired
    private JwtAuthenticationProvider authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    public String authUser(String phoneNumber, String password) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        phoneNumber,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtUser jwtUser = userDetailsService.loadUserByUsername(phoneNumber);

        if (!jwtUser.isEnabled()) {
            throw new IllegalArgumentException("User is Blocked");
        }

        return jwtTokenUtil.generateToken(jwtUser);
    }
}
