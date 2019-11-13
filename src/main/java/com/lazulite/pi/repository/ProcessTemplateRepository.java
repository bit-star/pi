package com.lazulite.pi.repository;
import com.lazulite.pi.domain.ProcessTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProcessTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessTemplateRepository extends JpaRepository<ProcessTemplate, Long> {

}
