package ua.org.ubts.applications.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import ua.org.ubts.applications.service.StudentFilesTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StudentFilesDownloadFilter extends OncePerRequestFilter {

    private StudentFilesTokenService studentFilesTokenService;

    public StudentFilesDownloadFilter(StudentFilesTokenService studentFilesTokenService) {
        this.studentFilesTokenService = studentFilesTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (!studentFilesTokenService.verifyToken(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            chain.doFilter(request, response);
        }
    }

}
