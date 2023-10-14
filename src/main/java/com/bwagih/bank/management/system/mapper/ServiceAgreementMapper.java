package com.bwagih.bank.management.system.mapper;


import com.bwagih.bank.management.system.commons.BaseMapper;
import com.bwagih.bank.management.system.dto.ServiceAgreementDto;
import com.bwagih.bank.management.system.entity.ServiceAgreement;
import com.bwagih.bank.management.system.enums.StatusCode;
import org.mapstruct.*;


@Mapper(
        builder = @Builder(disableBuilder = true),
        imports = {StatusCode.class},
        uses = {ClientMapper.class, RoleMapper.class}
)
public interface ServiceAgreementMapper extends BaseMapper<ServiceAgreement, ServiceAgreementDto> {

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    ServiceAgreementDto mapToDto(ServiceAgreement entity);

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    ServiceAgreement mapToEntity(ServiceAgreementDto dto);


    @BeforeMapping
    default void getStatusCode(@MappingTarget ServiceAgreement entity, ServiceAgreementDto dto) {
        entity.setStatus(dto.getStatus().getCode());
    }

    @AfterMapping
    default void translateStatus(ServiceAgreement entity, @MappingTarget ServiceAgreementDto dto) {
        dto.setStatus(StatusCode.getStatusCode(entity.getStatus()));
    }

}
