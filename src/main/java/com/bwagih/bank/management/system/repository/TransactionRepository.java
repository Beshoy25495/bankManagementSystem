package com.bwagih.bank.management.system.repository;

import com.bwagih.bank.management.system.entity.Transaction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long> {


    Iterable<Transaction> findByExecutionDateGreaterThanEqual(@Param("creationDateTime") Date creationDateTime);
}
