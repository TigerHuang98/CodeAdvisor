package org.codeAdvisorGroup.CodeAdvisor.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
