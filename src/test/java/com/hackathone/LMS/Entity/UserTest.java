package com.hackathone.LMS.Entity;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import static org.junit.jupiter.api.Assertions.*;
 
public class UserTest {
    private User user;
 
    @BeforeEach
    void setUp() {
        user = new User();
    }
 
    @Test
    void testUserId() {
        user.setUserId(1L);
        assertEquals(1L, user.getUserId());
    }
 
    @Test
    void testEmail() {
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }
 
    @Test
    void testUserName() {
        user.setUserName("Test User");
        assertEquals("Test User", user.getUserName());
    }
 
    @Test
    void testMobNumber() {
        user.setMobNumber("1234567890");
        assertEquals("1234567890", user.getMobNumber());
    }
 
    @Test
    void testAddress() {
        user.setAddress("123 Test St");
        assertEquals("123 Test St", user.getAddress());
    }
 
    @Test
    void testPanId() {
        user.setPanId("ABCDE1234F");
        assertEquals("ABCDE1234F", user.getPanId());
    }
 
    @Test
    void testSalary() {
        user.setSalary(50000.0);
        assertEquals(50000.0, user.getSalary());
    }
 
    @Test
    void testHaveLoan() {
        user.sethaveLoan(true);
        assertTrue(user.ishaveLoan());
    }
}