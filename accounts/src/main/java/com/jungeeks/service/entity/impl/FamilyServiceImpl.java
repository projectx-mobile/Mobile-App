package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.Family;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.repository.AccountsFamilyRepository;
import com.jungeeks.service.entity.FamilyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.jungeeks.exception.enums.ERROR_CODE.FAMILY_NOT_FOUND;

@Service("accounts-familyServiceImpl")
@Slf4j
public class FamilyServiceImpl implements FamilyService {

    private final AccountsFamilyRepository accountsFamilyRepository;

    @Autowired
    public FamilyServiceImpl(AccountsFamilyRepository accountsFamilyRepository) {
        this.accountsFamilyRepository = accountsFamilyRepository;
    }

    @Override
    public Family getFamilyById(String id) {
        log.debug("Get family by id {}", id);

        return accountsFamilyRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format("Family with id %s not found", id), FAMILY_NOT_FOUND));
    }

    @Override
    public Family save(Family family) {
        log.debug("Save family with id {}", family.getId());

        return accountsFamilyRepository.save(family);
    }
}
