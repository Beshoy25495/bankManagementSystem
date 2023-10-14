package com.bwagih.bank.management.system.repository;

import com.bwagih.bank.management.system.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

}
