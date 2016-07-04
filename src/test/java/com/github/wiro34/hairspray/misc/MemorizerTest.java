package com.github.wiro34.hairspray.misc;

import java.util.Random;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

public class MemorizerTest {

    private final Memorizer<Integer> mem = new Memorizer<>(() -> generateRandomNumber());

    private int generateRandomNumber() {
        return new Random().nextInt();
    }

    @Test
    public void testGetFactoryInstance() throws Exception {
        Integer origin = mem.get();
        assertEquals(mem.get(), origin);
        assertEquals(mem.get(), origin);
        assertEquals(mem.get(), origin);
    }
}
