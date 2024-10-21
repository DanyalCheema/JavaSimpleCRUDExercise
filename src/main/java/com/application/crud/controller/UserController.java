package com.application.crud.controller;

import com.application.crud.entity.User;
import com.application.crud.service.UserService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<User> addUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.addUser(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
    return ResponseEntity.ok(userService.editUser(id, user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/role/{roleName}")
  public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
    List<User> users = userService.findUsersByRoleName(roleName);
    return ResponseEntity.ok(users);
  }

  @GetMapping("/search")
  public ResponseEntity<Page<User>> searchUsers(
      @RequestParam(required = false) String name,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<User> users = userService.searchUsers(name, page, size);
    return ResponseEntity.ok(users);
  }

}
