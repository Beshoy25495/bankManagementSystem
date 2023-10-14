package com.bwagih.bank.management.system.schedulers;

import com.bwagih.bank.management.system.entity.CashAccount;
import com.bwagih.bank.management.system.service.CashAccountService;
import com.bwagih.bank.management.system.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Configuration
@EnableScheduling
@Log4j2
public class EODTransExecutionDateScheduler {
    @Autowired
    TransactionService transactionService;


    @Scheduled(cron = "0 0 0 * * *")
    @Async("threadPoolTaskScheduler")
    public void transExecutionDate() {
        try {
            log.info("STARTING ::>----------EODTransExecutionDateScheduler.transExecutionDate()");

            //get all transactions where execution_date greater than or equals currentDate
            getTransactionsPerExecutionDate();

        } catch (Exception e) {
            log.error("-------------EODTransExecutionDateScheduler.transExecutionDate()", e);
        }
    }

    private void getTransactionsPerExecutionDate() {
        transactionService.findByExecutionDateGreaterThanEqual(new Date()).forEach(transaction -> {
            //update their cashAccounts
            CashAccount cashAccount = transaction.getCashAccount();
            getTransactionsPerExecutionDate(cashAccount);

        });
    }

    private static void getTransactionsPerExecutionDate(CashAccount cashAccount) {
        if (Objects.nonNull(cashAccount)) {
            if (cashAccount.getBlockAmount().compareTo(BigDecimal.ZERO) > 0) {

                cashAccount.setBalance(Objects.nonNull(cashAccount.getBalance())
                        ? cashAccount.getBalance().add(cashAccount.getBlockAmount())
                        : cashAccount.getBlockAmount());
                cashAccount.setBlockAmount(BigDecimal.ZERO);

            } else if (Objects.nonNull(cashAccount.getBalance())
                    && (cashAccount.getBalance().compareTo(cashAccount.getBlockAmount()) > 0)) {
                cashAccount.setBalance(cashAccount.getBalance().add(cashAccount.getBlockAmount()));
                cashAccount.setBlockAmount(BigDecimal.ZERO);
            }

        }
    }


    @Bean(value = "threadPoolTaskScheduler")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        return taskScheduler;
    }

}
