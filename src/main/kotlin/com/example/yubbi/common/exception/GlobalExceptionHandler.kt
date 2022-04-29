package com.example.yubbi.common.exception

import com.example.yubbi.common.exception.custom.NotFoundMemberException
import com.example.yubbi.common.exception.custom.NotMatchPasswordException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    fun generalExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse(ErrorCode.GENERAL_EXCEPTION))
    }

    @ExceptionHandler(value = [NotMatchPasswordException::class])
    fun notMatchPasswordExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.NOT_MATCH_PASSWORD))
    }

    @ExceptionHandler(value = [NotFoundMemberException::class])
    fun notFoundMemberExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.NOT_FOUND_MEMBER))
    }
}
