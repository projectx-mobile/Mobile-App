package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.Category;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.repository.CategoryRepository;
import com.jungeeks.service.entity.CategoryService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.jungeeks.exception.enums.ERROR_CODE.CATEGORY_NOT_FOUND;
import static com.jungeeks.exception.enums.ERROR_CODE.FAMILY_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findByTitle(String title) {
        return categoryRepository.findByTitle(title).orElseThrow(
                () -> new BusinessException(String.format("Category with title %s not found", title), NOT_FOUND));
    }
}
