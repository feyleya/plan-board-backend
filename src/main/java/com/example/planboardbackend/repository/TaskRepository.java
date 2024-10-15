package com.example.planboardbackend.repository;

import com.example.planboardbackend.model.Task;
import com.example.planboardbackend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByUser(User user);

    Optional<Task> findByIdAndUser(Long id, User user);

    boolean existsByIdAndUser(Long id, User user);

}
