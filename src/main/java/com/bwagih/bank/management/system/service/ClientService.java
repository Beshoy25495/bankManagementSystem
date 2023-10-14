package com.bwagih.bank.management.system.service;

import com.bwagih.bank.management.system.commons.BaseService;
import com.bwagih.bank.management.system.entity.Account;
import com.bwagih.bank.management.system.entity.Client;
import com.bwagih.bank.management.system.repository.ClientRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class ClientService extends BaseService<Client, Long> {

    private final ClientRepository clientRepository;

    @Autowired
    private AccountService accountService;


    @PostConstruct
    public void init() {
        accountService.setClientService(this);
    }


    @Override
    public List<Client> findAll() {
        return super.findAll();
    }

    @Override
    public Client insert(Client entity) {

        checkIfRecordIdIsNull(entity.getClientId());

        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.insert(entity);
    }


    @Override
    public Client findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public List<Client> findAllByIds(Iterable<Long> longs) {
        return super.findAllByIds(longs);
    }

    @Override
    public List<Client> insertAll(List<Client> entity) {
        entity.parallelStream().forEach(this::getDataFromDbIfIdIsAlreadyExist);

        return super.insertAll(entity);
    }

    @Override
    public Client update(Client entity) {

        checkIfRecordAlreadyExist(entity.getClientId());
        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.update(entity);
    }

    @Override
    public void deleteById(Client entity) {
        super.deleteById(entity);
    }

    @Override
    public Optional<Client> getById(Long aLong) {

        Optional<Client> client = super.getById(aLong);
        if (client.isEmpty()) {
            log.error("invalid data , this client not found ..");
            throw new EntityNotFoundException("invalid data , this client not found ..");
        }

        return client;
    }

    private void getDataFromDbIfIdIsAlreadyExist(Client entity) {
        getAccountsIfAccountIdsSentOrThrowException(entity);
    }

    private void getAccountsIfAccountIdsSentOrThrowException(Client entity) {

        Set<Long> accounts = entity.getAccounts()
                .parallelStream().filter(Objects::nonNull).map(Account::getAccountNumber).collect(Collectors.toCollection(HashSet::new));

        entity.setAccounts(new HashSet<>(accountService.findAllByIds(accounts)));
    }


}
