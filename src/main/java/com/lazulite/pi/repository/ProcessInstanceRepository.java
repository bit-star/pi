package com.lazulite.pi.repository;
import com.lazulite.pi.domain.ProcessInstance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProcessInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessInstanceRepository extends JpaRepository<ProcessInstance, Long> {

}
