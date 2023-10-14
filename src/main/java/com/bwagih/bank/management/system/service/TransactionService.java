package com.bwagih.bank.management.system.service;

import com.bwagih.bank.management.system.commons.BaseService;
import com.bwagih.bank.management.system.entity.CashAccount;
import com.bwagih.bank.management.system.entity.Transaction;
import com.bwagih.bank.management.system.model.TransactionCreated;
import com.bwagih.bank.management.system.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionService extends BaseService<Transaction, Long> {

    private final TransactionRepository transactionRepository;
    private final CashAccountService cashAccountService;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public List<Transaction> findAll() {
        return super.findAll();
    }


    @Transactional
    @Override
    public Transaction insert(Transaction entity) {

        checkIfRecordIdIsNull(entity.getTransactionNumber());

        getDataFromDbIfIdIsAlreadyExist(entity);

        Transaction transactionResult = super.insert(entity);

        eventPublisher.publishEvent(TransactionCreated.builder()
                .transactionNumber(transactionResult.getTransactionNumber()).build());

        return transactionResult;
    }


    @Override
    public Transaction findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public List<Transaction> findAllByIds(Iterable<Long> longs) {
        return super.findAllByIds(longs);
    }

    @Override
    public List<Transaction> insertAll(List<Transaction> entity) {
        entity.parallelStream().forEach(this::getDataFromDbIfIdIsAlreadyExist);

        return super.insertAll(entity);
    }

    @Override
    public Transaction update(Transaction entity) {

        checkIfRecordAlreadyExist(entity.getTransactionNumber());
        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.update(entity);
    }

    @Override
    public void deleteById(Transaction entity) {
        super.deleteById(entity);
    }

    @Override
    public Optional<Transaction> getById(Long aLong) {

        Optional<Transaction> transaction = super.getById(aLong);
        if (transaction.isEmpty()) {
            log.error("invalid data , this transaction not found ..");
            throw new EntityNotFoundException("invalid data , this transaction not found ..");
        }

        return transaction;
    }

    public Iterable<Transaction> findByExecutionDateGreaterThanEqual(Date creationDateTime) {
        Iterable<Transaction> transactions;
        try {
            transactions =  transactionRepository.findByExecutionDateGreaterThanEqual(creationDateTime);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            transactions = new ArrayList<>();
        }

        return transactions;

    }

    private void getDataFromDbIfIdIsAlreadyExist(Transaction entity) {
        getCashAccountIfCashAccountIdSentOrThrowException(entity);
    }

    private void getCashAccountIfCashAccountIdSentOrThrowException(Transaction entity) {

        CashAccount cashAccount = entity.getCashAccount();
        if (Objects.nonNull(cashAccount) && Objects.nonNull(cashAccount.getCashAccountId())) {
            entity.setCashAccount(cashAccountService.getById(cashAccount.getCashAccountId()).get());
        } else {
            throw new EntityNotFoundException("cashAccount is Required..");
        }

    }


}
