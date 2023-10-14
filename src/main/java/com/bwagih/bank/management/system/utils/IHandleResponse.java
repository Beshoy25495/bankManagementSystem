package com.bwagih.bank.management.system.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

//@FunctionalInterface
public interface IHandleResponse {

    default public <T> ResponseEntity<T> handle(T data , HttpStatus status) {
        return ResponseEntity
                .status(status)
                .contentType(new MediaType(MediaType.APPLICATION_JSON
                        , StandardCharsets.UTF_8))
                .body(data);
    }

}
