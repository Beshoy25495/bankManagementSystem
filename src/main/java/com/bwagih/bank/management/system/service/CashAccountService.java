package com.bwagih.bank.management.system.service;

import com.bwagih.bank.management.system.commons.BaseService;
import com.bwagih.bank.management.system.entity.Account;
import com.bwagih.bank.management.system.entity.CashAccount;
import com.bwagih.bank.management.system.repository.CashAccountRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class CashAccountService extends BaseService<CashAccount, Long> {

    private final CashAccountRepository cashAccountRepository;

    @Autowired
    private AccountService accountService;

    @PostConstruct
    public void init() {
        accountService.setCashAccountService(this);
    }


    @Override
    public List<CashAccount> findAll() {
        return super.findAll();
    }

    @Override
    public CashAccount insert(CashAccount entity) {

        checkIfRecordIdIsNull(entity.getCashAccountId());

        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.insert(entity);
    }


    @Override
    public CashAccount findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public List<CashAccount> findAllByIds(Iterable<Long> longs) {
        return super.findAllByIds(longs);
    }

    @Override
    public List<CashAccount> insertAll(List<CashAccount> entity) {
        entity.parallelStream().forEach(this::getDataFromDbIfIdIsAlreadyExist);

        return super.insertAll(entity);
    }

    @Override
    public CashAccount update(CashAccount entity) {

        checkIfRecordAlreadyExist(entity.getCashAccountId());
        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.update(entity);
    }

    @Override
    public void deleteById(CashAccount entity) {
        super.deleteById(entity);
    }

    @Override
    public Optional<CashAccount> getById(Long aLong) {

        Optional<CashAccount> cashAccount = super.getById(aLong);
        if (cashAccount.isEmpty()) {
            log.error("invalid data , this cashAccount not found ..");
            throw new EntityNotFoundException("invalid data , this cashAccount not found ..");
        }

        return cashAccount;
    }


    public void updateBlockAmount(Long cashAccountId, BigDecimal blockAmount) {

        getById(cashAccountId);

        Integer cashAccountUpdated = cashAccountRepository.updateBlockAmount(cashAccountId, blockAmount);

        if (!(cashAccountUpdated > 0)) {
            throw new RuntimeException("BlockAmount did not be update ..");
        }
    }

    public void updateCashAccStatus(Long cashAccountId, String status) {

        getById(cashAccountId);

        Integer cashAccountUpdated = cashAccountRepository.updateStatus(cashAccountId, status);

        if (!(cashAccountUpdated > 0)) {
            throw new RuntimeException("cannot be update status ..");
        }
    }

    private void getDataFromDbIfIdIsAlreadyExist(CashAccount entity) {
        getAccountIfAccountIdSentOrThrowException(entity);
    }

    private void getAccountIfAccountIdSentOrThrowException(CashAccount entity) {
        Account account = entity.getAccount();
        if (Objects.nonNull(account) && Objects.nonNull(account.getAccountNumber())) {
            entity.setAccount(accountService.getById(account.getAccountNumber()).get());
        } else {
            throw new EntityNotFoundException("account is Required..");
        }
    }


}
