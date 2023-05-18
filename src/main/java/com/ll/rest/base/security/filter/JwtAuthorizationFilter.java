package com.ll.rest.base.security.filter;

import com.ll.rest.base.jwt.JwtProvider;
import com.ll.rest.boundedContext.member.entity.Member;
import com.ll.rest.boundedContext.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Map;

import static org.springframework.security.core.context.SecurityContextHolder.*;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 Authorization 값을 가져온다.
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null) {
            String token = bearerToken.substring("Bearer ".length());

            if (jwtProvider.verify(token)) {
                Map<String, Object> claims = jwtProvider.getClaims(token);
                Long id = (Long) claims.get("id");

                Member member = memberService.findById(id).orElseThrow();

                forceAuthentication(member);
            }
        }

        filterChain.doFilter(request, response);
    }

    // 강제로 로그인 처리하는 메소드
    private void forceAuthentication(Member member) {
        User user = new User(member.getUsername(), member.getPassword(), member.getGrantedAuthorities());

        // 스프링 시큐리티 객체에 저장할 authentication 객체를 생성
        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        user,
                        null,
                        member.getGrantedAuthorities()
                );

        // 스프링 시큐리티 내에 우리가 만든 authentication 객체를 저장할 context 생성
        SecurityContext context = createEmptyContext();
        // context에 authentication 객체를 저장
        context.setAuthentication(authentication);
        // 스프링 시큐리티에 context를 등록
        setContext(context);
    }
}
