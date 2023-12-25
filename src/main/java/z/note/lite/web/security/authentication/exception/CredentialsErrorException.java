package z.note.lite.web.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class CredentialsErrorException extends AuthenticationException {

    public CredentialsErrorException(String msg) {
        super(msg);
    }

}
