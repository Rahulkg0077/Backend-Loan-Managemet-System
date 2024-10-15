package com.hackathone.LMS.ErrorMessages;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import static org.junit.jupiter.api.Assertions.*;
 
public class ErrorResponseTest {
    private ErrorResponse errorResponse;
 
    @BeforeEach
    void setUp() {
        errorResponse = new ErrorResponse("Not Found", 404);
    }
 
    @Test
    void testGetMessage() {
        assertEquals("Not Found", errorResponse.getMessage());
    }
 
    @Test
    void testSetMessage() {
        errorResponse.setMessage("Bad Request");
        assertEquals("Bad Request", errorResponse.getMessage());
    }
 
    @Test
    void testGetErrorCode() {
        assertEquals(404, errorResponse.getCode());
    }
 
    @Test
    void testSetErrorCode() {
        errorResponse.setCode(400);
        assertEquals(400, errorResponse.getCode());
    }
}