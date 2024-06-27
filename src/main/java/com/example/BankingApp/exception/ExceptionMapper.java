package com.example.BankingApp.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.filter.ServerHttpObservationFilter;

import java.util.List;
@Slf4j
@RestControllerAdvice
public class ExceptionMapper {
    Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

    @ExceptionHandler(NoResultFoundException.class)
    public ProblemDetail handleException(HttpServletRequest httpServletRequest, NoResultFoundException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());
        problemDetail.setTitle("No result found.");
        addErrorForObservation(httpServletRequest,e);
        logger.info("Exception is thrown: {}",problemDetail.getTitle());
        return problemDetail;
    }
    @ExceptionHandler(ArithmeticException.class)
    public ProblemDetail handleException(HttpServletRequest httpServletRequest, ArithmeticException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,e.getMessage());
        problemDetail.setTitle("Problem with balance.");
        addErrorForObservation(httpServletRequest,e);
        logger.info("Exception is thrown: {}",problemDetail.getTitle());
        return problemDetail;
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ProblemDetail handleException(HttpServletRequest httpServletRequest, AuthenticationException e){
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,e.getMessage());
//        problemDetail.setTitle("User is unauthorized.");
//        addErrorForObservation(httpServletRequest,e);
//        logger.info("Exception is thrown: {}",problemDetail.getTitle());
//        return problemDetail;
//    }




    private static void addErrorForObservation(HttpServletRequest request,Throwable e){
        ServerHttpObservationFilter.findObservationContext(request)
                .ifPresent(context->context.setError(e));
    }
}
