package com.taskflow.taskflow.controller;

// TODO: импорты
import com.taskflow.taskflow.model.User;
import com.taskflow.taskflow.model.Role;
import com.taskflow.taskflow.repository.UserRepository;
import com.taskflow.taskflow.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // TODO: конструктор со всеми четырьмя зависимостями
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          JwtService jwtService, AuthenticationManager authenticationManager) {
        this.authenticationManager =  authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService =  jwtService;
        this.userRepository = userRepository;

    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User registerted successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}