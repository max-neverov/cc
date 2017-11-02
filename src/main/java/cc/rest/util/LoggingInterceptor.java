package cc.rest.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Maxim Neverov
 */
@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_ID = "requestId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // in real app it would be taken from HTTP header
        String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, requestId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (request.getQueryString() != null) {
            log.info("{} {}?{}, status: {}, remoteAddress: {}", request.getMethod(), request.getRequestURI(),
                    response.getStatus(), request.getRemoteAddr(), request.getQueryString());
        } else {
            log.info("{} {}, status: {}, remoteAddress: {}", request.getMethod(), request.getRequestURI(),
                    response.getStatus(), request.getRemoteAddr());
        }
        MDC.remove(REQUEST_ID);
    }

}
