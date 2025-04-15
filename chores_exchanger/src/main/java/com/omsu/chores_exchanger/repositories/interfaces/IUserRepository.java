package com.omsu.chores_exchanger.repositories.interfaces;

import com.omsu.chores_exchanger.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {}
