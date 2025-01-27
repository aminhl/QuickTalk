package org.nexthope.quicktalk.chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nexthope.quicktalk.commons.BaseAuditing;
import org.nexthope.quicktalk.message.Message;
import org.nexthope.quicktalk.user.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat")
public class Chat extends BaseAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String chatId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate desc")
    private List<Message> messages;

}
