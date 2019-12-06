package org.codeAdvisorGroup.CodeAdvisor.repositories;

import org.codeAdvisorGroup.CodeAdvisor.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Long> {

    @Query("FROM User u WHERE u.name=:name")
    User findByUsername(@Param("name") String name);

}
