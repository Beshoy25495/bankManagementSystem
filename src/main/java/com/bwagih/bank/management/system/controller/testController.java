package com.bwagih.bank.management.system.controller;

import com.bwagih.bank.management.system.entity.*;
import com.bwagih.bank.management.system.enums.RoleName;
import com.bwagih.bank.management.system.enums.StatusCode;
import com.bwagih.bank.management.system.enums.TransactionType;
import com.bwagih.bank.management.system.model.ResponseModel;
import com.bwagih.bank.management.system.repository.*;
import com.bwagih.bank.management.system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class testController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public ResponseEntity<ResponseModel> test() {

        return ResponseEntity.ok(
                ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .build()
        );
    }
}
