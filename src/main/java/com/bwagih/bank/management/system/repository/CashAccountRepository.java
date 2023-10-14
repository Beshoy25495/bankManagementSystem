package com.bwagih.bank.management.system.repository;

import com.bwagih.bank.management.system.entity.CashAccount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface CashAccountRepository extends BaseRepository<CashAccount, Long> {


    @Modifying
    @Transactional
    @Query("update #{#entityName} t SET t.blockAmount = :blockAmount WHERE t.cashAccountId = :cashAccountId")
    Integer updateBlockAmount(@Param("cashAccountId") Long cashAccountId, @Param("blockAmount") BigDecimal blockAmount);


    @Modifying
    @Transactional
    @Query("update #{#entityName} t SET t.status = :status WHERE t.cashAccountId = :cashAccountId")
    Integer updateStatus(@Param("cashAccountId") Long cashAccountId, @Param("status") String status);


}
