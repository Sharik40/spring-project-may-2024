package org.spring.cp.springprojectmay2024.filter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.spring.cp.springprojectmay2024.service.UserService;
import org.spring.cp.springprojectmay2024.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public final JwtUtil jwtUtil;

    public final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(authHeaderValue)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeaderValue.substring("Bearer ".length());

        try {
            if (jwtUtil.isTokenExpired(jwtToken)) {
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtUtil.extractUsername(jwtToken);

            if (StringUtils.isBlank(username)) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }

        filterChain.doFilter(request, response);

    }
}
