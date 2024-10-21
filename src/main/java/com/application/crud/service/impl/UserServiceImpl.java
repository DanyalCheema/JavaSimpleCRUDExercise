package com.application.crud.service.impl;

import com.application.crud.entity.User;
import com.application.crud.repository.UserRepository;
import com.application.crud.service.UserService;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User addUser(User user) {
    validateUser(user);
    try {
      return userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw new IllegalArgumentException("Email already exists: " + user.getEmail());
    }
  }

  @Override
  public User editUser(Long id, User updatedUser) {
    validateUser(updatedUser);
    return userRepository.findById(id).map(user -> {
      user.setName(updatedUser.getName());
      user.setEmail(updatedUser.getEmail());
      user.setRole(updatedUser.getRole());
      try {
        return userRepository.save(user);
      } catch (DataIntegrityViolationException e) {
        throw new IllegalArgumentException("Email already exists: " + user.getEmail());
      }
    }).orElseThrow(() -> new RuntimeException("User not found"));
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public List<User> findUsersByRoleName(String roleName) {
    return userRepository.findUsersByRoleName(roleName);
  }

  public Page<User> searchUsers(String name, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    if (StringUtils.hasText(name)) {
      return userRepository.findByNameContainingIgnoreCase(name, pageable);
    }
    return userRepository.findAll(pageable);
  }

  private void validateUser(User user) {
    if (!StringUtils.hasText(user.getName())) {
      throw new IllegalArgumentException("User name is required");
    }
    if (!StringUtils.hasText(user.getEmail())) {
      throw new IllegalArgumentException("User email is required");
    }
    if (user.getRole() == null || !StringUtils.hasText(user.getRole().getRoleName())) {
      throw new IllegalArgumentException("Role is required");
    }
  }
}
