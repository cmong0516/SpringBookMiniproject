package hello.hellospring.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberForm {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String pw;


}
