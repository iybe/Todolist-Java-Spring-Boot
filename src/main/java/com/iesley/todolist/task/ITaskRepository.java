package com.iesley.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID>{
    List<TaskModel> findByIdUser(UUID idUser);
    @Transactional
    void deleteByIdUser(UUID idUser);
}
