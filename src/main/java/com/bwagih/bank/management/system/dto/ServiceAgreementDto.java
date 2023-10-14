package com.bwagih.bank.management.system.dto;

import com.bwagih.bank.management.system.commons.BaseDto;
import com.bwagih.bank.management.system.enums.StatusCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceAgreementDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    private Long agreementId;

    private String userName;

    private String password;

    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    private StatusCode status;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;


    private ClientDto client;

    private List<RoleDto> roleList;


}
