package org.maximum0.simpleblog.domain.exception.controller;


import org.maximum0.simpleblog.core.exception.BaseException;
import org.maximum0.simpleblog.core.exception.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exception")
public class ExceptionController {

    @GetMapping("/throw-generic-exception")
    public void  throwGenericException() {
        throw new RuntimeException("Test Exception");
    }

    @GetMapping("/throw-base-exception")
    public void  throwBaseException(@RequestParam ErrorCode errorCode) {
        throw new BaseException(errorCode);
    }

}
