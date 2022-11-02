package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.Family;
import com.jungeeks.exception.FamilyNotFoundException;
import com.jungeeks.repository.AccountsFamilyRepository;
import com.jungeeks.service.entity.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private AccountsFamilyRepository accountsFamilyRepository;

    @Override
    public Family getFamilyById(String id) {
        return accountsFamilyRepository.findById(id)
                .orElseThrow(() -> new FamilyNotFoundException(String.format("Family with id %s not found", id)));
    }

    @Override
    public Family save(Family family) {
        return accountsFamilyRepository.save(family);
    }
}
