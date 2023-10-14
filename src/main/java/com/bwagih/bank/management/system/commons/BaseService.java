package com.bwagih.bank.management.system.commons;


import com.bwagih.bank.management.system.repository.BaseRepository;
import com.bwagih.bank.management.system.utils.MessageUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.MappedSuperclass;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@MappedSuperclass
@Log4j2
public class BaseService<T extends BaseEntity, ID extends Number> {

    @Autowired
    private BaseRepository<T, ID> baseRepository;

    @Autowired
    private MessageUtils messageUtils;

    public T findById(ID id) {

        Optional<T> entity = baseRepository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throwFailureException(id);
        }

        return null;
    }

    public Optional<T> getById(ID id) {
        return Optional.of(baseRepository.getReferenceById(id));
    }

    public List<T> findAll() {
        return baseRepository.findAll();
    }

    public List<T> findAllByIds(Iterable<ID> ids) {
        return baseRepository.findAllById(ids);
    }

    public T insert(T entity) {
        return baseRepository.save(entity);
    }

    public List<T> insertAll(List<T> entity) {
        return baseRepository.saveAll(entity);
    }

    public T update(T entity) {
        return baseRepository.save(entity);
    }

    public void deleteById(T entity) {
        baseRepository.delete(entity);
    }


    private void throwFailureException(ID id) {
        String[] msgParam = {String.valueOf(id)};
        String msg = messageUtils.getMessage("validation.recordNotFound.message", msgParam);

        throw new EntityNotFoundException(msg);
    }

    protected void checkIfRecordAlreadyExist(ID id) {

        Optional<T> data = getById(id);
        boolean isAlreadyExist = data.isPresent();
        threwExceptionIfEntityExist(isAlreadyExist, "This record already exist");
    }

    protected void checkIfRecordIdIsNull(ID id) {
        boolean isAlreadyExist = Objects.nonNull(id);
        threwExceptionIfEntityExist(isAlreadyExist, "id should be null..");
    }

    private static void threwExceptionIfEntityExist(boolean isAlreadyExist, String errorMessage) {
        if (isAlreadyExist) {
            log.error(errorMessage);
            throw new EntityExistsException(errorMessage);
        }
    }

}
