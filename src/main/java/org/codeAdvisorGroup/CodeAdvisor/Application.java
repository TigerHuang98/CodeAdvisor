package org.codeAdvisorGroup.CodeAdvisor;

import org.codeAdvisorGroup.CodeAdvisor.entities.User;
import org.codeAdvisorGroup.CodeAdvisor.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run(UserRepository userRepository) throws Exception {
        return (String[] args) -> {
            User user1 = new User("Bill");
            userRepository.save(user1);
            userRepository.findAll().forEach(user -> System.out.println(user));
        };
    }
}