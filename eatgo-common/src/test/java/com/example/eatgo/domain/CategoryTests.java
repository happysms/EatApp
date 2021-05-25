package com.example.eatgo.domain;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTests {

    @Test
    public void creation(){
        Category category = Category.builder().name("korean food").build();

        Assert.assertThat(category.getName(), is("korean food"));
    }


}