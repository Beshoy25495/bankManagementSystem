package com.bwagih.bank.management.system.commons;

import java.util.Set;

public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {

    D mapToDto(E entity);

    E mapToEntity(D dto);

    Set<D> mapBunchOfEntityToDto(Set<E> bunchOfEntities);

    Set<E> mapBunchOfDtoToEntity(Set<D> bunchOfDtos);

}
