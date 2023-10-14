package com.bwagih.bank.management.system.repository;

import com.bwagih.bank.management.system.entity.ServiceAgreement;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ServiceAgreementRepository extends BaseRepository<ServiceAgreement, Long> {

    @Modifying
    @Transactional
    @Query("update #{#entityName} t SET t.status = :status WHERE t.agreementId = :agreementId")
    Integer approveServiceAgreement(@Param("agreementId") Long agreementId, @Param("status") String status);

}
