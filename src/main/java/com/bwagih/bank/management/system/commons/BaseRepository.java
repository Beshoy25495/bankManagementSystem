package com.bwagih.bank.management.system.commons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Number> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

//	@Modifying
//	@Transactional
//	@Query("update #{#entityName} t SET t.status = :status WHERE t.id = :id")
//	void updateEntity(@Param("id") ID id, @Param("status") String statusCode);

}
