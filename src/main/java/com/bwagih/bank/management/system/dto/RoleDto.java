package com.bwagih.bank.management.system.dto;

import com.bwagih.bank.management.system.commons.BaseDto;
import com.bwagih.bank.management.system.enums.RoleName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    private Long id;

    private RoleName roleName;

    private List<UsersDto> usersList;

    private List<ServiceAgreementDto> agreementList;


}
