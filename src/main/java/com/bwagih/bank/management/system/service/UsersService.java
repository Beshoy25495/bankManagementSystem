package com.bwagih.bank.management.system.service;

import com.bwagih.bank.management.system.commons.BaseService;
import com.bwagih.bank.management.system.dto.UsersDto;
import com.bwagih.bank.management.system.entity.Role;
import com.bwagih.bank.management.system.entity.Users;
import com.bwagih.bank.management.system.mapper.UsersMapper;
import com.bwagih.bank.management.system.repository.UsersRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class UsersService extends BaseService<Users, Long> {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersMapper userMapper;

    @Autowired
    private RoleService roleService;


    @PostConstruct
    public void init() {
        roleService.setUsersService(this);
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<Users> findAll() {
        return super.findAll();
    }

    @Override
    public Users insert(Users entity) {

        checkIfRecordIdIsNull(entity.getId());

        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.insert(entity);
    }


    @Override
    public Users findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public List<Users> findAllByIds(Iterable<Long> longs) {
        return super.findAllByIds(longs);
    }

    @Override
    public List<Users> insertAll(List<Users> entity) {
        entity.parallelStream().forEach(this::getDataFromDbIfIdIsAlreadyExist);

        return super.insertAll(entity);
    }

    @Override
    public Users update(Users entity) {

        checkIfRecordAlreadyExist(entity.getId());
        getDataFromDbIfIdIsAlreadyExist(entity);

        return super.update(entity);
    }

    @Override
    public void deleteById(Users entity) {
        super.deleteById(entity);
    }

    @Override
    public Optional<Users> getById(Long aLong) {

        Optional<Users> user = super.getById(aLong);
        if (user.isEmpty()) {
            log.error("invalid data , this user not found ..");
            throw new EntityNotFoundException("invalid data , this user not found ..");
        }

        return user;
    }

    private void getDataFromDbIfIdIsAlreadyExist(Users entity) {
        getRolesIfRoleIdsSentOrThrowException(entity);
    }

    private void getRolesIfRoleIdsSentOrThrowException(Users entity) {

        Set<Long> roles = entity.getRoles()
                .parallelStream().filter(Objects::nonNull).map(Role::getId).collect(Collectors.toCollection(HashSet::new));

        entity.setRoles(new HashSet<>(roleService.findAllByIds(roles)));
    }


    public UsersDto loadUserByUsername(@NotBlank String username) {


        Users user = usersRepository.findByUsername(username);

        if (user == null)
            throw new EntityNotFoundException("Invalid username or password.");
        else
            return userMapper.mapToDto(user);

    }


    public UsersDto loadUserByEmail(@Email @NotBlank String email)  {


        Users user = usersRepository.findByEmail(email);

        if (user == null)
            throw new EntityNotFoundException("Invalid email or password.");
        else
            return userMapper.mapToDto(user);

    }

//    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles) {
//        return roles.stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getRoleName().name()))
//                .collect(Collectors.toList());
//
////            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
////
////        return grantedAuthorities;
//    }


    ///////////////////////////////////
    ///////////////////////////////////

    public UsersDto register(UsersDto userLoginDto,
                             Map<String, String> headers, HttpServletRequest request) {
        Users usersEntity = new Users();

        log.info("call save in userRepository startup");
        try {
            usersEntity = userMapper.mapToEntity(userLoginDto);
            usersEntity = usersRepository.save(usersEntity);
            userLoginDto = userMapper.mapToDto(usersEntity);
            userLoginDto.setReplyCode("0");
            userLoginDto.setReplyMessage("Operation Done Successfully");
        } catch (Exception e) {
            log.error("call save in userRepository has been error : " + e.getMessage());
            userLoginDto = userMapper.mapToDto(usersEntity);
            userLoginDto.setReplyCode("E");
            userLoginDto.setReplyMessage("Opps! Error Has Occured .");
        }

        return userLoginDto;
    }

    public UsersDto update(UsersDto userLoginDto,
                           Map<String, String> headers, HttpServletRequest request) {
        Users usersEntity = new Users();

        log.info("call update in userRepository startup");
        try {
            usersEntity = userMapper.mapToEntity(userLoginDto);

            Users users = usersRepository.findByUsername(usersEntity.getUserName());

            if (users != null) {

                usersEntity = usersRepository.save(users);

                userLoginDto = userMapper.mapToDto(usersEntity);
                userLoginDto.setReplyCode("0");
                userLoginDto.setReplyMessage("Operation Done Successfully");
            } else {
                userLoginDto = userMapper.mapToDto(usersEntity);
                userLoginDto.setReplyCode("-1");
                userLoginDto.setReplyMessage("not found .");
            }

        } catch (Exception e) {
            log.error("call update in userRepository has been error : " + e.getMessage());
            userLoginDto = userMapper.mapToDto(usersEntity);
            userLoginDto.setReplyCode("E");
            userLoginDto.setReplyMessage("Opps! Error Has Occured .");
        }

        return userLoginDto;
    }

    public UsersDto delete(UsersDto userLoginDto,
                           Map<String, String> headers, HttpServletRequest request) {

        Users usersEntity = new Users();

        log.info("call delete in plotRepository startup");
        try {
            usersEntity = userMapper.mapToEntity(userLoginDto);

            Users users = usersRepository.findByUsername(usersEntity.getUserName());

            if (users != null) {

                Integer isDeleted = usersRepository.deleteUsersByUserName(usersEntity.getUserName());

                userLoginDto = new UsersDto();
                userLoginDto.setReplyCode(isDeleted > 0 ? "0" : "-1");
                userLoginDto.setReplyMessage(isDeleted > 0 ? "Operation Done Successfully" : "general error..");
            } else {
                userLoginDto = userMapper.mapToDto(usersEntity);
                userLoginDto.setReplyCode("-1");
                userLoginDto.setReplyMessage("not found..");
            }

        } catch (Exception e) {
            log.error("call delete in userRepository has been error : " + e.getMessage());
            userLoginDto = userMapper.mapToDto(usersEntity);
            userLoginDto.setReplyCode("E");
            userLoginDto.setReplyMessage("Opps! Error Has Occured .");
        }

        return userLoginDto;
    }



}
