package com.example.studentstay.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Set<String> WHITE_LIST = Set.of(
            "/login", "/logout",
            "/css/", "/js/",
            "/assignmentForm",
            "/assignments",
            "/transferForm",
            "/assignmentTransfer"
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  r = (HttpServletRequest) req;
        HttpServletResponse s = (HttpServletResponse) resp;

        String path = r.getServletPath();

        boolean allowed = WHITE_LIST.stream().anyMatch(path::startsWith);

        if (allowed) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = r.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("employee") != null;

        if (loggedIn) {
            chain.doFilter(req, resp);
        } else {
            s.sendRedirect(r.getContextPath() + "/login");
        }
    }
}