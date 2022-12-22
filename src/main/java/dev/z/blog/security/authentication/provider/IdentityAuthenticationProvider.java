package dev.z.blog.security.authentication.provider;

import dev.z.blog.security.authentication.token.IdentityAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class IdentityAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication instanceof IdentityAuthenticationToken authenticationToken) {

        }
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
