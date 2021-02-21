package com.luoyang;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UuidTest {
    @Test
    public void uuid(){
        String uid = UUID.randomUUID().toString().substring(30);
        System.out.println(uid);
    }
}
