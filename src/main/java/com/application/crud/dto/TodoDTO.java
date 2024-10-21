package com.application.crud.dto;

import lombok.Data;

/**
 * Author: Danyal Cheema Date:10/21/2024
 */

@Data
public class TodoDTO {

  private Long userId;
  private Long id;
  private String title;
  private Boolean completed;
}
