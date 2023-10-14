package com.bwagih.bank.management.system.service;

import com.bwagih.bank.management.system.commons.BaseService;
import com.bwagih.bank.management.system.entity.Account;
import com.bwagih.bank.management.system.entity.CashAccount;
import com.bwagih.bank.management.system.entity.Client;
import com.bwagih.bank.management.system.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class AccountService extends BaseService<Account, Long> {

    private final AccountRepository accountRepository;
    private ClientService clientService;
    private CashAccountService cashAccountService;


    @Autowired
    public AccountService(AccountRepository accountRepository, @Lazy ClientService clientService, @Lazy CashAccountService cashAccountService) {
        this.accountRepository = accountRepository;
        this.clientService = clientService;
        this.cashAccountService = cashAccountService;
    }

    public void setCashAccountService(CashAccountService cashAccountService) {
        this.cashAccountService = cashAccountService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public List<Account> findAll() {
        return super.findAll();
    }

    @Override
    public Account insert(Account entity) {

        checkIfRecordIdIsNull(entity.getAccountNumber());

        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.insert(entity);
    }


    @Override
    public Account findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public List<Account> findAllByIds(Iterable<Long> longs) {
        return super.findAllByIds(longs);
    }

    @Override
    public List<Account> insertAll(List<Account> entity) {
        entity.parallelStream().forEach(this::getDataFromDbIfIdIsAlreadyExist);

        return super.insertAll(entity);
    }

    @Override
    public Account update(Account entity) {

        checkIfRecordAlreadyExist(entity.getAccountNumber());
        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.update(entity);
    }

    @Override
    public void deleteById(Account entity) {
        super.deleteById(entity);
    }

    @Override
    public Optional<Account> getById(Long aLong) {

        Optional<Account> account = super.getById(aLong);
        if (account.isEmpty()) {
            log.error("invalid data , this cashAccount not found ..");
            throw new EntityNotFoundException("invalid data , this cashAccount not found ..");
        }

        return account;
    }

    private void getDataFromDbIfIdIsAlreadyExist(Account entity) {
        getKycIndividualIfClientIdSentOrThrowException(entity);
        getCashAccountsIfCashAccountIdsSentOrThrowException(entity);
    }

    private void getKycIndividualIfClientIdSentOrThrowException(Account entity) {
        Client kycIndividual = entity.getClient();
        if (Objects.nonNull(kycIndividual) && Objects.nonNull(kycIndividual.getClientId())) {
            entity.setClient(clientService.getById(kycIndividual.getClientId()).get());
        } else {
            throw new EntityNotFoundException("clientId is Required..");
        }
    }

    private void getCashAccountsIfCashAccountIdsSentOrThrowException(Account entity) {

        Set<Long> cashAccounts = entity.getCashAccounts()
                .parallelStream().filter(Objects::nonNull).map(CashAccount::getCashAccountId).collect(Collectors.toCollection(HashSet::new));

        entity.setCashAccounts(new HashSet<>(cashAccountService.findAllByIds(cashAccounts)));
    }


}
