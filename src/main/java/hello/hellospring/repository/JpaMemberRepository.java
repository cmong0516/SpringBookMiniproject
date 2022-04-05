package hello.hellospring.repository;


import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findBy_Id(Long _id) {
        Member member = em.find(Member.class, _id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findById(String id) {
        List<Member> result = em.createQuery("select m from member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
        return result.stream().findAny();
    }
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from member m", Member.class)
                .getResultList();
    }

    @Override
    public void deleteOne(Member member) {
        Member member1 = em.find(Member.class, member.get_Id());
        em.remove(member1);

    }

}
