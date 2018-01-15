package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ubermaster.entity.security.JwtAuthenticationRequest;
import ubermaster.entity.security.JwtAuthenticationResponse;
import ubermaster.security.service.JwtTokenUtil;
import ubermaster.security.service.UserAuthService;

import javax.servlet.http.HttpServletRequest;

import static ubermaster.security.SecurityConstants.HEADER_STRING;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserAuthService userAuthService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        if (authenticationRequest == null
                || authenticationRequest.getPhoneNumber() == null
                || authenticationRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Wrong request!");
        }
        final String token = userAuthService
                .authUser(authenticationRequest.getPhoneNumber(),authenticationRequest.getPassword());

        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    //Coming soon
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String tokenHeader = HEADER_STRING;
        String token = request.getHeader(tokenHeader);

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body("Token cannot be refreshed!");
        }
    }

}