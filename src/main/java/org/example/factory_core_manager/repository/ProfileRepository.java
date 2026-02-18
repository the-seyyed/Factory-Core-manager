package org.example.factory_core_manager.repository;


import org.example.factory_core_manager.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByWorkerCode(String workerCode);

    List<Profile> findProfileByWorkerCode(String workerCode);
}
