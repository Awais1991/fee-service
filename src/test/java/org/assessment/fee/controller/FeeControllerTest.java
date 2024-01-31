package org.assessment.fee.controller;

import org.assessment.fee.commen.ApiResponse;
import org.assessment.fee.dto.FeeDto;
import org.assessment.fee.dto.TypesDto;
import org.assessment.fee.enums.ErrorCode;
import org.assessment.fee.exception.ApplicationException;
import org.assessment.fee.service.FeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class FeeControllerTest {

    @Mock
    private FeeService feeService;

    @InjectMocks
    private FeeController feeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test get fee by uuid success")
    public void testGetFeeByUuidSuccess() {
        String validUuid = "valid_uuid";
        FeeDto expectedFeeDto = createSampleFeeDto();
        when(feeService.getFeeByUuid(validUuid)).thenReturn(expectedFeeDto);

        ResponseEntity<ApiResponse<FeeDto>> responseEntity = feeController.getFeeByUuid(validUuid);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedFeeDto, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
    }

    @Test
    @DisplayName("Test get fee by uuid not found")
    public void testGetFeeByUuidNotFound() {
        String invalidUuid = "invalid_uuid";
        when(feeService.getFeeByUuid(invalidUuid)).thenThrow(new ApplicationException(ErrorCode.FEE_NOT_FOUND));

       ApplicationException exception =  assertThrows(ApplicationException.class, () ->  feeController.getFeeByUuid(invalidUuid));
       assertEquals(exception.getMessage(), "Fee not found");
       assertEquals(exception.getCode(), "200106");

    }

    @Test
    @DisplayName("Test get fee success")
    public void testGetFeeSuccess() {
        List<FeeDto> expectedFeeList = Arrays.asList(createSampleFeeDto(), createSampleFeeDto());
        when(feeService.getAllFee()).thenReturn(expectedFeeList);

        ResponseEntity<ApiResponse<List<FeeDto>>> responseEntity = feeController.getFee();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedFeeList, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
    }

    @Test
    @DisplayName("Test get fee empty list")
    public void testGetFeeEmptyList() {
        List<FeeDto> emptyFeeList = Arrays.asList();
        when(feeService.getAllFee()).thenReturn(emptyFeeList);

        ResponseEntity<ApiResponse<List<FeeDto>>> responseEntity = feeController.getFee();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(emptyFeeList, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
    }

    @Test
    @DisplayName("Test get fee by fee type and grade success")
    public void testGetFeeByFeeTypeAndGradeSuccess() {
        // Arrange
        String validFeeType = "tuition_fee";
        String validGrade = "10";
        FeeDto expectedFeeDto = createSampleFeeDto();

        when(feeService.getFeeByFeeTypeAndGrade(validFeeType, validGrade)).thenReturn(expectedFeeDto);

        // Act
        ResponseEntity<ApiResponse<FeeDto>> responseEntity = feeController.getFeeByFeeTypeAndGrade(validFeeType, validGrade);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedFeeDto, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
        // Add more assertions based on your specific requirements
    }

    @Test
    @DisplayName("Test get fee by fee type and grade not found")
    public void testGetFeeByFeeTypeAndGradeNotFound() {
        // Arrange
        String invalidFeeType = "invalid_fee_type";
        String invalidGrade = "invalid_grade";

        when(feeService.getFeeByFeeTypeAndGrade(invalidFeeType, invalidGrade))
                .thenThrow(new ApplicationException(ErrorCode.FEE_NOT_FOUND));


        ApplicationException exception =  assertThrows(ApplicationException.class, () ->  feeController.getFeeByFeeTypeAndGrade(invalidFeeType, invalidGrade));
        assertEquals(exception.getMessage(), "Fee not found");
        assertEquals(exception.getCode(), "200106");
    }


    @Test
    @DisplayName("test get fee by grade success")
    public void testGetFeeByGradeSuccess() {
        // Arrange
        String validGrade = "10";
        List<FeeDto> expectedFeeList = Arrays.asList(createSampleFeeDto(), createSampleFeeDto());

        when(feeService.getFeeByGrade(validGrade)).thenReturn(expectedFeeList);

        // Act
        ResponseEntity<ApiResponse<List<FeeDto>>> responseEntity = feeController.getFeeByGrade(validGrade);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedFeeList, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
    }

    @Test
    @DisplayName("Test get fee by grade empty list")
    public void testGetFeeByGradeEmptyList() {
        String validGrade = "10";
        List<FeeDto> emptyFeeList = Arrays.asList();

        when(feeService.getFeeByGrade(validGrade)).thenReturn(emptyFeeList);

        ResponseEntity<ApiResponse<List<FeeDto>>> responseEntity = feeController.getFeeByGrade(validGrade);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(emptyFeeList, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
    }

    @Test
    @DisplayName("Test add fee success")
    public void testAddFeeSuccess() {
        // Arrange
        FeeDto requestDto = createSampleFeeDto();
        FeeDto expectedFeeDto = createSampleFeeDto();

        when(feeService.addFee(requestDto)).thenReturn(expectedFeeDto);

        // Act
        ResponseEntity<ApiResponse<FeeDto>> responseEntity = feeController.addFee(requestDto);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedFeeDto, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
    }

    @Test
    @DisplayName("Test add fee invalid request")
    public void testAddFeeInvalidRequest() {
        // Arrange
        FeeDto invalidRequestDto = new FeeDto();  // Set up an invalid request, e.g., missing required fields

        when(feeService.addFee(invalidRequestDto))
                .thenThrow(new ApplicationException(ErrorCode.INVALID_VALUE, "request body is required"));

        ApplicationException exception =  assertThrows(ApplicationException.class, () ->  feeController.addFee(invalidRequestDto));
        assertEquals(exception.getMessage(), "request body is required");
        assertEquals(exception.getCode(), "200101");

    }

    @Test
    @DisplayName("Test add fee failure")
    public void testAddFeeFailure() {
        // Arrange
        FeeDto requestDto = createSampleFeeDto();

        when(feeService.addFee(requestDto)).thenThrow(new ApplicationException(ErrorCode.ADD_FEE_FAILED));

        ApplicationException exception =  assertThrows(ApplicationException.class, () ->  feeController.addFee(requestDto));
        assertEquals(exception.getMessage(), "Failed to add fee");
        assertEquals(exception.getCode(), "200104");
    }

    @Test
    @DisplayName("Test update fee success")
    public void testUpdateFeeSuccess() {
        // Arrange
        String validUuid = "valid_uuid";
        FeeDto requestDto = createSampleFeeDto();
        FeeDto expectedFeeDto = createSampleFeeDto();

        when(feeService.updateFee(requestDto, validUuid)).thenReturn(expectedFeeDto);

        ResponseEntity<ApiResponse<FeeDto>> responseEntity = feeController.updateFee(validUuid, requestDto);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedFeeDto, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
    }

    @Test
    @DisplayName("Test update uee invalid Request")
    public void testUpdateFeeInvalidRequest() {
        // Arrange
        String validUuid = "valid_uuid";
        FeeDto invalidRequestDto = new FeeDto();
        when(feeService.updateFee(invalidRequestDto, validUuid)).thenThrow(new ApplicationException(ErrorCode.INVALID_DATES));
        ApplicationException exception =  assertThrows(ApplicationException.class, () ->  feeController.updateFee(validUuid, invalidRequestDto));
        assertEquals(exception.getMessage(), "Date range is invalid");
        assertEquals(exception.getCode(), "200107");

    }

    @Test
    @DisplayName("Test get fee types success")
    public void testGetFeeTypesSuccess() {
        List<TypesDto> expectedFeeTypes = Arrays.asList(createSampleTypesDto(), createSampleTypesDto());
        when(feeService.getFeeTypes()).thenReturn(expectedFeeTypes);
        ResponseEntity<ApiResponse<List<TypesDto>>> responseEntity = feeController.getFeeTypes();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedFeeTypes, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
    }

    @Test
    @DisplayName("Test get fee types empty list")
    public void testGetFeeTypesEmptyList() {
        List<TypesDto> emptyFeeTypes = Arrays.asList();
        when(feeService.getFeeTypes()).thenReturn(emptyFeeTypes);

        ResponseEntity<ApiResponse<List<TypesDto>>> responseEntity = feeController.getFeeTypes();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(emptyFeeTypes, responseEntity.getBody().getData());
        assertNull(responseEntity.getBody().getError());
    }


    private FeeDto createSampleFeeDto() {
        FeeDto feeDto = new FeeDto();
        return feeDto;
    }

    private TypesDto createSampleTypesDto() {
        return new TypesDto("", "");
    }
}
