package overcloud.blog.usecase.user.login;

import jakarta.servlet.http.HttpServletResponse;

public interface Login {
    LoginResponse login(LoginRequest request, HttpServletResponse response);
}
