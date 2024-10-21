package com.application.crud.service.impl;

import com.application.crud.dto.TodoDTO;
import com.application.crud.service.JsonPlaceholderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JsonPlaceholderServiceImpl implements JsonPlaceholderService {

  private final RestTemplate restTemplate;

  @Value("${todo.api.url}")
  private String TODO_URL;

  public JsonPlaceholderServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<TodoDTO> fetchTodos() {
    ResponseEntity<List<TodoDTO>> responseEntity = restTemplate.exchange(
        TODO_URL,
        org.springframework.http.HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<TodoDTO>>() {
        }
    );
    return responseEntity.getBody();
  }
}
