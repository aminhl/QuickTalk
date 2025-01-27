package org.nexthope.quicktalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class QuickTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickTalkApplication.class, args);
    }

}
