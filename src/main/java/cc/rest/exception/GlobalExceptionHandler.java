package cc.rest.exception;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Maxim Neverov
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public NewsletterError handleOtherExceptions(Throwable t) {
        log.error(t.getMessage(), t);
        return new NewsletterError("News-101", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler({DataAccessException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public NewsletterError handleDatabaseErrors(DataAccessException e) {
        log.error(e.getMessage(), e);
        return new NewsletterError("News-102", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public NewsletterError handleIntegrityErrors(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        return new NewsletterError("News-103", HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler({NewsletterBadRequest.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public NewsletterError handle(NewsletterBadRequest e) {
        log.error(e.getMessage(), e);
        return new NewsletterError("News-104", e.getMessage());
    }

    @ExceptionHandler({HystrixRuntimeException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public NewsletterError handle(HystrixRuntimeException e) {
        log.error(e.getMessage());
        return new NewsletterError("News-105", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
    }

}
