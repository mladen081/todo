package m.todo.service;

import m.todo.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    void deleteUser(Long id);
}
