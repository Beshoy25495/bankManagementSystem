package com.bwagih.bank.management.system.controller;

import com.bwagih.bank.management.system.dto.AccountDto;
import com.bwagih.bank.management.system.dto.CashAccountDto;
import com.bwagih.bank.management.system.mapper.CashAccountMapper;
import com.bwagih.bank.management.system.service.CashAccountService;
import com.bwagih.bank.management.system.utils.IHandleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


@RestController
@RequestMapping("/cash-account/")
@CrossOrigin(value = {"http://localhost:4200"})
@RequiredArgsConstructor
@Validated
@Log4j2
public class CashAccountController implements IHandleResponse {
    private final CashAccountService cashAccountService;
    private final CashAccountMapper cashAccountMapper;

    @GetMapping("get-all-records")
    public ResponseEntity<?> findAll() {
        return handle(cashAccountMapper.mapBunchOfEntityToDto(new HashSet<>(cashAccountService.findAll()))
                , HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> insert(@RequestBody CashAccountDto cashAccountDto) {
        return handle(cashAccountMapper.mapToDto(cashAccountService.insert(cashAccountMapper.mapToEntity(cashAccountDto)))
                , HttpStatus.CREATED);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody CashAccountDto cashAccountDto) {
        return handle(cashAccountMapper.mapToDto(cashAccountService.update(cashAccountMapper.mapToEntity(cashAccountDto)))
                , HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteById(@RequestBody CashAccountDto cashAccountDto) {
        cashAccountService.deleteById(cashAccountMapper.mapToEntity(cashAccountDto));
        return handle("OPERATION DONE SUCCESSFULLY", HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return handle(cashAccountMapper.mapToDto(cashAccountService.findById(id)), HttpStatus.OK);
    }

}
