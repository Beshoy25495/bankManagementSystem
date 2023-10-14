package com.bwagih.bank.management.system.dto;

import com.bwagih.bank.management.system.commons.BaseDto;
import com.bwagih.bank.management.system.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    private Long transactionNumber;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Date executionDate;

    private BigDecimal amount;

    private CashAccountDto cashAccount;

}
