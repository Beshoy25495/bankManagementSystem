package com.bwagih.bank.management.system.mapper;


import com.bwagih.bank.management.system.commons.BaseMapper;
import com.bwagih.bank.management.system.dto.UsersDto;
import com.bwagih.bank.management.system.entity.Users;
import com.bwagih.bank.management.system.enums.StatusCode;
import org.mapstruct.*;


@Mapper(
        builder = @Builder(disableBuilder = true),
        uses = {RoleMapper.class},
        imports = {StatusCode.class}
)
public interface UsersMapper extends BaseMapper<Users, UsersDto> , UserAgreementMapper {

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    UsersDto mapToDto(Users entity);

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    Users mapToEntity(UsersDto dto);


    @BeforeMapping
    default void getStatusCode(@MappingTarget Users entity, UsersDto dto) {
        entity.setStatus(dto.getStatus().getCode());
    }

    @AfterMapping
    default void translateStatus(Users entity, @MappingTarget UsersDto dto) {
        dto.setStatus(StatusCode.getStatusCode(entity.getStatus()));
    }

}
