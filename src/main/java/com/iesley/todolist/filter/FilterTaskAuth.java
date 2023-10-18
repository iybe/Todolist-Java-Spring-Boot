package com.iesley.todolist.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.iesley.todolist.user.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        var servletPath = request.getServletPath();
        var method = request.getMethod();
        if (servletPath.equals("/users/") && !method.equals("DELETE")) {
            filterChain.doFilter(request, response);
            return;
        }

        var basicAuth = request.getHeader("Authorization");
        if (basicAuth == null || !basicAuth.startsWith("Basic ")) {
            response.sendError(401);
            return;
        }

        var base64Credentials = basicAuth.substring("Basic ".length());

        var credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials)).split(":");

        var username = credentials[0];
        var password = credentials[1];

        var user = this.userRepository.findByUsername(username);
        if (user == null) {
            response.sendError(401);
            return;
        }

        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (!passwordVerify.verified) {
            response.sendError(401);
            return;
        }

        request.setAttribute("userId", user.getId());

        filterChain.doFilter(request, response);
    }

    
    
}
