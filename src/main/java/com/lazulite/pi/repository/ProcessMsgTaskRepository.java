package com.lazulite.pi.repository;
import com.lazulite.pi.domain.ProcessMsgTask;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProcessMsgTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessMsgTaskRepository extends JpaRepository<ProcessMsgTask, Long> {

}
