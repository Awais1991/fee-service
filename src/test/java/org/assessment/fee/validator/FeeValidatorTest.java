package org.assessment.fee.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.assessment.fee.dto.FeeDto;
import org.assessment.fee.exception.ApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

class FeeValidatorTest {

    private final FeeValidator feeValidator = new FeeValidator();

    @Test
    @DisplayName("Test validate add fee validFeeDto")
     void testValidateAddFeeValidFeeDto() {
        FeeDto validFeeDto = createValidFeeDto();
        validFeeDto.setFeeType("TUITION_FEE");
        validFeeDto.setFeeFrequency("MONTHLY_FEE");

        assertTrue(feeValidator.validateAddFee(validFeeDto));
    }

    @Test
    @DisplayName("Test validate add fee null FeeDto")
    void testValidateAddFeeNullFeeDto() {
        assertThrows(ApplicationException.class, () -> feeValidator.validateAddFee(null));
    }

    @Test
    @DisplayName("Test validate add fee invalid feeType")
     void testValidateAddFeeInvalidFeeType() {
        FeeDto invalidFeeDto = createValidFeeDto();
        invalidFeeDto.setFeeType("InvalidType");
        assertThrows(ApplicationException.class, () -> feeValidator.validateAddFee(invalidFeeDto));
    }

    @Test
    @DisplayName("Test validate add fee invalid fee frequency")
     void testValidateAddFeeInvalidFeeFrequency() {
        FeeDto invalidFeeDto = createValidFeeDto();
        invalidFeeDto.setFeeFrequency("InvalidFrequency");

        assertThrows(ApplicationException.class, () -> feeValidator.validateAddFee(invalidFeeDto));
    }

    @Test
    @DisplayName("Test validate add fee missing fee grade")
     void testValidateAddFeeMissingFeeGrade() {
        FeeDto invalidFeeDto = createValidFeeDto();
        invalidFeeDto.setFeeGrade(null);

        assertThrows(ApplicationException.class, () -> feeValidator.validateAddFee(invalidFeeDto));
    }

    @Test
    @DisplayName("Test validate add fee invalid fee amount")
     void testValidateAddFeeInvalidFeeAmount() {
        FeeDto invalidFeeDto = createValidFeeDto();
        invalidFeeDto.setFeeAmount(BigDecimal.valueOf(-50.00));

        assertThrows(ApplicationException.class, () -> feeValidator.validateAddFee(invalidFeeDto));
    }

    @Test
    @DisplayName("Test validate add fee missing effective date")
     void testValidateAddFeeMissingEffectiveDate() {

        FeeDto invalidFeeDto = createValidFeeDto();
        invalidFeeDto.setEffectiveDate(null);

        assertThrows(ApplicationException.class, () -> feeValidator.validateAddFee(invalidFeeDto));
    }

    @Test
    @DisplayName("Test validate add fee missing expiryDate")
     void testValidateAddFeeMissingExpiryDate() {

        FeeDto invalidFeeDto = createValidFeeDto();
        invalidFeeDto.setExpiryDate(null);

        assertThrows(ApplicationException.class, () -> feeValidator.validateAddFee(invalidFeeDto));
    }

    @Test
    @DisplayName("Test validate dates valid dates")
     void testValidateDatesValidDates() {

        FeeDto validFeeDto = createValidFeeDto();

        assertTrue(feeValidator.validateDates(validFeeDto));
    }

    @Test
    @DisplayName("Test validate dates invalid dates")
     void testValidateDatesInvalidDates() {

        FeeDto invalidDatesFeeDto = createValidFeeDto();
        invalidDatesFeeDto.setEffectiveDate(LocalDate.now().plusDays(1));
        invalidDatesFeeDto.setExpiryDate(LocalDate.now());

        assertThrows(ApplicationException.class, () -> feeValidator.validateDates(invalidDatesFeeDto));
    }

    private FeeDto createValidFeeDto() {
        return new FeeDto(
                "valid_uuid",
                "Tuition",
                "Description",
                "10",
                BigDecimal.valueOf(100.00),
                "AED",
                "Monthly",
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );
    }
}