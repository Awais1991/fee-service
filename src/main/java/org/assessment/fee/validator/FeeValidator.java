package org.assessment.fee.validator;


import org.assessment.fee.dto.FeeDto;
import org.assessment.fee.enums.ErrorCode;
import org.assessment.fee.enums.FeeFrequencies;
import org.assessment.fee.enums.FeeTypes;
import org.assessment.fee.exception.ApplicationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class FeeValidator {

    public boolean validateAddFee(FeeDto feeDto){

        Optional.ofNullable(feeDto)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_VALUE, "request body is required"));

        Optional.ofNullable(feeDto.getFeeType())
                .filter(type -> StringUtils.hasLength(type)).filter(FeeTypes::isValidFeeType)
                .orElseThrow(() -> new ApplicationException(ErrorCode.VALUE_REQ.getCode(),
                        String.format(ErrorCode.VALUE_REQ.getMessage(), "fee_type")));

        Optional.ofNullable(feeDto.getFeeFrequency())
                .filter(freq -> StringUtils.hasLength(freq)).filter(FeeFrequencies::isValidFrequency)
                .orElseThrow(() -> new ApplicationException(ErrorCode.VALUE_REQ.getCode(),
                        String.format(ErrorCode.VALUE_REQ.getMessage(), "fee_frequency")));

        Optional.ofNullable(feeDto.getFeeGrade())
                .filter(grad -> StringUtils.hasLength(grad))
                .orElseThrow(() -> new ApplicationException(ErrorCode.VALUE_REQ.getCode(),
                        String.format(ErrorCode.VALUE_REQ.getMessage(), "fee_grade")));

        Optional.ofNullable(feeDto.getFeeAmount())
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) >= 1)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_FIELD.getCode(),
                        "fee_amount is null or invalid value"));

        Optional.ofNullable(feeDto.getEffectiveDate())
                .orElseThrow(() -> new ApplicationException(ErrorCode.VALUE_REQ.getCode(),
                        String.format(ErrorCode.VALUE_REQ.getMessage(), "fee_effective_date")));

        Optional.ofNullable(feeDto.getExpiryDate())
                .orElseThrow(() -> new ApplicationException(ErrorCode.VALUE_REQ.getCode(),
                        String.format(ErrorCode.VALUE_REQ.getMessage(), "fee_expiry_date")));

        return validateDates(feeDto);
   }

    public boolean validateDates(FeeDto feeDto) {
        if (null!=feeDto.getEffectiveDate() && null!=feeDto.getExpiryDate()  &&
                feeDto.getEffectiveDate().isAfter(feeDto.getExpiryDate())){
            throw new ApplicationException(ErrorCode.INVALID_DATES);
        }
        return true;
    }
}
