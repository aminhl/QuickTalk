package org.nexthope.quicktalk.user;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserMapper {

    /**
     * Creates a User object from token attributes.
     *
     * This method maps the attributes from an authentication token to a User object.
     * It sets the user's ID, first name, last name, email, and last seen time.
     *
     * @param attributes A Map containing key-value pairs of user attributes from the token.
     *                   Expected keys include "sub" for ID, "given_name" or "nick_name" for first name,
     *                   "family_name" for last name, and "email" for email address.
     * @return A User object populated with information from the token attributes.
     */
    public User fromTokenAttributes(Map<String, Object> attributes) {
        User user = new User();
        user.setId(attributes.getOrDefault("sub", null).toString());
        user.setFirstname(attributes.getOrDefault("given_name",
                attributes.getOrDefault("nick_name", null)).toString());
        user.setLastname(attributes.getOrDefault("family_name", null).toString());
        user.setEmail(attributes.getOrDefault("email", null).toString());
        user.setLastSeen(LocalDateTime.now());
        return user;
    }

}
