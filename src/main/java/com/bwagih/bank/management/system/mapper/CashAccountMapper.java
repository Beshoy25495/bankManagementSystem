package com.bwagih.bank.management.system.mapper;


import com.bwagih.bank.management.system.commons.BaseMapper;
import com.bwagih.bank.management.system.dto.CashAccountDto;
import com.bwagih.bank.management.system.entity.Account;
import com.bwagih.bank.management.system.entity.CashAccount;
import com.bwagih.bank.management.system.enums.StatusCode;
import org.mapstruct.*;

import java.util.Objects;


@Mapper(
        uses = {AccountMapper.class},
        imports = {StatusCode.class},
        builder = @Builder(disableBuilder = true)
)
public interface CashAccountMapper extends BaseMapper<CashAccount, CashAccountDto> {

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    CashAccountDto mapToDto(CashAccount entity);

    @Mappings(
            @Mapping(target = "status", defaultValue = "P", ignore = true)
    )
    @Override
    CashAccount mapToEntity(CashAccountDto dto);

    @BeforeMapping
    default void getStatusCode(@MappingTarget CashAccount entity, CashAccountDto dto) {
        entity.setStatus(dto.getStatus().getCode());
        entity.setAccount(new Account(dto.getAccountNumber()));

    }

    @AfterMapping
    default void translateStatus(CashAccount entity, @MappingTarget CashAccountDto dto) {
        dto.setStatus(StatusCode.getStatusCode(entity.getStatus()));
        dto.setAccountNumber(Objects.nonNull(entity.getAccount()) ? entity.getAccount().getAccountNumber() : null);
    }

}
