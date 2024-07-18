package com.sparta.audumbla.audumblaworldjpa.config;

import com.sparta.audumbla.audumblaworldjpa.entities.ApiKey;
import com.sparta.audumbla.audumblaworldjpa.repositories.ApiKeyRepository;
import com.sparta.audumbla.audumblaworldjpa.service.APIService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CowInterceptor implements HandlerInterceptor {
    private final APIService apiService;
    public CowInterceptor(APIService apiService) {
        this.apiService = apiService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getParameter("apiKey");
        if (apiKey == null) {
            if(request.getMethod().equals("GET")){
                return true; // Allow GET requests
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
        ApiKey key = apiService.getApiKeyByApiKey(apiKey);
        if (key == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        assert key != null;
        request.setAttribute("apiKey", key.getRole());
        return true;
    }
}
