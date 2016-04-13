package com.github.leoluz.dashboard.web

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED


class OauthFilter implements Filter {

    private String consumerSecret

    @Override
    void init(FilterConfig filterConfig) throws ServletException {
        consumerSecret = filterConfig.getInitParameter("consumerSecret")
    }

    @Override
    void destroy() {
    }

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request
        HttpServletResponse res = (HttpServletResponse) response

        OauthVerifier oauthVerifier = new OauthVerifier(req, consumerSecret)
        if (oauthVerifier.hasValidSignature()) {
            chain.doFilter(request, response)
        } else {
            res.sendError(HTTP_UNAUTHORIZED, "Invalid Oauth signature!")
        }
    }
}
