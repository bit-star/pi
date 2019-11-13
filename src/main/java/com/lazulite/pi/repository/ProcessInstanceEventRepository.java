package com.lazulite.pi.repository;
import com.lazulite.pi.domain.ProcessInstanceEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProcessInstanceEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessInstanceEventRepository extends JpaRepository<ProcessInstanceEvent, Long> {

}
