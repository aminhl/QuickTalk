package org.nexthope.quicktalk.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nexthope.quicktalk.chat.Chat;
import org.nexthope.quicktalk.commons.BaseAuditing;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseAuditing {

    private static final short LAST_ACTIVE_INTERVAL = 5 ;
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(name = "first_name", nullable = false)
    private String email;

    private LocalDateTime lastSeen;

    @OneToMany
    private List<Chat> chatsAsSender;

    @OneToMany
    private List<Chat> chatsAsRecipient;

    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }



}
