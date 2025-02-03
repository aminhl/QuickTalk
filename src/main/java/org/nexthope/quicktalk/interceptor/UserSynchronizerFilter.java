package org.nexthope.quicktalk.interceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.nexthope.quicktalk.user.UserSynchronizer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserSynchronizerFilter extends OncePerRequestFilter {

    private final UserSynchronizer userSynchronizer;

    /**
     * Performs user synchronization with the Identity Provider (IdP) for authenticated requests.
     * This method is called once per request and checks if the current authentication is not anonymous.
     * If a valid JWT authentication token is present, it synchronizes the user information with the IdP.
     *
     * @param request     The HTTP servlet request being processed.
     * @param response    The HTTP servlet response being created.
     * @param filterChain The filter chain for invoking the next filter in the chain.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs during the processing of the request.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            userSynchronizer.synchronizeWithIdp(jwtAuthToken.getToken());
        }
    }

}
