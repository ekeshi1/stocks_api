package com.pp.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class ControllerUtil {
    public static <T> ResponseEntity<?> illegalArgumentWrapper(Supplier<T> responseSupplier) {
        try {
            return ResponseEntity.ok(responseSupplier.get());
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
