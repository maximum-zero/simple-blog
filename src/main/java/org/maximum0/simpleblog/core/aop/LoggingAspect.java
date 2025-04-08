package org.maximum0.simpleblog.core.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * Controller 계층의 요청/응답/예외를 로깅하는 AOP Aspect.
 * traceId(MDC)가 있는 경우 함께 출력되며, 요청 파라미터 및 응답 결과도 JSON 형태로 로깅된다.
 */
@Aspect
@Component
class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper;

    public LoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Pointcut("execution(* org.maximum0.simpleblog.domain..controller..*(..))")
    public void controllerPointcut() {}

    /**
     * Controller 메서드 실행 전 요청 정보를 로깅.
     *
     * @param joinPoint 실행되는 메서드 정보
     */
    @Before("controllerPointcut()")
    public void logBeforeControllerExecution(JoinPoint joinPoint) {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request == null) {
            return;
        }

        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String params = extractRequestParams(request);

        log.info("▶️ {} {} - {}#{} | Params: {}", request.getMethod(), request.getRequestURI(), typeName, methodName, params);
    }

    /**
     * Controller 메서드가 성공적으로 반환되었을 때 결과를 로깅.
     *
     * @param joinPoint 실행된 메서드 정보
     * @param result 반환된 응답 객체
     */
    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void logAfterControllerExecution(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String response = toSafeJson(result);

        log.info("✅ {}#{} returned: {}", joinPoint.getSignature().getDeclaringTypeName(), methodName, response);
    }

    /**
     * Controller 메서드에서 예외가 발생한 경우 예외 내용을 로깅.
     *
     * @param joinPoint 예외가 발생한 메서드 정보
     * @param ex 발생한 예외
     */
    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "ex")
    public void logAfterException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();


        log.error("⛔️ Exception in {}#{}: {}", joinPoint.getSignature().getDeclaringTypeName(), methodName, ex.getMessage(), ex);
    }


    /**
     * 현재 HTTP 요청 객체를 반환.
     *
     * @return HttpServletRequest 객체 또는 null
     */
    private HttpServletRequest getCurrentHttpRequest() {
        var attributes = RequestContextHolder.getRequestAttributes();
        if (attributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 요청 파라미터를 문자열로 변환.
     *
     * @param request HttpServletRequest 객체
     * @return 파라미터 문자열 (예: key1=value1, key2=value2)
     */
    private String extractRequestParams(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap.isEmpty()) return "{}";

        StringBuilder sb = new StringBuilder();
        paramMap.forEach((key, value) -> sb.append(key).append("=").append(String.join(",", value)).append(", "));
        return sb.substring(0, sb.length() - 2);
    }

    /**
     * 객체를 JSON 문자열로 직렬화. 너무 긴 경우는 truncate 처리.
     *
     * @param result 직렬화할 객체
     * @return 직렬화된 문자열 또는 에러 메시지
     */
    private String toSafeJson(Object result) {
        try {
            String json = result != null ? objectMapper.writeValueAsString(result) : "null";
            return json.length() > 1000 ? json.substring(0, 1000) + "...(truncated)" : json;
        } catch (Exception e) {
            return "Error serializing response: " + e.getMessage();
        }
    }
}