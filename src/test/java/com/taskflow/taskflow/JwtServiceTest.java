package com.taskflow.taskflow;
import com.taskflow.taskflow.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class JwtServiceTest {
    private JwtService jwtService;

    @BeforeEach
    void setUp(){
        jwtService = new JwtService();
    }
    @Test
    void generateToken_returnsToken(){
        String token = jwtService.generateToken("name");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
    @Test
    void extractUsername_returnsCorrectUsername(){
        String token = jwtService.generateToken("name");
        String username = jwtService.extractUsername(token);
        assertEquals("name", username);
    }
    @Test
    void  isTokenValid_returnsTrueForValidToken(){
        String token = jwtService.generateToken("name");
        assertTrue(jwtService.isTokenValid(token));
    }
    @Test
    void isTokenValid_returnsFalseForInvalidToken(){
        String token = jwtService.generateToken("name");
        assertFalse(jwtService.isTokenValid("invalid.token.here"));
    }

}
