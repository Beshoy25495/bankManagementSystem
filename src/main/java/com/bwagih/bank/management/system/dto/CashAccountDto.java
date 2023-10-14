package com.bwagih.bank.management.system.dto;

import com.bwagih.bank.management.system.commons.BaseDto;
import com.bwagih.bank.management.system.enums.StatusCode;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashAccountDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    private Long cashAccountId;

    @Enumerated(EnumType.STRING)
    private StatusCode status;

    private String currency;

    private BigDecimal balance;

    private BigDecimal blockAmount;

    private Long accountNumber;

}
