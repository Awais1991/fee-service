package org.assessment.fee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
@Entity
@Table(name = "fees")
public class Fee {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "uuid", nullable = false, unique = true)
	private String uuid;
	@Column(name = "fee_type", nullable = false)
	private String feeType;
	@Column(name = "fee_amount", nullable = false)
	private BigDecimal feeAmount;
	@Column(name = "fee_currency", nullable = false)
	private String feeCurrency;
	@Column(name = "grade")
	private String grade;
	@Column(name = "fee_frequency", nullable = false)
	private String feeFrequency;
	@Column(name = "effective_date", nullable = false)
	private LocalDate effectiveDate;
	@Column(name = "expiry_date", nullable = false)
	private LocalDate expiryDate;

}
