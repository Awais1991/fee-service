package org.assessment.fee.mapper;

import org.assessment.fee.dto.FeeDto;
import org.assessment.fee.entity.Fee;
import org.assessment.fee.enums.FeeTypes;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class FeeMapper implements Mapper<FeeDto, Fee>{
    @Override
    public Fee mapToEntity(FeeDto feeDto) {
       return Fee.builder()
        		.uuid(UUID.randomUUID().toString())
        		.feeType(feeDto.getFeeType())
        		.feeAmount(feeDto.getFeeAmount())
        		.grade(feeDto.getFeeGrade())
        		.effectiveDate(feeDto.getEffectiveDate())
        		.expiryDate(feeDto.getExpiryDate())
                .feeFrequency(feeDto.getFeeFrequency())
                .feeCurrency(feeDto.getFeeCurrency())
        		.build();

    }

    @Override
    public Fee updateEntity(FeeDto feeDto, Fee fee) {
        if(null!= feeDto.getFeeAmount())fee.setFeeAmount(feeDto.getFeeAmount());
        if(StringUtils.hasText(feeDto.getFeeFrequency()))fee.setFeeFrequency(feeDto.getFeeFrequency());
        if(StringUtils.hasText(feeDto.getFeeCurrency()))fee.setFeeFrequency(feeDto.getFeeCurrency());
        if(null!=feeDto.getEffectiveDate())fee.setEffectiveDate(feeDto.getEffectiveDate());
        if(null!=feeDto.getExpiryDate())fee.setExpiryDate(feeDto.getExpiryDate());
        return fee;
    }

    @Override
    public FeeDto mapToDto(Fee fee) {
        return FeeDto.builder()
        		.feeUUID(fee.getUuid())
        		.feeType(fee.getFeeType())
        		.feeTypeDesc(FeeTypes.getDescByName(fee.getFeeType()))
        		.feeGrade(fee.getGrade())
        		.feeAmount(fee.getFeeAmount())
        		.feeFrequency(fee.getFeeFrequency())
        		.effectiveDate(fee.getEffectiveDate())
        		.expiryDate(fee.getExpiryDate())
                .feeCurrency(fee.getFeeCurrency())
        		.build();
    }
}
