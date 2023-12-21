package com.juank.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juank.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
