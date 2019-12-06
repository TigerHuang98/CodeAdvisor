package org.codeAdvisorGroup.CodeAdvisor.services;

import org.codeAdvisorGroup.CodeAdvisor.entities.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
