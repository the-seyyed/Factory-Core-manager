package org.example.factory_core_manager.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.factory_core_manager.entity.Profile;
import org.example.factory_core_manager.exception.MultipleProfileByWorkerCodeException;
import org.example.factory_core_manager.exception.ProfileNotFoundByWorkerCodeException;
import org.example.factory_core_manager.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {


    private final ProfileRepository profileRepository;

    private final EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;


    @Autowired
    public ProfileService(ProfileRepository profileRepository, EntityManager entityManager ) {
        this.profileRepository = profileRepository;
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public boolean isDuplicatedWorkerCode(String workerCode) {
        return profileRepository.existsByWorkerCode(workerCode);
    }

    public Profile getProfileByWorkerCode(String workerCode) {
        List<Profile> profile = profileRepository.findProfileByWorkerCode(workerCode);
        if (profile.isEmpty()) {
            throw new ProfileNotFoundByWorkerCodeException("No profile found for worker code " + workerCode);
        }
        else if (profile.size() > 1) {
            throw new MultipleProfileByWorkerCodeException("Multiple profiles found for worker code " + workerCode);
        }
        return profile.get(0);
    }




}
