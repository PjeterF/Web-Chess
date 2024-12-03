package com.example.WebChess.configuration.filters;

import com.example.WebChess.account.Account;
import com.example.WebChess.account.AccountRepository;
import com.example.WebChess.account.AccountService;
import com.example.WebChess.configuration.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final AccountRepository accountRepository;

    @Autowired
    public JWTFilter(JWTService jwtService, AccountRepository accountRepository) {
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestPath=request.getServletPath();
        if (requestPath.startsWith("/api/accounts/login") || requestPath.equals("/api/accounts") || requestPath.startsWith("/game")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader=request.getHeader("Authorization");
        String jwt;
        String username;
        if(authHeader==null || !authHeader.startsWith(("Bearer "))){
            response.setStatus(403);
            filterChain.doFilter(request, response);
            return;
        }
        jwt=authHeader.substring(7);
        username=jwtService.extractUsername(jwt);

        Optional<Account> accountOptional=accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            response.setStatus(403);
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(accountOptional.get(), accountOptional.get().getPassword(), accountOptional.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
