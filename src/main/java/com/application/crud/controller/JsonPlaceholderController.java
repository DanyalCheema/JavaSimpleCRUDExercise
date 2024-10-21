package com.application.crud.controller;

import com.application.crud.dto.TodoDTO;
import com.application.crud.service.JsonPlaceholderService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/json")
@AllArgsConstructor
public class JsonPlaceholderController {

  private final JsonPlaceholderService jsonPlaceholderService;

  @GetMapping("/todo")
  public List<TodoDTO> getTodos() {
    return jsonPlaceholderService.fetchTodos();
  }

}
