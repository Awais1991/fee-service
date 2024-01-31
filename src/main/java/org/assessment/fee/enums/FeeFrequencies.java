package org.assessment.fee.enums;

import org.assessment.fee.exception.ApplicationException;

import java.util.HashMap;
import java.util.Map;

public enum FeeFrequencies {
    ONE_TIME_FEE("One-Time Fee"),
    MONTHLY_FEE("Monthly Fee"),
    QUARTERLY_FEE("Quarterly Fee"),
    HALF_YEARLY_FEE("Half-Yearly Fee"),
    YEARLY_FEE("Yearly Fee");

    private final String displayName;
    private static final Map<String, FeeFrequencies> frequencyMap = new HashMap<>();

    static {
        for (FeeFrequencies frequency : values()) {
            frequencyMap.put(frequency.name(), frequency);
        }
    }

    FeeFrequencies(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    private static String getAllFrequencies() {
        return String.join(", ", frequencyMap.keySet());
    }

    public static boolean isValidFrequency(String input) {
        if (frequencyMap.containsKey(input)) {
            return true;
        }
        throw new ApplicationException(ErrorCode.INVALID_VALUE.getCode(), "Invalid fee frequency. Valid values are: " + getAllFrequencies());
    }
}