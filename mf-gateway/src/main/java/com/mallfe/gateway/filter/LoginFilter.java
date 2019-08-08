package com.mallfe.gateway.filter;

import com.mallfe.common.utils.JwtUtils;
import com.mallfe.gateway.cart.config.FilterProperties;
import com.mallfe.gateway.cart.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Qin PengCheng
 * @date 2018/6/13
 */
@Component
@Slf4j
@EnableConfigurationProperties(value = {JwtProperties.class, FilterProperties.class})
public class LoginFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    @Autowired
    private FilterProperties filterProperties;
    @Autowired
    private JwtProperties jwtProperties;

    private Boolean isAllowPath(String uri) {
        List<String> allowPaths = filterProperties.getAllowPaths();
        boolean isFind = false;
        for (String allowPath : allowPaths) {
            if (uri.startsWith(allowPath)) {
                isFind = true;
                break;
            }
        }
        return isFind;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        return ! isAllowPath(requestURI);

    }

    @Override
    public Object run() throws ZuulException {
        //获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
//        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        String requestUrl = request.getServletPath();
        String ipAddr = request.getRemoteAddr();
        log.info(String.format("%s [%s] >>> %s",ipAddr, request.getMethod(), requestUrl));
        String token = ctx.getRequest().getHeader("Authorization");
        try {
            JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());

        }
        return null;
    }

}
