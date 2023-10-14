package com.bwagih.bank.management.system.mapper;


import com.bwagih.bank.management.system.commons.BaseMapper;
import com.bwagih.bank.management.system.dto.AccountDto;
import com.bwagih.bank.management.system.entity.Account;
import com.bwagih.bank.management.system.entity.Client;
import com.bwagih.bank.management.system.enums.StatusCode;
import org.mapstruct.*;

import java.util.Objects;


@Mapper(
        builder = @Builder(disableBuilder = true),
        uses = {CashAccountMapper.class},
        imports = {StatusCode.class}
)
public interface AccountMapper extends BaseMapper<Account, AccountDto> {

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    AccountDto mapToDto(Account entity);

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    Account mapToEntity(AccountDto dto);

    @BeforeMapping
    default void getStatusCode(@MappingTarget Account entity, AccountDto dto) {
        entity.setStatus(dto.getStatus().getCode());
        entity.setClient(new Client(dto.getClientId()));
    }

    @AfterMapping
    default void translateStatus(Account entity, @MappingTarget AccountDto dto) {
        dto.setStatus(StatusCode.getStatusCode(entity.getStatus()));
        dto.setClientId(Objects.nonNull(entity.getClient()) ? entity.getClient().getClientId() : null);
    }

}
