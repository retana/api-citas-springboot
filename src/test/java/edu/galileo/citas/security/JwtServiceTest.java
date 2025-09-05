package edu.galileo.citas.security;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    JwtService jwtService;

    @BeforeEach
    void setup() throws Exception {
        jwtService = new JwtService();
        setField(jwtService, "secret", "ZmFrZV9zZWNyZXRfZm9yX2VkdV9nYWxpbGVvX2NpdGFzX2FwaV90ZXN0");
        setField(jwtService, "expirationMs", 60000L);
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void generateAndValidateToken() {
        String token = jwtService.generateToken("alice");
        assertNotNull(token);
        assertEquals("alice", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, "alice"));
        assertFalse(jwtService.isTokenValid(token, "bob"));
    }
}
