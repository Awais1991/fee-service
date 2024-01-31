package org.assessment.fee.controller;

import org.assessment.fee.commen.ApiResponse;
import org.assessment.fee.dto.FeeDto;
import org.assessment.fee.dto.TypesDto;
import org.assessment.fee.service.FeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<ApiResponse<FeeDto>> getFeeByUuid(@PathVariable String uuid){
        return ResponseEntity.ok(new ApiResponse<>(feeService.getFeeByUuid(uuid), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FeeDto>>> getFee(){
        return ResponseEntity.ok(new ApiResponse<>(feeService.getAllFee(), null));
    }

    @GetMapping(path = "/{fee-type}/grade/{grade}")
    public ResponseEntity<ApiResponse<FeeDto>> getFeeByFeeTypeAndGrade(@PathVariable("fee-type") String feeType,
                                                                       @PathVariable("grade") String grade){
        return ResponseEntity.ok(new ApiResponse<>(feeService.getFeeByFeeTypeAndGrade(feeType, grade), null));
    }

    @GetMapping(path = "/grades/{grade}")
    public ResponseEntity<ApiResponse<List<FeeDto>>> getFeeByGrade(@PathVariable("grade") String grade){
        return ResponseEntity.ok(new ApiResponse<>(feeService.getFeeByGrade(grade), null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FeeDto>> addFee(@RequestBody FeeDto feeDto){
        return ResponseEntity.ok(new ApiResponse<>(feeService.addFee(feeDto), null));
    }
    @PutMapping(path = "/{uuid}")
    public ResponseEntity<ApiResponse<FeeDto>> updateFee(@PathVariable String uuid, @RequestBody FeeDto feeDto){
        return ResponseEntity.ok(new ApiResponse<>(feeService.updateFee(feeDto, uuid), null));
    }
    @GetMapping(path = "/fee-types")
    public ResponseEntity<ApiResponse<List<TypesDto>>> getFeeTypes(){
        return ResponseEntity.ok(new ApiResponse<>(feeService.getFeeTypes(), null));
    }
    @GetMapping(path = "/fee-frequencies")
    public ResponseEntity<ApiResponse<List<TypesDto>>> getFeeFrequencies(){
        return ResponseEntity.ok(new ApiResponse<>(feeService.getFeeFrequency(), null));
    }
}
