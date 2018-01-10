package ubermaster.entity.security;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationRequest implements Serializable {
    private String phoneNumber;
    private String password;

    public JwtAuthenticationRequest() {
    }

    public JwtAuthenticationRequest(String phoneNumber, String password) {
       this.phoneNumber = phoneNumber;
       this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
