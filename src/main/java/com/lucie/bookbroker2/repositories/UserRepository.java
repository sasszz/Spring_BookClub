package com.lucie.bookbroker2.repositories;

import java.util.Optional;

import com.lucie.bookbroker2.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

}