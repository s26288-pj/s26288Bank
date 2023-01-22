package com.example.s26288bank.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends RuntimeException {
    private final String messsage;
}