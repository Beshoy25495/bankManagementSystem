package com.bwagih.bank.management.system.mapper;


import com.bwagih.bank.management.system.commons.BaseMapper;
import com.bwagih.bank.management.system.dto.RoleDto;
import com.bwagih.bank.management.system.entity.Role;
import com.bwagih.bank.management.system.enums.RoleName;
import org.mapstruct.*;


@Mapper(
        builder = @Builder(disableBuilder = true),
        uses = {UsersMapper.class , ServiceAgreementMapper.class},
        imports = {RoleName.class}
)
public interface RoleMapper extends BaseMapper<Role, RoleDto> {

}
