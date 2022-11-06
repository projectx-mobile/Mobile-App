package com.jungeeks.service.entity;

import com.jungeeks.entity.Category;

public interface CategoryService {

    Category findByTitle(String title);
}
