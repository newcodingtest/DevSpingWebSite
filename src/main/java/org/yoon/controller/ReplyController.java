package org.yoon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyPageDTO;
import org.yoon.domain.ReplyVO;
import org.yoon.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies")
@AllArgsConstructor
@Log4j
@RestController 
public class ReplyController {

	private ReplyService service;
	
	@PreAuthorize("isAuthenticated()")
	//댓글등록
	//consumes, produces >> JSON 방식식의 데이터만 처리하는 속성
	//@RequestBody 를 이용하여 JSON 데이터를 ReplyVo 타입으로 변환
	@PostMapping(value = "/new",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo, Model model){
		
		log.info("ReplyVO:  "+vo);
		
		int cnt = service.register(vo);
		log.info("댓글 등록 1이 나오면 성공!!:   " +cnt);
		
		//댓글 성공하면 http 상태코드 200 아니면 에러
		return cnt==1 ? new ResponseEntity<>("success", HttpStatus.OK) 
				 	  : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	//특정 게시물 댓글 확인
	@GetMapping(value = "/pages/{bno}/{page}",
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno){
		log.info("댓글 리스트======================");
		Criteria cri = new Criteria(page,10);
		log.info(cri);
		log.info(service.getList(cri, bno));
		return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
	}
	
	//댓글 조회
	@GetMapping(value = "/{rno}", 
				produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
							MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		log.info("댓글 조회: " + rno);
		
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	//댓글삭제
	@PreAuthorize("principal.username == #vo.replayer")
	@DeleteMapping(value = "/{rno}")
	public ResponseEntity<String> remove(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno){
		log.info("get: " + rno);
		
		return service.remove(rno) ==1 
			? new ResponseEntity<>("success", HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	//댓글 수정
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
			value = "/{rno}",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo,@PathVariable("rno") Long rno){
			vo.setBno(rno);
			log.info("수정하는 댓글 번호: " +rno);
			log.info("댓글 수정: " +vo);
			
			return service.modify(vo) == 1
					? new ResponseEntity<>("success", HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	
}
