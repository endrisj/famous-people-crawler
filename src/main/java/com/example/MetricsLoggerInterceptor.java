package com.example;

import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class MetricsLoggerInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private MetricsEndpoint metricsEndpoint;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logMetrics();
    }    
    
    private void logMetrics() {
        Map<String, Object> metricsToLog = metricsEndpoint.invoke().entrySet()
            .stream()
            .filter(metric -> isMetricToLog(metric.getKey()))
            .collect(Collectors.toMap(metric -> metric.getKey(), metric -> metric.getValue()));
        System.out.println("Collected metrics until this request: "+metricsToLog);
    }
    
    private boolean isMetricToLog(String metricName) {
        return (metricName.startsWith("gauge.response.") || metricName.startsWith("counter.status."));
    }
}
