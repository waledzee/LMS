package service;

import dto.UserDto;
import entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserDto user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserDto user);
    void deleteUser(Long id);
}
