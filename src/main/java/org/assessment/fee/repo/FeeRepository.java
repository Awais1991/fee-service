package org.assessment.fee.repo;

import org.assessment.fee.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeeRepository extends JpaRepository<Fee, Long> {

    Optional<Fee> findByUuid(String uuid);

    boolean existsByFeeTypeAndGrade(String feeType, String grade);
    Optional<Fee> findByFeeTypeAndGrade(String feeType, String gradeUUID);
    List<Fee> findByGrade(String gradeUUID);

}
