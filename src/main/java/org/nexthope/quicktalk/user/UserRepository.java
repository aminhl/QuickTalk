package org.nexthope.quicktalk.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.nexthope.quicktalk.user.UserConstants.FIND_USER_BY_EMAIL;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(name = FIND_USER_BY_EMAIL)
    Optional<User > findByEmail(String email);

}
