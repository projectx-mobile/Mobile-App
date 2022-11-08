package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.Task;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TaskServiceImplTest.class)
class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;
    @Mock
    private  TaskRepository taskRepository;

    private static Task task;

    private static final String TITLE = "testTitle";

    @BeforeAll
    static void setUp() {
        task = Task.builder()
                .id(1L)
                .title(TITLE)
                .build();
    }

    @Test
    void findByTitlePositive() {
        when(taskRepository.findByTitle(TITLE)).thenReturn(Optional.ofNullable(task));

        Task task1 = taskService.findByTitle(TITLE);

        assertEquals(task1, task);
    }

    @Test
    void findByTitleNegative() {
        when(taskRepository.findByTitle("")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> taskService.findByTitle(""));
    }

    @Test
    void save() {
        when(taskRepository.save(any())).thenReturn(task);

        Task taskDb = taskService.save(task);

        assertEquals(taskDb, task);
    }
}