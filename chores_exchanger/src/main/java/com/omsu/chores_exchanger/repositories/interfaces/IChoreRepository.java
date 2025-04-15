package com.omsu.chores_exchanger.repositories.interfaces;

import com.omsu.chores_exchanger.models.entities.Chore;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChoreRepository extends JpaRepository<Chore, String> {
    @Transactional
    @Query("SELECT chore FROM Chore chore WHERE chore.status = :status")
    List<Chore> findAllByStatus(@Param(value = "status") String status);

    @Transactional
    @Query(value = "SELECT * FROM CHORE chore WHERE chore.creator_login IS NOT NULL and chore.creator_login = :login and chore.status = :status",
            nativeQuery = true)
    List<Chore> findByCreatorLoginAndStatus(@Param(value = "login") String login, @Param(value = "status") String status);

    @Transactional
    @Query(value = "SELECT * FROM CHORE chore WHERE chore.executor_login IS NOT NULL and chore.executor_login = :login and chore.status = :status",
            nativeQuery = true)
    List<Chore> findByExecutorLoginAndStatus(@Param(value = "login") String login, @Param(value = "status") String status);
}