package com.bwagih.bank.management.system.repository;

import com.bwagih.bank.management.system.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsersRepository extends BaseRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE UPPER(TRIM(u.email))=UPPER(TRIM(:email))")
    public Users findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE UPPER(TRIM(u.userName))=UPPER(TRIM(:username))")
    public Users findByUsername(@Param("username") String username);

    @Transactional
    public Integer deleteUsersByUserName(String username);

    Page<Users> findAll(Specification specification, Pageable pageable);

}
