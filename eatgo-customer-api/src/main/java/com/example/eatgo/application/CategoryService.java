package com.example.eatgo.application;

import com.example.eatgo.domain.Category;
import com.example.eatgo.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepositoryd;

    @Autowired
    public CategoryService(CategoryRepository categoryRepositoryd) {
        this.categoryRepositoryd = categoryRepositoryd;
    }

    public List<Category> getCategoris() {
        List<Category> categories = categoryRepositoryd.findAll();

        return categories;
    }

}
