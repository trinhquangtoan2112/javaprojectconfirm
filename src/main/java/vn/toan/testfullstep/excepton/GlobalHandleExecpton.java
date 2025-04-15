package vn.toan.testfullstep.excepton;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandleExecpton {
//    @ExceptionHandler(ResourceNotFoundExcepton.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse handleEmailExists(Exception e, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse();
//
//        errorResponse.setTimestamp(new Date());
//         errorResponse.setStatus(HttpStatus.CONFLICT.value());
//        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
//        errorResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
//
//        String mess = e.getMessage();
//
//        errorResponse.setMessage(mess);
//
//        return errorResponse;
//
//    }
}
