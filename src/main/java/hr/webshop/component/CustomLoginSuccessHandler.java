package hr.webshop.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        // Get the roles of the logged-in user
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Loop through the roles and redirect accordingly
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/webshop/admin/products");
                return;
            } else if (role.equals("ROLE_USER")) {
                response.sendRedirect("/webshop/products");
                return;
            }
        }

        // fallback
        response.sendRedirect("/");
    }
}
