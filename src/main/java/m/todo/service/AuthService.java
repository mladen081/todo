package m.todo.service;

import m.todo.dto.JwtAuthResponse;
import m.todo.dto.LoginDto;
import m.todo.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
}
