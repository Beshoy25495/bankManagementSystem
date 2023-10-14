package com.bwagih.bank.management.system.mapper;


import com.bwagih.bank.management.system.commons.BaseMapper;
import com.bwagih.bank.management.system.dto.TransactionDto;
import com.bwagih.bank.management.system.entity.Transaction;
import com.bwagih.bank.management.system.enums.TransactionType;
import org.mapstruct.*;


@Mapper(
        builder = @Builder(disableBuilder = true),
        uses = {CashAccountMapper.class},
        imports = {TransactionType.class}
)
public interface TransactionMapper extends BaseMapper<Transaction, TransactionDto> {

    @Mappings(
            @Mapping(target = "type", defaultValue = "P", ignore = true)
    )
    @Override
    TransactionDto mapToDto(Transaction entity);

    @Mappings(
            @Mapping(target = "type", defaultValue = "P", ignore = true)
    )
    @Override
    Transaction mapToEntity(TransactionDto dto);


    @BeforeMapping
    default void getStatusCode(@MappingTarget Transaction entity, TransactionDto dto) {
        entity.setType(dto.getType().getCode());
    }

    @AfterMapping
    default void translateStatus(Transaction entity, @MappingTarget TransactionDto dto) {
        dto.setType(TransactionType.getTypeId(entity.getType()));
    }

}
