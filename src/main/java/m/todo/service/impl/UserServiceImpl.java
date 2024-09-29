package m.todo.service.impl;

import lombok.AllArgsConstructor;
import m.todo.entity.User;
import m.todo.exception.ResourceNotFoundException;
import m.todo.repository.UserRepository;
import m.todo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Clear the roles to remove associations
        user.getRoles().clear();
        userRepository.save(user); // Save changes to clear roles

        // Now delete the user
        userRepository.delete(user);
    }

}

