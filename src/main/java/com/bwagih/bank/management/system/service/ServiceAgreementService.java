package com.bwagih.bank.management.system.service;

import com.bwagih.bank.management.system.commons.BaseService;
import com.bwagih.bank.management.system.entity.*;
import com.bwagih.bank.management.system.model.ResponseModel;
import com.bwagih.bank.management.system.model.ServiceAgreementCreated;
import com.bwagih.bank.management.system.model.TransactionCreated;
import com.bwagih.bank.management.system.repository.ServiceAgreementRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class ServiceAgreementService extends BaseService<ServiceAgreement, Long> {

    private final ServiceAgreementRepository agreementRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoleService roleService;


    @PostConstruct
    public void init() {
        roleService.setAgreementService(this);
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<ServiceAgreement> findAll() {
        return super.findAll();
    }

    @Override
    public ServiceAgreement insert(ServiceAgreement entity) {

        checkIfRecordIdIsNull(entity.getAgreementId());

        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.insert(entity);
    }


    @Override
    public ServiceAgreement findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public List<ServiceAgreement> findAllByIds(Iterable<Long> longs) {
        return super.findAllByIds(longs);
    }

    @Override
    public List<ServiceAgreement> insertAll(List<ServiceAgreement> entity) {
        entity.parallelStream().forEach(this::getDataFromDbIfIdIsAlreadyExist);

        return super.insertAll(entity);
    }

    @Override
    public ServiceAgreement update(ServiceAgreement entity) {

        checkIfRecordAlreadyExist(entity.getAgreementId());
        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.update(entity);
    }

    @Override
    public void deleteById(ServiceAgreement entity) {
        super.deleteById(entity);
    }

    @Override
    public Optional<ServiceAgreement> getById(Long aLong) {

        Optional<ServiceAgreement> serviceAgreement = super.getById(aLong);
        if (serviceAgreement.isEmpty()) {
            log.error("invalid data , this agreement not found ..");
            throw new EntityNotFoundException("invalid data , this cashAccount not found ..");
        }

        return serviceAgreement;
    }


    @Transactional
    public ResponseModel approveServiceAgreement(Long agreementId) {

        getById(agreementId).orElseThrow();

        Integer cashAccountUpdated = agreementRepository.approveServiceAgreement(agreementId, "A");

        if (!(cashAccountUpdated > 0)) {
            throw new RuntimeException("agreement didn't be approved..");
        }

        eventPublisher.publishEvent(ServiceAgreementCreated.builder()
                .agreementId(agreementId).build());

        return ResponseModel
                .builder()
                .status(HttpStatus.OK)
                .success(cashAccountUpdated > 0 ? Boolean.TRUE : Boolean.FALSE)
                .data("operation done successfully..")
                .build();
    }

    private void getDataFromDbIfIdIsAlreadyExist(ServiceAgreement entity) {
        getKycIndividualIfClientIdSentOrThrowException(entity);
        getRolesIfRoleIdsSentOrThrowException(entity);
    }

    private void getKycIndividualIfClientIdSentOrThrowException(ServiceAgreement entity) {
        Client kycIndividual = entity.getClient();
        if (Objects.nonNull(kycIndividual) && Objects.nonNull(kycIndividual.getClientId())) {
            entity.setClient(clientService.getById(kycIndividual.getClientId()).get());
        } else {
            throw new EntityNotFoundException("clientId is Required..");
        }
    }

    private void getRolesIfRoleIdsSentOrThrowException(ServiceAgreement entity) {

        Set<Long> roles = entity.getRoles()
                .parallelStream().filter(Objects::nonNull).map(Role::getId).collect(Collectors.toCollection(HashSet::new));

        entity.setRoles(new HashSet<>(roleService.findAllByIds(roles)));
    }


}
