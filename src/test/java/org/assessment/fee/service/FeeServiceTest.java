package org.assessment.fee.service;

import org.assessment.fee.dto.FeeDto;
import org.assessment.fee.dto.TypesDto;
import org.assessment.fee.entity.Fee;
import org.assessment.fee.enums.FeeFrequencies;
import org.assessment.fee.enums.FeeTypes;
import org.assessment.fee.exception.ApplicationException;
import org.assessment.fee.mapper.FeeMapper;
import org.assessment.fee.repo.FeeRepository;
import org.assessment.fee.validator.FeeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class FeeServiceTest {

    @Mock
    private FeeRepository feeRepository;
    @Mock
    private FeeMapper feeMapper;
    @Mock
    private FeeValidator feeValidator;
    @InjectMocks
    private FeeService feeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Get Empty Fee List")
     void testGetAllFeeEmptyList() {
        List<FeeDto> result = feeService.getAllFee();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Test Get Fee non-empty list")
     void testGetAllFeeNonEmptyList() {
        Fee fee1 = new Fee();
        Fee fee2 = new Fee();
        when(feeRepository.findAll()).thenReturn(Arrays.asList(fee1, fee2));

        // Mocking mapping for feeMapper
        FeeDto feeDto1 = new FeeDto();
        FeeDto feeDto2 = new FeeDto();
        when(feeMapper.mapToDto(fee1)).thenReturn(feeDto1);
        when(feeMapper.mapToDto(fee2)).thenReturn(feeDto2);

        // Execute the function
        List<FeeDto> result = feeService.getAllFee();

        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains(feeDto1));
        assertTrue(result.contains(feeDto2));
    }


    @Test
    @DisplayName("Test valid inputs while adding the fee")
     void testAddFeeValidInput() {
        // Mocking valid input and validation
        FeeDto feeDto = new FeeDto();
        when(feeValidator.validateAddFee(feeDto)).thenReturn(true);
        when(feeService.validateFromDBAndThrowIfExists(feeDto)).thenReturn(false);

      //   Mocking mapping for feeMapper
        Fee feeEntity = new Fee();
        when(feeMapper.mapToEntity(feeDto)).thenReturn(feeEntity);
        when(feeRepository.save(feeEntity)).thenReturn(feeEntity);
        when(feeMapper.mapToDto(feeEntity)).thenReturn(feeDto);
        // Execute the function
        FeeDto result = feeService.addFee(feeDto);

        // Verify the result
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test Check if fee is already exits in Database")
     void testAddFeeDuplicateFeeInDB() {
        // Mocking valid input, validation, and existing fee in the DB
        FeeDto feeDto = new FeeDto();
        when(feeValidator.validateAddFee(feeDto)).thenReturn(true);
        when(feeService.validateFromDBAndThrowIfExists(feeDto)).thenReturn(true);
        String expectedErrorMessage="Fee already exit";
        String expectedErrorCode = "200105";
        // Execute the function
        ApplicationException exception = assertThrows(ApplicationException.class, () -> feeService.addFee(feeDto));
        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedErrorCode, exception.getCode());
    }

    @Test
    @DisplayName("Test update fee valid input And existingFee")
     void testUpdateFeeValidInputAndExistingFee() {
        // Mocking valid input and existing fee in the repository
        String uuid = "valid_uuid";
        FeeDto feeDto = new FeeDto();
        Fee existingFee = new Fee();

        when(feeValidator.validateDates(feeDto)).thenReturn(true);
        when(feeRepository.findByUuid(uuid)).thenReturn(java.util.Optional.of(existingFee));
        when(feeMapper.updateEntity(feeDto, existingFee)).thenReturn(existingFee);
        when(feeRepository.save(existingFee)).thenReturn(existingFee);
        when(feeMapper.mapToDto(existingFee)).thenReturn(feeDto);

        // Execute the function
        FeeDto result = feeService.updateFee(feeDto, uuid);

        // Verify the result
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test update fee with invalid inputs")
     void testUpdateFeeInvalidInput() {
        // Mocking invalid input
        String uuid = "valid_uuid";
        FeeDto feeDto = new FeeDto();

        when(feeValidator.validateDates(feeDto)).thenReturn(false);
        String expectedErrorMessage = "Fee not found";
        String expectedErrorCode = "200106";

        // Execute the function and expect an exception
        ApplicationException exception = assertThrows(ApplicationException.class, () -> feeService.updateFee(feeDto, uuid));
        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedErrorCode, exception.getCode());
    }

    @Test
    @DisplayName("Test update fee not found")
     void testUpdateFeeFeeNotFound() {
        // Mocking valid input but fee not found in the repository
        String uuid = "non_existent_uuid";
        FeeDto feeDto = new FeeDto();

        when(feeValidator.validateDates(feeDto)).thenReturn(true);
        when(feeRepository.findByUuid(uuid)).thenReturn(java.util.Optional.empty());
        String expectedErrorMessage = "Fee not found";
        String expectedErrorCode = "200106";

        // Execute the function and expect an exception
        ApplicationException exception =  assertThrows(ApplicationException.class, ()
                -> feeService.updateFee(feeDto, uuid));
        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedErrorCode, exception.getCode());
    }

    @Test
    @DisplayName("Test get fee by fee type and grade valid input and fee found")
     void testGetFeeByFeeTypeAndGradeValidInputAndFeeFound() {
        // Mocking valid input and fee found in the repository
        String feeType = "tuition";
        String grade = "10";
        FeeDto expectedFeeDto = new FeeDto();
        Fee expectedFeeEntity = new Fee();

        when(feeRepository.findByFeeTypeAndGrade(feeType, grade)).thenReturn(java.util.Optional.of(expectedFeeEntity));
        when(feeMapper.mapToDto(expectedFeeEntity)).thenReturn(expectedFeeDto);

        // Execute the function
        FeeDto result = feeService.getFeeByFeeTypeAndGrade(feeType, grade);

        // Verify the result
        assertNotNull(result);
        assertEquals(expectedFeeDto, result);

    }

    @Test
    @DisplayName("Test get fee by feeType and grade fee not found")
     void testGetFeeByFeeTypeAndGradeFeeNotFound() {
        // Mocking valid input but fee not found in the repository
        String feeType = "tuition";
        String grade = "10";
        String expectedErrorMessage = "Fee not found";
        String expectedErrorCode = "200106";

        when(feeRepository.findByFeeTypeAndGrade(feeType, grade)).thenReturn(java.util.Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> feeService.getFeeByFeeTypeAndGrade(feeType, grade));
        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedErrorCode, exception.getCode());
    }

    @Test
    @DisplayName("Test get fee by grade valid input and fee found")
     void testGetFeeByGradeValidInputAndFeeFound() {
        // Mocking valid input and fees found in the repository
        String grade = "10";
        FeeDto expectedFeeDto1 = new FeeDto();
        FeeDto expectedFeeDto2 = new FeeDto();
        List<Fee> expectedFeeEntities = Arrays.asList(
                new Fee(), new Fee());

        when(feeRepository.findByGrade(grade)).thenReturn(expectedFeeEntities);
        when(feeMapper.mapToDto(expectedFeeEntities.get(0))).thenReturn(expectedFeeDto1);
        when(feeMapper.mapToDto(expectedFeeEntities.get(1))).thenReturn(expectedFeeDto2);

        // Execute the function
        List<FeeDto> result = feeService.getFeeByGrade(grade);

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedFeeDto1, result.get(0));
        assertEquals(expectedFeeDto2, result.get(1));
    }

    @Test
    @DisplayName("Test get fee by grade valid input but no fee found")
     void testGetFeeByGradeValidInputButNoFeeFound() {
        // Mocking valid input but no fees found in the repository
        String grade = "10";
        String expectedErrorMessage = "Record not found";
        String expectedErrorCode = "200108";

        when(feeRepository.findByGrade(grade)).thenReturn(Collections.emptyList());

        // Execute the function and expect an exception with the specified message
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> feeService.getFeeByGrade(grade));
        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedErrorCode, exception.getCode());
    }

    @Test
    @DisplayName("Test get fee by grade invalid input")
     void testGetFeeByGradeInvalidInput() {
        // Mocking invalid input
        String grade = null;
        String expectedErrorMessage = "Record not found";
        String expectedErrorCode = "200108";

        // Execute the function and expect an exception with the specified message
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> feeService.getFeeByGrade(grade));
        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedErrorCode, exception.getCode());
    }

    @Test
    @DisplayName("Test get fee types")
     void testGetFeeTypes() {
        // Execute the function
        var result = feeService.getFeeTypes();

        // Verify the result
        assertNotNull(result);
        assertEquals(FeeTypes.values().length, result.size());

        // Assert each element in the result list
        for (FeeTypes feeType : FeeTypes.values()) {
            TypesDto expectedDto = TypesDto.builder().key(feeType.name()).value(feeType.getDisplayName()).build();
            assertTrue(result.contains(expectedDto));
        }
    }

    @Test
    @DisplayName("Test get fee frequency")
     void testGetFeeFrequency() {
        // Execute the function
        List<TypesDto> result = feeService.getFeeFrequency();

        // Verify the result
        assertNotNull(result);
        assertEquals(FeeFrequencies.values().length, result.size());

        // Assert each element in the result list
        for (FeeFrequencies feeFrequency : FeeFrequencies.values()) {
            TypesDto expectedDto = TypesDto.builder().key(feeFrequency.name()).value(feeFrequency.getDisplayName()).build();
            assertTrue(result.contains(expectedDto));
        }
    }

}
