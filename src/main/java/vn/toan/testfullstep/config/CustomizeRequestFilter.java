package vn.toan.testfullstep.config;

import java.io.IOException;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import vn.toan.testfullstep.service.JwtService;
import vn.toan.testfullstep.service.UserServiceDetail;
import vn.toan.testfullstep.common.TokenType;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Slf4j(topic = "Customize-request-filter")
@RequiredArgsConstructor
@Component
@EnableMethodSecurity(prePostEnabled = true)
public class CustomizeRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserServiceDetail serviceDetail;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //   check quyen truoc
        //    @Component
        //   NOSONAR  public class RoleBasedAuthorizationFilter extends OncePerRequestFilter {
        //        private static final Map<String, String> URL_ROLE_MAPPING = Map.of(
        //                "/user/list", "ADMIN",
        //                "/user/detail", "USER",
        //                "/user/settings", "ADMIN",
        //                "/user/profile", "USER"
        //        );
        //        @Override
        //        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        //                                        FilterChain filterChain) throws ServletException, IOException {
        //            String requestUrl = request.getRequestURI();
        //            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //            // Kiểm tra xem request URL có trong danh sách ánh xạ không
        //            for (Map.Entry<String, String> entry : URL_ROLE_MAPPING.entrySet()) {
        //                if (requestUrl.startsWith(entry.getKey())) {
        //                    String requiredRole = entry.getValue();
        //                    // Kiểm tra quyền của người dùng
        //                    if (auth == null || auth.getAuthorities().stream()
        //                            .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(requiredRole))) {
        //                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền truy cập: " + requiredRole);
        //                        return;
        //                    }
        //                }
        //            }
        //            filterChain.doFilter(request, response);
        //        }
        //    }
        log.info("{}  {}", request.getMethod(), request.getRequestURI());
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            log.info("token: {}...", token.substring(0, 20));
            String username;
            try {
                username = jwtService.extractUsername(token, TokenType.ACCESS_TOKEN);
                log.info("username: {}", username);
            } catch (AccessDeniedException e) {
                log.info(e.getMessage());
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(errorResponse(e.getMessage()));
                return;
            }

            UserDetails user = serviceDetail.loadUserByUsername(username);
            //  Tạo đối tượng xác thực và thiết lập vào SecurityContext
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private String errorResponse(String message) {
        try {
            ErrorResponse error = new ErrorResponse();
            error.setTimestamp(new Date());
            error.setError("Forbidden");
            error.setStatus(HttpServletResponse.SC_FORBIDDEN);
            error.setMessage(message);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(error);
        } catch (Exception e) {
            return ""; // Return an empty string if serialization fails
        }
    }

    @Setter
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private class ErrorResponse {

        Date timestamp;
        int status;
        String error;
        String message;
    }
}
