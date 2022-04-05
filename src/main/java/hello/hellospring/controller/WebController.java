package hello.hellospring.controller;

import hello.hellospring.MemberService;
import hello.hellospring.domain.LoginForm;
import hello.hellospring.domain.LoginService;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.MemberForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/miniproject")
@Controller
public class WebController {

    private final MemberService memberService;
    private final LoginService loginService;

    //MemberController 가 생성될때 MemberService 타입의 memberService가 들어감.
    @Autowired
    //@Autowired를 이용하면 memberService를 연결시켜준다.
    public WebController(MemberService memberService, LoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;
    }

    @GetMapping("/best")
    public String best() {
        return "best";
    }

    @GetMapping("/best2")
    public String best2() {
        return "best2";
    }

    @GetMapping("/complit")
    public String complit() {
        return "complit";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm form) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        Member loginMember = loginService.login(form.getId(), form.getPw());

        log.info("login? {}", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        //로그인 성공 처리


        return "redirect:/";
    }


    @GetMapping("/rentbook")
    public String rentbook() {
        return "rentbook";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/signin")
    public String signin(@ModelAttribute("memberForm") MemberForm form) {
        return "signin";
    }

    @PostMapping(value = "/signin")
    public String create(@Valid @ModelAttribute MemberForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signin";
        }


        Member member = new Member();
        member.setName(form.getName());
        member.setId(form.getId());
        member.setPw(form.getPw());
        memberService.join(member);


        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping(value = "/delete")
    public String delete(Member member) {
        member.set_Id(2L);
        member.setId("송광섭");
        member.setName("송광섭");
        member.setPw("1111");
        memberService.DeleteOne(member);
        return "redirect:/";
    }
}
