package com.bwagih.bank.management.system.service;

import com.bwagih.bank.management.system.commons.BaseService;
import com.bwagih.bank.management.system.entity.*;
import com.bwagih.bank.management.system.repository.RoleRepository;
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
public class RoleService extends BaseService<Role, Long> {

    private final RoleRepository roleRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ServiceAgreementService agreementService;

    @PostConstruct
    public void init() {
        usersService.setRoleService(this);
        agreementService.setRoleService(this);
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public void setAgreementService(ServiceAgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @Override
    public List<Role> findAll() {
        return super.findAll();
    }

    @Override
    public Role insert(Role entity) {

        checkIfRecordIdIsNull(entity.getId());

        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.insert(entity);
    }


    @Override
    public Role findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public List<Role> findAllByIds(Iterable<Long> longs) {
        return super.findAllByIds(longs);
    }

    @Override
    public List<Role> insertAll(List<Role> entity) {
        entity.parallelStream().forEach(this::getDataFromDbIfIdIsAlreadyExist);

        return super.insertAll(entity);
    }

    @Override
    public Role update(Role entity) {

        checkIfRecordAlreadyExist(entity.getId());
        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.update(entity);
    }

    @Override
    public void deleteById(Role entity) {
        super.deleteById(entity);
    }

    @Override
    public Optional<Role> getById(Long aLong) {

        Optional<Role> role = super.getById(aLong);
        if (role.isEmpty()) {
            log.error("invalid data , this role not found ..");
            throw new EntityNotFoundException("invalid data , this role not found ..");
        }

        return role;
    }

    private void getDataFromDbIfIdIsAlreadyExist(Role entity) {
        getUsersIfUsersIdsSentOrThrowException(entity);
        getAgreementsIfrAgreementIdsSentOrThrowException(entity);
    }


    private void getUsersIfUsersIdsSentOrThrowException(Role entity) {

        Set<Long> users = entity.getUsers()
                .parallelStream().filter(Objects::nonNull).map(Users::getId).collect(Collectors.toCollection(HashSet::new));

        entity.setUsers(new HashSet<>(usersService.findAllByIds(users)));
    }

    private void getAgreementsIfrAgreementIdsSentOrThrowException(Role entity) {

        Set<Long> agreement = entity.getAgreement()
                .parallelStream().filter(Objects::nonNull).map(ServiceAgreement::getAgreementId).collect(Collectors.toCollection(HashSet::new));

        entity.setAgreement(new HashSet<>(agreementService.findAllByIds(agreement)));
    }


}
