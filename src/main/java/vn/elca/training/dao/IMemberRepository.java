package vn.elca.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import vn.elca.training.dom.Member;

public interface IMemberRepository extends JpaRepository<Member, String>, QueryDslPredicateExecutor<Member> {

}
