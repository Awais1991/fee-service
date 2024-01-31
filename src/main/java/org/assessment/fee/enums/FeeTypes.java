package org.assessment.fee.enums;

import org.assessment.fee.exception.ApplicationException;

import java.util.HashMap;
import java.util.Map;

public enum FeeTypes {
    ADMISSION_FEE("Admission Fee"),
    TRANSPORTATION_FEE("Transportation Fee"),
    MEAL_FEE("Meal Fee"),
    UNIFORM_FEE("Uniform Fee"),
    TECHNOLOGY_FEE("Technology Fee"),
    TEST_FEE("Test Fee"),
    TUITION_FEE("Tuition Fee"),
    EXTRA_CURRICULAR_FEE("Extracurricular Fee");

    private final String displayName;
    private static final Map<String, FeeTypes> feeTypeMap = new HashMap<>();

    static {
        for (FeeTypes feeTypes : values()) {
            feeTypeMap.put(feeTypes.name(), feeTypes);
        }
    }

    FeeTypes(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    private static String getAllFeeTypes() {
        return String.join(", ", feeTypeMap.keySet());
    }




    public static boolean isValidFeeType(String input) {
        if (feeTypeMap.containsKey(input)) {
            return true;
        }
        throw new ApplicationException(ErrorCode.INVALID_VALUE.getCode(), "Invalid fee type. Valid values are: " + getAllFeeTypes());
    }
    public static FeeTypes getByName(String name){
        return feeTypeMap.get(name);
    }
    public static String getDescByName(String name){
        return feeTypeMap.get(name).getDisplayName();
    }
}

