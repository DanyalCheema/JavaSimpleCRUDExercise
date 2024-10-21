package com.application.crud.service;

import com.application.crud.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;


public interface UserService {

  User addUser(User user);

  User editUser(Long id, User updatedUser);

  void deleteUser(Long id);

  List<User> findUsersByRoleName(String roleName);

  Page<User> searchUsers(String name, int page, int size);
}
