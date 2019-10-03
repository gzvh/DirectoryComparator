package pl.przemek.java.advanced;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheatSheet {

    @BeforeEach
    void setUp() {
        System.out.println("before");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after");
    }

    @Test
    void testExample() {
        System.out.println("test1");
        Assertions.assertEquals(1, 1);
    }

    @Test
    void testExample2() {
        System.out.println("test2");
        Assertions.assertEquals(1, 1);
    }

}
