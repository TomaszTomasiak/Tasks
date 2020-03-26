package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskController taskController;

    @Test
    public void schouldFetchEmptyListOfTasks() throws Exception{
        //Given
        List<TaskDto> tasks = new ArrayList<>();
        when(taskController.getTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void schouldFetchNotEmptyListOfTasks() throws Exception{
        //Given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(15L, "Task_title", "Task_content"));
        when(taskController.getTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(15)))
                .andExpect(jsonPath("$[0].title", is("Task_title")))
                .andExpect(jsonPath("$[0].content", is("Task_content")));
    }

    @Test
    public void shouldGetTaskWithIndicatedId() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(15L, "Task_title", "Task_content");
        when(taskController.getTask(15L)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=15").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(15)))
                .andExpect(jsonPath("title", is("Task_title")))
                .andExpect(jsonPath("content", is("Task_content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception{
        //Given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(15L, "Task_title", "Task_content"));
        when(taskController.getTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=15").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void schouldUpdateTask() throws Exception {
        //Given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(15L, "Task_title", "Task_content"));

        TaskDto updatedTaskDto = new TaskDto(15L, "Updated_Task_title", "Updated_Task_content");
        when(taskController.updateTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(updatedTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedTaskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(15)))
                .andExpect(jsonPath("$.title", is("Updated_Task_title")))
                .andExpect(jsonPath("$.content", is("Updated_Task_content")));
    }


    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(15L, "Task_title", "Task_content");
        when(taskController.createTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(15)))
                .andExpect(jsonPath("$.title", is("Task_title")))
                .andExpect(jsonPath("$.content", is("Task_content")));
    }
}