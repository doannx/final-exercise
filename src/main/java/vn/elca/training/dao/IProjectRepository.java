package vn.elca.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import vn.elca.training.dom.Project;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long>, QueryDslPredicateExecutor<Project> {
}
