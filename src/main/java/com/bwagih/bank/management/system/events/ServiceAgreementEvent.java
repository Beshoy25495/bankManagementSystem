package com.bwagih.bank.management.system.events;

import com.bwagih.bank.management.system.entity.*;
import com.bwagih.bank.management.system.mapper.UsersMapper;
import com.bwagih.bank.management.system.model.ServiceAgreementCreated;
import com.bwagih.bank.management.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Log4j2
public class ServiceAgreementEvent {

    private final ServiceAgreementService agreementService;
    private final UsersService usersService;
    private final UsersMapper usersMapper;

    @EventListener(ServiceAgreementCreated.class)
//  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) -- uncomment to make pass tests in UserResourceJdbcTemplateTest
//  @Transactional(propagation = Propagation.REQUIRES_NEW) -- uncomment to make pass method
    public void onAgreementCreate(ServiceAgreementCreated agreementCreated) {
        ServiceAgreement agreementResult = agreementService.getById(agreementCreated.getAgreementId()).get();

        usersService.insert(usersMapper.mapAgreementToUser(agreementResult));
    }


}