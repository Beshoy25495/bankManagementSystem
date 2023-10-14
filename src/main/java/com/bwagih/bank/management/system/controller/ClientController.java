package com.bwagih.bank.management.system.controller;

import com.bwagih.bank.management.system.dto.AccountDto;
import com.bwagih.bank.management.system.dto.ClientDto;
import com.bwagih.bank.management.system.mapper.ClientMapper;
import com.bwagih.bank.management.system.service.ClientService;
import com.bwagih.bank.management.system.utils.IHandleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


@RestController
@RequestMapping("/client/")
@CrossOrigin(value = {"http://localhost:4200"})
@RequiredArgsConstructor
@Validated
@Log4j2
public class ClientController implements IHandleResponse {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping("get-all-records")
    public ResponseEntity<?> findAll() {
        return handle(clientMapper.mapBunchOfEntityToDto(new HashSet<>(clientService.findAll()))
                , HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> insert(@RequestBody ClientDto clientDto) {
        return handle(clientMapper.mapToDto(clientService.insert(clientMapper.mapToEntity(clientDto)))
                , HttpStatus.CREATED);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody ClientDto clientDto) {
        return handle(clientMapper.mapToDto(clientService.update(clientMapper.mapToEntity(clientDto)))
                , HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteById(@RequestBody ClientDto clientDto) {
        clientService.deleteById(clientMapper.mapToEntity(clientDto));
        return handle("OPERATION DONE SUCCESSFULLY", HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return handle(clientMapper.mapToDto(clientService.findById(id)), HttpStatus.OK);
    }

}
