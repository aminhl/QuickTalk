package org.nexthope.quicktalk.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    /**
     * Synchronizes user information with the Identity Provider (IdP) based on the provided JWT token.
     * This method extracts the user's email from the token, creates or updates the user in the repository,
     * and logs the synchronization process.
     *
     * @param token The JWT token containing the user's claims and information from the IdP.
     *              This token is used to extract the user's email and other attributes.
     */
    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with idp");
        getUserEmailFromToken(token).ifPresent(userEmail -> {
            log.info("Synchronizing user with email: {}", userEmail);
            User user = userMapper.fromTokenAttributes(token.getClaims());
            userRepository.save(user);
        });
    }

    /**
     * Extracts the user's email address from the provided JWT token.
     *
     * @param token The JWT token containing user claims.
     * @return An Optional containing the user's email address if present in the token,
     *         or an empty Optional if the email is not found.
     */
    private Optional<String> getUserEmailFromToken(Jwt token) {
        Map<String, Object> attributes = token.getClaims();
        return Optional.ofNullable(attributes.get("email")).map(Object::toString);
    }

}
