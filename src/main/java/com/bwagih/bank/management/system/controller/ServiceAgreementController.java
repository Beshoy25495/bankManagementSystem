package com.bwagih.bank.management.system.controller;

import com.bwagih.bank.management.system.dto.ServiceAgreementDto;
import com.bwagih.bank.management.system.dto.TransactionDto;
import com.bwagih.bank.management.system.mapper.ServiceAgreementMapper;
import com.bwagih.bank.management.system.model.ResponseModel;
import com.bwagih.bank.management.system.service.ServiceAgreementService;
import com.bwagih.bank.management.system.utils.IHandleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


@RestController
@RequestMapping("/service-agreement/")
@CrossOrigin(value = {"http://localhost:4200"})
@RequiredArgsConstructor
@Validated
@Log4j2
public class ServiceAgreementController implements IHandleResponse {
    private final ServiceAgreementService agreementService;
    private final ServiceAgreementMapper agreementMapper;

    @GetMapping("get-all-records")
    public ResponseEntity<?> findAll() {
        return handle(agreementMapper.mapBunchOfEntityToDto(new HashSet<>(agreementService.findAll()))
                , HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> insert(@RequestBody ServiceAgreementDto agreementDto) {
        return handle(agreementMapper.mapToDto(agreementService.insert(agreementMapper.mapToEntity(agreementDto)))
                , HttpStatus.CREATED);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody ServiceAgreementDto agreementDto) {
        return handle(agreementMapper.mapToDto(agreementService.update(agreementMapper.mapToEntity(agreementDto)))
                , HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteById(@RequestBody ServiceAgreementDto agreementDto) {
        agreementService.deleteById(agreementMapper.mapToEntity(agreementDto));
        return handle("OPERATION DONE SUCCESSFULLY", HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return handle(agreementMapper.mapToDto(agreementService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("approve/{agreementId}")
    public ResponseEntity<?> approveServiceAgreement(@PathVariable long agreementId) {
        return handle(agreementService.approveServiceAgreement(agreementId), HttpStatus.OK);
    }
}
