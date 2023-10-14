package com.bwagih.bank.management.system.events;

import com.bwagih.bank.management.system.entity.CashAccount;
import com.bwagih.bank.management.system.entity.Transaction;
import com.bwagih.bank.management.system.model.TransactionCreated;
import com.bwagih.bank.management.system.service.CashAccountService;
import com.bwagih.bank.management.system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class TransactionEvent {

    private final TransactionService transactionService;
    private final CashAccountService cashAccountService;

    @EventListener(TransactionCreated.class)
//  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) -- uncomment to make pass tests in UserResourceJdbcTemplateTest
//  @Transactional(propagation = Propagation.REQUIRES_NEW) -- uncomment to make pass method
    public void onTransactionCreate(TransactionCreated transactionCreated) {
        Transaction transactionResult = transactionService.getById(transactionCreated.getTransactionNumber()).get();

        CashAccount cashAccount = transactionResult.getCashAccount();
        cashAccount.setBlockAmount(Objects.nonNull(cashAccount.getBlockAmount()) ? cashAccount.getBlockAmount()
                .add(transactionResult.getAmount()) : transactionResult.getAmount());

        cashAccountService.updateBlockAmount(cashAccount.getCashAccountId(), cashAccount.getBlockAmount());
    }


}