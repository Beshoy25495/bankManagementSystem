package com.bwagih.bank.management.system.mapper;


import com.bwagih.bank.management.system.commons.BaseMapper;
import com.bwagih.bank.management.system.dto.ClientDto;
import com.bwagih.bank.management.system.entity.Client;
import com.bwagih.bank.management.system.enums.StatusCode;
import org.mapstruct.*;


@Mapper(
        builder = @Builder(disableBuilder = true),
        uses = {AccountMapper.class},
        imports = {StatusCode.class}
)
public interface ClientMapper extends BaseMapper<Client, ClientDto> {

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    ClientDto mapToDto(Client entity);

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    Client mapToEntity(ClientDto dto);

    @BeforeMapping
    default void getStatusCode(@MappingTarget Client entity, ClientDto dto) {
        entity.setStatus(dto.getStatus().getCode());
    }

    @AfterMapping
    default void translateStatus(Client entity, @MappingTarget ClientDto dto) {
        dto.setStatus(StatusCode.getStatusCode(entity.getStatus()));
    }

}
