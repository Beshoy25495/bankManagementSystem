package com.bwagih.bank.management.system.dto;

import com.bwagih.bank.management.system.commons.BaseDto;
import com.bwagih.bank.management.system.enums.StatusCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    private Long clientId;

    @Enumerated(EnumType.STRING)
    private StatusCode status;

    private String firstName;

    private String lastName;
    private String email;

    private Set<AccountDto> accounts = new HashSet<>();

}
