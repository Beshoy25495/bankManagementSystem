package com.bwagih.bank.management.system.controller;

import com.bwagih.bank.management.system.dto.UsersDto;
import com.bwagih.bank.management.system.mapper.UsersMapper;
import com.bwagih.bank.management.system.service.UsersService;
import com.bwagih.bank.management.system.utils.IHandleResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;


@RestController
@RequestMapping("/users/")
@CrossOrigin(value = {"http://localhost:4200"})
@RequiredArgsConstructor
@Validated
@Log4j2
public class UsersController implements IHandleResponse {
    private final UsersMapper usersMapper;
    private final UsersService myUserDetailsService;

    @PostMapping({"/", ""})
    public ResponseEntity<UsersDto> register(@RequestBody UsersDto userLoginDto,
                                             @RequestHeader Map<String, String> headers,
                                             HttpServletRequest request) {

        log.info("call register in user Service startup");
        userLoginDto = myUserDetailsService.register(userLoginDto, headers, request);
        if (userLoginDto.getReplyCode().equals("0")) {
            return handle(userLoginDto, HttpStatus.CREATED);
        }
        return handle(userLoginDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/update")
    public ResponseEntity<UsersDto> update(@RequestBody UsersDto userLoginDto,
                                           @RequestHeader Map<String, String> headers,
                                           HttpServletRequest request) {
        log.info("call update in user Service startup");
        userLoginDto = myUserDetailsService.update(userLoginDto, headers, request);
        if (userLoginDto.getReplyCode().equals("0"))
            return handle(userLoginDto, HttpStatus.OK);
        else if (userLoginDto.getReplyCode().equals("-1"))
            return handle(userLoginDto, HttpStatus.SERVICE_UNAVAILABLE);
        else
            return handle(userLoginDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/delete")
    public ResponseEntity<UsersDto> delete(@RequestBody UsersDto userLoginDto,
                                           @RequestHeader Map<String, String> headers,
                                           HttpServletRequest request) {

        log.info("call delete in user Service startup");
        userLoginDto = myUserDetailsService.delete(userLoginDto, headers, request);
        if (userLoginDto.getReplyCode().equals("0"))
            return handle(userLoginDto, HttpStatus.OK);
        else if (userLoginDto.getReplyCode().equals("-1"))
            return handle(userLoginDto, HttpStatus.SERVICE_UNAVAILABLE);
        else
            return handle(userLoginDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //////////////////////////////////////////////
    //////////////////////////////////////////////

//    @PreAuthorize("hasAnyRole('SUPERVISOR' , 'TRADER')")
//    @RequestMapping(method = RequestMethod.GET, value = "/getUserData")
//    public ResponseEntity<CustomUser> getUserData(@RequestHeader Map<String, String> headers) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        CustomUser user = (CustomUser) auth.getPrincipal();
//        if (user.getFirstName() != null && user.getLastName() != null)
//            user.setFullName(user.getFirstName().concat(" ").concat(user.getLastName()));
//
//        if (headers.get("authorization") != null) {
//            String userName_pass = headers.get("authorization")
//                    .replace("Basic ", "");
//
//            byte[] decodedBytes = Base64.getDecoder().decode(userName_pass);
//            String decodedString = new String(decodedBytes);
//            user.setPass(decodedString.replace(user.getUsername() + ":", ""));
//        }
//
//        return new ResponseEntity<CustomUser>(user, HttpStatus.OK);
//    }


    //for jwt
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginByUserName(@RequestBody UsersDto userObj,
                                          @RequestHeader Map<String, String> headers,
                                          HttpServletRequest request) {

        /*validation for username and password if success return 200 Ok
          (vreified) else return Invalid username or password 401 */
//        authenticationManager.authenticate(
//                SecurityContextHolder.getContext().getAuthentication()
////                new UsernamePasswordAuthenticationToken(
////                        userObj.getUserName(),
////                        userObj.getPlainPassword()
////                )
//        );

        UsersDto userDetails = myUserDetailsService.loadUserByUsername(userObj.getUserName());

        return handle(userDetails, HttpStatus.OK);

    }

    @RequestMapping(value = "/getDataByUsername/{username}", method = RequestMethod.GET)
    public ResponseEntity getDataByUsername(@Valid @PathVariable String username,
                                            @RequestHeader Map<String, String> headers,
                                            HttpServletRequest request) {
        UsersDto userObj = new UsersDto();
        UsersDto userDetails = myUserDetailsService.loadUserByUsername(username);
        return handle(userDetails, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginByEmail", method = RequestMethod.POST)
    public ResponseEntity loginByEmail(@RequestBody UsersDto userObj) {
        log.info("call loginByEmail in userController startup");

        /*validation for username and password if success return 200 Ok
          (vreified) else return Invalid username or password 401 */
//        authenticationManager.authenticate(
//                SecurityContextHolder.getContext().getAuthentication()
//        );

        UsersDto userDetails = myUserDetailsService.loadUserByEmail(userObj.getEmail());

        return ResponseEntity.ok(userDetails);

    }

    @GetMapping("get-all-records")
    public ResponseEntity<?> findAll() {
        return handle(usersMapper.mapBunchOfEntityToDto(new HashSet<>(myUserDetailsService.findAll()))
                , HttpStatus.OK);
    }


}
