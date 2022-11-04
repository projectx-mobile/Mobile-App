package com.jungeeks.service.entity;

import com.jungeeks.entity.Family;

public interface FamilyService {

    Family getFamilyById(String id);

    Family save(Family family);
}
