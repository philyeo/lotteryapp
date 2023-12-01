package com.philyeo.lotteryapp.shared.web.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@RestControllerAdvice
public class LotteryAppControllerAdvice {

    @ExceptionHandler(LotteryAppException.class)
    public ResponseEntity<Problem> handleLotteryAppException(LotteryAppException ex) {
        // Create a Problem instance based on the exception details
//        Problem problem = new LotteryAppProblem(Status.BAD_REQUEST, ex.getMessage(), ex.getDetail());
        Problem problem = Problem.builder()
            .withTitle(ex.getMessage())
            .withStatus(Status.BAD_REQUEST)
            .withDetail(ex.getErrorCode() + ": " + ex.getDetail())
            .build();

        // Return the ResponseEntity with the Problem instance
        return ResponseEntity.status(problem.getStatus().getStatusCode())
            .body(problem);
    }
}
