package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
