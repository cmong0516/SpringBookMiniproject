package hello.hellospring.controller;

import hello.hellospring.MemberService;
import hello.hellospring.domain.LoginForm;
import hello.hellospring.domain.LoginService;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.MemberForm;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/miniproject")
@RequiredArgsConstructor
@Controller
public class WebController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final MemberRepository memberRepository;

    //MemberController 가 생성될때 MemberService 타입의 memberService가 들어감.
//    @Autowired
    //@Autowired를 이용하면 memberService를 연결시켜준다.
//    public WebController(MemberService memberService, LoginService loginService) {
//        this.memberService = memberService;
//        this.loginService = loginService;
//    }

    @GetMapping("/home")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null) {
            return "/";
        }

        Member loginMember = memberRepository.findBy_Id(memberId);
        if (loginMember == null) {
            return "/";
        }

        model.addAttribute("member", loginMember);
        return "home";
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
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
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

        //쿠키에 시간 정보를 주지 않으면 세션쿠키.
        //브라우저 종료시까지 유지.
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.get_Id()));
        response.addCookie(idCookie);


        return "redirect:home";
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

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
