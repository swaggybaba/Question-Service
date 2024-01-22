package com.prafful.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prafful.demo.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>{
	
	public List<Question> findByCategory(String category);
	
	@Query(value = "select * from question q where q.category = :category order by random() limit :numQ", nativeQuery = true)
	public List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
