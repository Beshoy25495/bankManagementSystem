package com.bwagih.bank.management.system.controller;

import com.bwagih.bank.management.system.dto.AccountDto;
import com.bwagih.bank.management.system.dto.TransactionDto;
import com.bwagih.bank.management.system.mapper.TransactionMapper;
import com.bwagih.bank.management.system.service.TransactionService;
import com.bwagih.bank.management.system.utils.IHandleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


@RestController
@RequestMapping("/transaction/")
@CrossOrigin(value = {"http://localhost:4200"})
@RequiredArgsConstructor
@Validated
@Log4j2
public class TransactionController implements IHandleResponse {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping("get-all-records")
    public ResponseEntity<?> findAll() {
        return handle(transactionMapper.mapBunchOfEntityToDto(new HashSet<>(transactionService.findAll()))
                , HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> insert(@RequestBody TransactionDto transactionDto) {
        return handle(transactionMapper.mapToDto(transactionService.insert(transactionMapper.mapToEntity(transactionDto)))
                , HttpStatus.CREATED);
    }

}
