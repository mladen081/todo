package m.todo.service.impl;

import lombok.AllArgsConstructor;
import m.todo.dto.JwtAuthResponse;
import m.todo.dto.LoginDto;
import m.todo.dto.RegisterDto;
import m.todo.entity.Role;
import m.todo.entity.User;
import m.todo.exception.TodoApiException;
import m.todo.repository.RoleRepository;
import m.todo.repository.UserRepository;
import m.todo.security.JwtTokenProvider;
import m.todo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw  new TodoApiException(HttpStatus.BAD_REQUEST, "Username is already in use");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "User  Registered Successfully";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);

            Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());

            String role = null;

            if (userOptional.isPresent()) {
                User loggedInUser = userOptional.get();
                Optional<Role> optionalRole =  loggedInUser.getRoles().stream().findFirst();

                if (optionalRole.isPresent()) {
                    Role userRole = optionalRole.get();
                    role = userRole.getName();
                }
            }

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setRole(role);
            jwtAuthResponse.setAccessToken(token);

            return jwtAuthResponse;
        } catch (BadCredentialsException e) {
            // Catching BadCredentialsException thrown by authenticationManager
            throw new TodoApiException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }


}

