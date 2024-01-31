package org.assessment.fee.service;

import org.assessment.fee.dto.FeeDto;
import org.assessment.fee.dto.TypesDto;
import org.assessment.fee.enums.ErrorCode;
import org.assessment.fee.enums.FeeFrequencies;
import org.assessment.fee.enums.FeeTypes;
import org.assessment.fee.exception.ApplicationException;
import org.assessment.fee.mapper.FeeMapper;
import org.assessment.fee.repo.FeeRepository;
import org.assessment.fee.validator.FeeValidator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeeService {

    private final FeeRepository feeRepository;
    private final FeeMapper feeMapper;
    private final FeeValidator feeValidator;

    public FeeService(FeeRepository feeRepository, FeeMapper feeMapper, FeeValidator feeValidator) {
        this.feeRepository = feeRepository;
        this.feeMapper = feeMapper;
        this.feeValidator = feeValidator;
    }

    public FeeDto addFee(FeeDto dto){
       return Optional.ofNullable(dto).filter(feeValidator::validateAddFee)
                .filter(feeDto -> !validateFromDBAndThrowIfExists(feeDto)).map(feeMapper::mapToEntity).map(feeRepository::save)
               .map(feeMapper::mapToDto).orElseThrow(() -> new ApplicationException(ErrorCode.ADD_FEE_FAILED));

    }

    public FeeDto updateFee(FeeDto feeDto, String uuid){
        return Optional.ofNullable(uuid).filter(s -> feeValidator.validateDates(feeDto))
                .flatMap(s -> feeRepository.findByUuid(s))
                .map(fee -> feeMapper.updateEntity(feeDto, fee))
                .map(fee -> feeRepository.save(fee))
                .map(feeMapper::mapToDto)
                .orElseThrow(() -> new ApplicationException(ErrorCode.FEE_NOT_FOUND));
    }

    public FeeDto getFeeByUuid(String feeUUID){
        return Optional.ofNullable(feeUUID).flatMap(s -> feeRepository.findByUuid(feeUUID)).map(feeMapper::mapToDto)
                .orElseThrow(() -> new ApplicationException(ErrorCode.FEE_NOT_FOUND));
    }

    public List<FeeDto> getAllFee(){
        return feeRepository.findAll().stream().map(feeMapper::mapToDto).collect(Collectors.toList());
    }

    public FeeDto getFeeByFeeTypeAndGrade(String feeType, String grade){
        return feeRepository.findByFeeTypeAndGrade(feeType, grade).map(feeMapper::mapToDto)
                .orElseThrow(() -> new ApplicationException(ErrorCode.FEE_NOT_FOUND));
    }

    public List<FeeDto> getFeeByGrade(String grade){
        List<FeeDto> feeDtos =  feeRepository.findByGrade(grade).stream().map(feeMapper::mapToDto).collect(Collectors.toList());
        if (null==feeDtos || feeDtos.isEmpty()){
            throw new ApplicationException(ErrorCode.NOT_FOUND);
        }
        return feeDtos;
    }

    public List<TypesDto> getFeeTypes(){
        return Arrays.stream(FeeTypes.values()).map(feeTypes -> {
           return TypesDto.builder().key(feeTypes.name()).value(feeTypes.getDisplayName()).build();
        }).collect(Collectors.toList());
    }

    public List<TypesDto> getFeeFrequency(){
        return Arrays.stream(FeeFrequencies.values()).map(feeTypes -> {
            return TypesDto.builder().key(feeTypes.name()).value(feeTypes.getDisplayName()).build();
        }).collect(Collectors.toList());
    }


    public boolean validateFromDBAndThrowIfExists(FeeDto feeDto){
        boolean result;
            result =  feeRepository.existsByFeeTypeAndGrade(feeDto.getFeeType(), feeDto.getFeeGrade());
        if (result){
            throw new ApplicationException(ErrorCode.FEE_EXITS);
        }
        return result;
    }
}
