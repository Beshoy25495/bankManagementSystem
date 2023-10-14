package com.bwagih.bank.management.system.controller;

import com.bwagih.bank.management.system.dto.AccountDto;
import com.bwagih.bank.management.system.dto.RoleDto;
import com.bwagih.bank.management.system.mapper.RoleMapper;
import com.bwagih.bank.management.system.service.RoleService;
import com.bwagih.bank.management.system.utils.IHandleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


@RestController
@RequestMapping("/role/")
@CrossOrigin(value = {"http://localhost:4200"})
@RequiredArgsConstructor
@Validated
@Log4j2
public class RoleController implements IHandleResponse {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping("get-all-records")
    public ResponseEntity<?> findAll() {
        return handle(roleMapper.mapBunchOfEntityToDto(new HashSet<>(roleService.findAll()))
                , HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> insert(@RequestBody RoleDto roleDto) {
        return handle(roleMapper.mapToDto(roleService.insert(roleMapper.mapToEntity(roleDto)))
                , HttpStatus.CREATED);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody RoleDto roleDto) {
        return handle(roleMapper.mapToDto(roleService.update(roleMapper.mapToEntity(roleDto)))
                , HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteById(@RequestBody RoleDto roleDto) {
        roleService.deleteById(roleMapper.mapToEntity(roleDto));
        return handle("OPERATION DONE SUCCESSFULLY", HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return handle(roleMapper.mapToDto(roleService.findById(id)), HttpStatus.OK);
    }

}
