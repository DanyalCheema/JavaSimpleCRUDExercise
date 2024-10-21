package com.application.crud.service;

import com.application.crud.dto.TodoDTO;
import java.util.List;

public interface JsonPlaceholderService {

  List<TodoDTO> fetchTodos();
}
