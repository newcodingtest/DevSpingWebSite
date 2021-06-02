package org.yoon.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yoon.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


//페이지에서 crud 테스트
@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardServiceTests {
	
	@Setter(onMethod_=@Autowired)
	private BoardService service;

	//글 등록 테스트
	@Test
	public void testRegister() {
		
		log.info("====등록 테스트 시작=====");
		BoardVO board = new BoardVO();
		board.setTitle("글 등록 테스트2");
		board.setContent("글 등록 테스트2");
		board.setWriter("글 등록 테스트2");
		
		service.register(board);
		
		log.info("테스트에서 등록된 글 번호는???>>>  " +board.getBno());
	}

	//글 조회 테스트
	@Test
	public void testGet() {
			
		log.info("====조회 테스트 시작=====");
		
		Long bno = 3L;
		
		log.info("조회 테스트 결과는 >>> "+ service.get(bno));
		}
	
	//글 삭제 테스트
	@Test
	public void testRemove() {
		
		log.info("====삭제 테스트 시작=====");
		
		Long bno = 3L;
		int check=service.remove(bno);
		
		log.info("1이면 삭제 테스트 성공!: "+check);
	}
	
	//글 수정 테스트
	@Test
	public void testModify() {
		
		BoardVO board = service.get(1L);
		
		if(board==null) {
			return;
		}
		board.setTitle("글 수정 테스트1");
		board.setContent("글 수정 테스트1");
		board.setWriter("글 수정 테스트1");
		board.setBno(1L);
		
		service.modify(board);
	}
	
	
	
	
}
