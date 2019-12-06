package org.codeAdvisorGroup.CodeAdvisor.repositories;

import org.codeAdvisorGroup.CodeAdvisor.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
