package com.prafful.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.prafful.demo.dao.QuestionDao;
import com.prafful.demo.model.Question;
import com.prafful.demo.model.QuestionWrapper;
import com.prafful.demo.model.Response;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
			return new ResponseEntity<>(questionDao.findByCategory(category) ,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<String> addQuestion(Question question) {
		
		try {
			questionDao.save(question);
			return new ResponseEntity<>("success",HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
		try {
			List<Integer> quesList = questionDao.findRandomQuestionsByCategory(categoryName,numQuestions);
			return new ResponseEntity<>(quesList,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsId) {
		try {
			List<QuestionWrapper> questionsList = new ArrayList<>();
			for(int id:questionsId) {
				Question question = questionDao.findById(id).get();
				QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
				questionsList.add(questionWrapper);
			}
			return new ResponseEntity<>(questionsList,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<Integer> getScore(List<Response> responses) {
		try {
			int right=0;
			for(Response response : responses) {
				if(questionDao.findById(response.getId()).get().getRightAnswer().equals(response.getResponse())) {
					right++;
				}
			}
			return new ResponseEntity<>(right,HttpStatus.OK);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(-1,HttpStatus.BAD_REQUEST);
	}

}
