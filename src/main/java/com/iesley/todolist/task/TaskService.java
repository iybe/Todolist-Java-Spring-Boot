package com.iesley.todolist.task;

import java.time.LocalDateTime;

public class TaskService {
    public static void validateCreateService(TaskModel task) throws Exception {
        if(task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new Exception("O título é obrigatório");
        }

        if(task.getDescription() == null || task.getDescription().isEmpty()) {
            throw new Exception("A descrição é obrigatória");
        }

        if(task.getStartAt() == null) {
            throw new Exception("A data de início é obrigatória");
        }

        if(task.getEndAt() == null) {
            throw new Exception("A data de término é obrigatória");
        }

        if(task.getPriority() == null || task.getPriority().isEmpty()) {
            throw new Exception("A prioridade é obrigatória");
        }

        var currentDate = LocalDateTime.now();
        
        if(currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
            throw new Exception("A data de início / data de término deve ser maior do que a data atual");
        }

        if(task.getStartAt().isAfter(task.getEndAt())) {
            throw new Exception("A data de início deve ser menor que a data de término");
        }
    }

    public static void copyNonNullProperties(TaskModel task1, TaskModel task2) {
        if(task1.getTitle() != null) {
            task2.setTitle(task1.getTitle());
        }

        if(task1.getDescription() != null) {
            task2.setDescription(task1.getDescription());
        }

        if(task1.getStartAt() != null) {
            task2.setStartAt(task1.getStartAt());
        }

        if(task1.getEndAt() != null) {
            task2.setEndAt(task1.getEndAt());
        }

        if(task1.getPriority() != null) {
            task2.setPriority(task1.getPriority());
        }
    }
}
