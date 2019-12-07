package org.codeAdvisorGroup.CodeAdvisor;

import org.codeAdvisorGroup.CodeAdvisor.entities.Code;
import org.codeAdvisorGroup.CodeAdvisor.entities.Role;
import org.codeAdvisorGroup.CodeAdvisor.entities.User;
import org.codeAdvisorGroup.CodeAdvisor.repositories.RoleRepository;
import org.codeAdvisorGroup.CodeAdvisor.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run(UserRepository userRepository, RoleRepository roleRepository,BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        return (String[] args) -> {
            Role ROLE_USER=new Role();
            ROLE_USER.setName("ROLE_USER");
            roleRepository.save(ROLE_USER);

            User user1=new User();
            user1.setUsername("Bill");
            user1.setPassword(bCryptPasswordEncoder.encode("pass"));
            user1.setRoles(new HashSet<Role>(Arrays.asList(ROLE_USER)));
            Code code1=new Code(user1,"HelloWorld.java",
                    "public class HelloWorld {\n" +
                            "\n" +
                            "    public static void main(String[] args) {\n" +
                            "        System.out.println(\"Hello, World\");\n" +
                            "    }\n" +
                            "\n" +
                            "}");
            Code code2=new Code(user1,"FooBar.java",
                    "public class FooBar {\n" +
                            "\n" +
                            "    public static void main(String[] args) {\n" +
                            "        System.out.println(\"FooBar\");\n" +
                            "    }\n" +
                            "\n" +
                            "}");
            user1.setCodeList(Arrays.asList(code1,code2));
            userRepository.save(user1);
            userRepository.findAll().forEach(user -> System.out.println(user));
        };
    }
}