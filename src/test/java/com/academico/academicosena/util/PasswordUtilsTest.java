package com.academico.academicosena.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class PasswordUtilsTest {

    @Test
    public void testHashPassword() {
        String plain = "miContraseña123";
        String hashed = PasswordUtils.hashPassword(plain);
        
        assertNotNull("Hash no debería ser nulo", hashed);
        assertNotEquals("Hash debería ser diferente al plaintext", plain, hashed);
        assertTrue("Hash debería empezar con $2a$, $2b$ o $2y$", 
            hashed.startsWith("$2a$") || hashed.startsWith("$2b$") || hashed.startsWith("$2y$"));
    }

    @Test
    public void testVerifyPassword_Correct() {
        String plain = "miContraseña123";
        String hashed = PasswordUtils.hashPassword(plain);
        
        boolean result = PasswordUtils.verifyPassword(plain, hashed);
        assertTrue("Debería verificar correctamente la contraseña", result);
    }

    @Test
    public void testVerifyPassword_Incorrect() {
        String plain = "miContraseña123";
        String hashed = PasswordUtils.hashPassword(plain);
        String wrongPassword = "otraContraseña";
        
        boolean result = PasswordUtils.verifyPassword(wrongPassword, hashed);
        assertFalse("Debería rechazar contraseña incorrecta", result);
    }

    @Test
    public void testVerifyPassword_NullPlain() {
        String hashed = PasswordUtils.hashPassword("test");
        boolean result = PasswordUtils.verifyPassword(null, hashed);
        assertFalse("Debería retornar false si plaintext es null", result);
    }

    @Test
    public void testVerifyPassword_NullHash() {
        boolean result = PasswordUtils.verifyPassword("test", null);
        assertFalse("Debería retornar false si hash es null", result);
    }

    @Test
    public void testHashPassword_Null() {
        String result = PasswordUtils.hashPassword(null);
        assertNull("Debería retornar null si input es null", result);
    }

    @Test
    public void testHashPassword_DifferentHashesEachTime() {
        String plain = "miContraseña123";
        String hash1 = PasswordUtils.hashPassword(plain);
        String hash2 = PasswordUtils.hashPassword(plain);
        
        // BCrypt usa salt aleatorio, así que hashes diferentes son normales
        assertNotEquals("Dos hashes del mismo plaintext deberían ser diferentes (salt aleatorio)", hash1, hash2);
        
        // Pero ambos deberían verificarse correctamente
        assertTrue("Hash1 debería verificarse", PasswordUtils.verifyPassword(plain, hash1));
        assertTrue("Hash2 debería verificarse", PasswordUtils.verifyPassword(plain, hash2));
    }

    @Test
    public void testVerifyPassword_SpecialCharacters() {
        String plain = "P@ss\\w0rd!#$%^&*()";
        String hashed = PasswordUtils.hashPassword(plain);
        
        boolean result = PasswordUtils.verifyPassword(plain, hashed);
        assertTrue("Debería manejar caracteres especiales correctamente", result);
    }

    @Test
    public void testVerifyPassword_LongPassword() {
        String plain = "a".repeat(100);  // 100 caracteres
        String hashed = PasswordUtils.hashPassword(plain);
        
        boolean result = PasswordUtils.verifyPassword(plain, hashed);
        assertTrue("Debería manejar contraseñas largas correctamente", result);
    }
}
