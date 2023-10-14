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
public class UsersDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String password;

    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    private StatusCode status;

    private String firstName;

    private String lastName;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    private List<RoleDto> roleList;


}
