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
	//��۵��
	//consumes: ��û ������ Ÿ�� ���� produces: ��û ����� ��ȯ ������ Ÿ�� ����
	//@RequestBody �� �̿��Ͽ� JSON �����͸� ReplyVo Ÿ������ ��ȯ
	@PostMapping(value = "/new",
			consumes = "application/json", //json ��û�� ó��
			produces = {MediaType.TEXT_PLAIN_VALUE}) ////�ؽ�Ʈ Ÿ�Ը� ����
	public ResponseEntity<String> create(@RequestBody ReplyVO vo, Model model){
		
		log.info("ReplyVO:  "+vo);
		
		int cnt = service.register(vo);
		log.info("��� ��� 1�� ������ ����!!:   " +cnt);
		
		//��� �����ϸ� http �����ڵ� 200 �ƴϸ� ����
		return cnt==1 ? new ResponseEntity<>("success", HttpStatus.OK) 
				 	  : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	//Ư�� �Խù� ��� Ȯ��
	@GetMapping(value = "/pages/{bno}/{page}",
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno){
		log.info("��� ����Ʈ======================");
		Criteria cri = new Criteria(page,10);
		log.info(cri);
		log.info(service.getList(cri, bno));
		return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
	}
	
	//��� ��ȸ
	@GetMapping(value = "/{rno}", 
				produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
							MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		log.info("��� ��ȸ: " + rno);
		
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	//��ۻ���
	@PreAuthorize("principal.username == #vo.replayer")
	@DeleteMapping(value = "/{rno}")
	public ResponseEntity<String> remove(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno){
		log.info("get: " + rno);
		
		return service.remove(rno) ==1 
			? new ResponseEntity<>("success", HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	//��� ����
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
			value = "/{rno}",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo,@PathVariable("rno") Long rno){
			vo.setBno(rno);
			log.info("�����ϴ� ��� ��ȣ: " +rno);
			log.info("��� ����: " +vo);
			
			return service.modify(vo) == 1
					? new ResponseEntity<>("success", HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	
}
