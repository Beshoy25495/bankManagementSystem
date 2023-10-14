package com.bwagih.bank.management.system.dto;

import com.bwagih.bank.management.system.commons.BaseDto;
import com.bwagih.bank.management.system.enums.StatusCode;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    private Long accountNumber;

    private String currency;

    @Enumerated(EnumType.STRING)
    private StatusCode status;

    private Long clientId;

    private BigDecimal totalBalance;

    private BigDecimal totalBlockAmount;

    //    @JsonIgnoreProperties(value = {"account"}, ignoreUnknown = true)
    private Set<CashAccountDto> cashAccounts = new HashSet<>();


}
