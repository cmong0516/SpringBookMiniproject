package hello.hellospring.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotEmpty(message = "올바른 아이디를 입력해주세요.")
    private String id;
    @NotEmpty(message = "올바른 비밀번호를 입력해주세요.")
    private String pw;
}
