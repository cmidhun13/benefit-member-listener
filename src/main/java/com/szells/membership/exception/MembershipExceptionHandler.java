package com.szells.membership.exception;


import com.szells.util.domain.ReturnDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class MembershipExceptionHandler {

    private static ReturnDetail errorResponse(AppException ex) {
        ReturnDetail returnDetail = new ReturnDetail();
        returnDetail.setErrorMsg(ex.getMessage());
        returnDetail.setErrorSource("membership-service");
        returnDetail.setErrorCode(ex.getErrCode());
        return returnDetail;
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ReturnDetail> handleMemberNotFoundException(MemberNotFoundException ex, WebRequest wr) {
        return ((ResponseEntity.BodyBuilder) ResponseEntity.notFound()).body(errorResponse(ex));
    }

    @ExceptionHandler(MemberSaveException.class)
    public ResponseEntity<ReturnDetail> handleMemberSaveException(MemberSaveException ex, WebRequest wr) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(ex));
    }

    @ExceptionHandler(InvalidMembershipIdException.class)
    public ResponseEntity<ReturnDetail> handleInvalidMembershipIdException(InvalidMembershipIdException ex, WebRequest wr) {
        return ResponseEntity.badRequest().body(errorResponse(ex));
    }


}
