package vn.elca.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import vn.elca.training.dom.Group;

public interface IGroupRepository extends JpaRepository<Group, Long>, QueryDslPredicateExecutor<Group> {

}
