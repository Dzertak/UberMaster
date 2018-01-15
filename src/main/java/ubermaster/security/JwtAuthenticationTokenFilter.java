package ubermaster.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import ubermaster.entity.security.JwtUser;
import ubermaster.security.service.JwtTokenUtil;
import ubermaster.security.service.JwtUserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ubermaster.security.SecurityConstants.HEADER_STRING;
import static ubermaster.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String tokenHeader = HEADER_STRING;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(this.tokenHeader);
        String username = null;
        String authToken = null;

        if (requestHeader != null && requestHeader.startsWith(TOKEN_PREFIX)) {
            authToken = requestHeader;
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("an error occured during getting username from token");
            } catch (ExpiredJwtException e) {
                throw new RuntimeException("the token is expired and not valid anymore");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            JwtUser jwtUser = this.userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, jwtUser)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}