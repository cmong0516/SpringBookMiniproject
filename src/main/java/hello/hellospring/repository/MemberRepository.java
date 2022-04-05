package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository {

    Member save(Member member);

    Member findBy_Id(Long _id);

    Optional<Member> findById(String id);

    List<Member> findAll();

    void deleteOne(Member member);
}
