package hello.hellospring.domain;

import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String id, String pw) {
        Optional<Member> byLoginId = memberRepository.findById(id);
        Member member = byLoginId.get();

        return memberRepository.findById(id).filter(m -> m.getPw().equals(pw)).orElse(null);
    }
}

//문제점 : 누군가 브라우저에서 쿠키값을 변경 가능하다.
