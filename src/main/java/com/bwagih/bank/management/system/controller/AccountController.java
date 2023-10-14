package com.bwagih.bank.management.system.controller;

import com.bwagih.bank.management.system.dto.AccountDto;
import com.bwagih.bank.management.system.mapper.AccountMapper;
import com.bwagih.bank.management.system.service.AccountService;
import com.bwagih.bank.management.system.utils.IHandleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


@RestController
@RequestMapping("/account/")
@CrossOrigin(value = {"http://localhost:4200"})
@RequiredArgsConstructor
@Validated
@Log4j2
public class AccountController implements IHandleResponse {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("get-all-records")
    public ResponseEntity<?> findAll() {
        return handle(accountMapper.mapBunchOfEntityToDto(new HashSet<>(accountService.findAll()))
                , HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> insert(@RequestBody AccountDto accountDto) {
        return handle(accountMapper.mapToDto(accountService.insert(accountMapper.mapToEntity(accountDto)))
                , HttpStatus.CREATED);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody AccountDto accountDto) {
        return handle(accountMapper.mapToDto(accountService.update(accountMapper.mapToEntity(accountDto)))
                , HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteById(@RequestBody AccountDto accountDto) {
        accountService.deleteById(accountMapper.mapToEntity(accountDto));
        return handle("OPERATION DONE SUCCESSFULLY", HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return handle(accountMapper.mapToDto(accountService.findById(id)), HttpStatus.OK);
    }

}
