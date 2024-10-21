package com.application.crud.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.application.crud.dto.TodoDTO;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class JsonPlaceholderServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private JsonPlaceholderServiceImpl jsonPlaceholderService;

    @Value("${todo.api.url}")
    private String TODO_URL;

    private TodoDTO todo1;
    private TodoDTO todo2;

    @BeforeEach
    void setUp() {
        todo1 = new TodoDTO();
        todo1.setId(1L);
        todo1.setTitle("Test Todo 1");
        todo1.setCompleted(false);

        todo2 = new TodoDTO();
        todo2.setId(2L);
        todo2.setTitle("Test Todo 2");
        todo2.setCompleted(true);
    }

    @Test
    void testFetchTodos_Success() {
        List<TodoDTO> todos = Arrays.asList(todo1, todo2);
        ResponseEntity<List<TodoDTO>> responseEntity = ResponseEntity.ok(todos);

        when(restTemplate.exchange(
            eq(TODO_URL),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        List<TodoDTO> result = jsonPlaceholderService.fetchTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(todo1.getTitle(), result.get(0).getTitle());
        assertEquals(todo2.getTitle(), result.get(1).getTitle());
        verify(restTemplate, times(1)).exchange(
            eq(TODO_URL),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void testFetchTodos_EmptyList() {
        ResponseEntity<List<TodoDTO>> responseEntity = ResponseEntity.ok(List.of());

        when(restTemplate.exchange(
            eq(TODO_URL),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        List<TodoDTO> result = jsonPlaceholderService.fetchTodos();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(restTemplate, times(1)).exchange(
            eq(TODO_URL),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void testFetchTodos_ApiError() {
        when(restTemplate.exchange(
            eq(TODO_URL),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        )).thenThrow(new RuntimeException("API Error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            jsonPlaceholderService.fetchTodos();
        });

        assertEquals("API Error", exception.getMessage());
        verify(restTemplate, times(1)).exchange(
            eq(TODO_URL),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        );
    }
}
