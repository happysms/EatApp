package com.example.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegionTests {

    @Test
    public void creation(){
        Region region = Region.builder().name("서울").build();

        assertEquals("서울" , region.getName());
    }
}