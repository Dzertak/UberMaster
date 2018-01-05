package ubermaster.security.Service;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by stephan on 04.07.17.
 */
@Component
public class TimeProvider implements Serializable {

    public Date now() {
        return new Date();
    }
}
