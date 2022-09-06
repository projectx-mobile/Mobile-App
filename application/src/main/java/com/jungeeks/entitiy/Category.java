package com.jungeeks.entitiy;

import com.jungeeks.entitiy.enums.CATEGORY_TYPE;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
public class Category {

    @Id
    private Long id;
    private String title;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private CATEGORY_TYPE category_type;
}
