package com.lazulite.pi.repository;
import com.lazulite.pi.domain.FormComponentValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FormComponentValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormComponentValuesRepository extends JpaRepository<FormComponentValues, Long> {

}
