package com.example.bookbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookbackend.domain.Book;
import com.example.bookbackend.domain.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	@Transactional // 서비스 함수가 종료될 때 commit, rollback 트랜잭션 관리
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	@Transactional(readOnly = true) // readOnly true 하면 JPA 변경감지라는 내부 기능 활성화 x, update 시의 정합성을 유지, insert의 팬텀 현상은 막지 못함
	public Book findById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요"));
	}
	
	@Transactional(readOnly = true)
	public List<Book> findByAll() {
		return bookRepository.findAll();
	}
	
	@Transactional
	public Book update(Long id, Book book) {
		// 더티체킹
		Book bookEntity = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요")); // 영속화(book 오브젝트) -> 영속성 컨텍스트 보관
		bookEntity.setTitle(book.getTitle());
		bookEntity.setAuthor(book.getAuthor());
		return bookEntity;
	} // 함수 종료 -> 트랜잭션 종료 => 영속화 되어있는 데이터를 DB로 갱신(flush) -> commit -----> 더티체킹
	
	@Transactional
	public String delete(Long id) {
		bookRepository.deleteById(id); // 오류가 터지면 알아서 exception을 탐
		return "ok";
	}
}
