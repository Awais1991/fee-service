package org.assessment.fee.repo;

import static org.junit.jupiter.api.Assertions.*;

import org.assessment.fee.entity.Fee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
 class FeeRepositoryTest {

    @Autowired
    private FeeRepository feeRepository;


    @BeforeEach
     void setUp() {
        // Insert test data into the database
        Fee fee1 = new Fee(1L,"existing_uuid", "Tuition", BigDecimal.valueOf(100.00), "AED", "g1", "Monthly", LocalDate.now(), LocalDate.now().plusMonths(1));
        Fee fee2 = new Fee(2L,"existing_grade_uuid", "Transport", BigDecimal.valueOf(150.00), "AED", "g1", "Monthly", LocalDate.now(), LocalDate.now().plusMonths(1));
        Fee fee3 = new Fee(3L,"existing_grade_uuid1", "Book", BigDecimal.valueOf(50.00), "AED", "g1", "Monthly", LocalDate.now(), LocalDate.now().plusMonths(1));

        feeRepository.saveAll(List.of(fee1, fee2, fee3));
        }

    @Test
    @DisplayName("Test find by uuid existing fee")
     void testFindByUuidExistingFee() {
        String uuid = "existing_uuid";
        Optional<Fee> result = feeRepository.findByUuid(uuid);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Test find by uuid non existing fee")
     void testFindByUuidNonExistingFee() {
        String uuid = "non_existing_uuid";
        Optional<Fee> result = feeRepository.findByUuid(uuid);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Test exists by feeType and grade existing fee")
     void testExistsByFeeTypeAndGradeExistingFee() {
        String feeType = "Tuition";
        String grade = "g1";
        boolean result = feeRepository.existsByFeeTypeAndGrade(feeType, grade);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test exists by fee type and grade non existing fee")
     void testExistsByFeeTypeAndGradeNonExistingFee() {
        String feeType = "Book";
        String grade = "12";
        boolean result = feeRepository.existsByFeeTypeAndGrade(feeType, grade);
        assertFalse(result);
    }

    @Test
    @DisplayName("Test find by feeType and grade existing fee")
     void testFindByFeeTypeAndGradeExistingFee() {
        String feeType = "Tuition";
        String grade = "g1";
        Optional<Fee> result = feeRepository.findByFeeTypeAndGrade(feeType, grade);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Test find by fee type and grade non existing fee")
     void testFindByFeeTypeAndGradeNonExistingFee() {
        String feeType = "Book";
        String gradeUUID = "non_existing_grade_uuid";
        Optional<Fee> result = feeRepository.findByFeeTypeAndGrade(feeType, gradeUUID);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Test find by grade existing fees")
     void testFindByGradeExistingFees() {
        String grade = "g1";
        List<Fee> result = feeRepository.findByGrade(grade);
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test
    @DisplayName("Test find by grade no fees")
     void testFindByGradeNoFees() {
        String grade = "g2";
        List<Fee> result = feeRepository.findByGrade(grade);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}

