package com.sparta.onetwoday.service;

import com.sparta.onetwoday.dto.LoginRequestDto;
import com.sparta.onetwoday.dto.SignupRequestDto;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.entity.UserRoleEnum;
import com.sparta.onetwoday.jwt.JwtUtil;
import com.sparta.onetwoday.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.sparta.onetwoday.entity.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException(DUPLICATE_USER.getMessage());
        }
        found = userRepository.findByNickname(nickname);
        if (found.isPresent()) {
            throw new IllegalArgumentException(DUPLICATE_NICKNAME.getMessage());
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException(ADMIN_PASSWORD_IS_INCORRECT.getMessage());
            }
            role = UserRoleEnum.ADMIN;
        }

        //닉네임이 공백포함인지 확인
        if(nickname.replaceAll(" ","").equals("")) {
            throw new IllegalArgumentException(NICKNAME_WITH_SPACES.getMessage());
        }

        User user = new User(username, nickname, password, role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException(COULD_NOT_FOUND_USER.getMessage())
        );
        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw  new IllegalArgumentException(PASSWORDS_DO_NOT_MATCH.getMessage());
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return jwtUtil.createToken(user.getUsername(), user.getRole());
    }

}
