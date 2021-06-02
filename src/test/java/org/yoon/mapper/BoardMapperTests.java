package org.yoon.mapper;


import java.util.List;

import org.junit.Test; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.service.BoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//DB  CRUD 테스트
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

	@Setter(onMethod_=@Autowired)
	private BoardMapper mapper;
	
	/*
	 * @Test
	 *  public void getList(Criteria cri) {
	 * mapper.getListPaging(cri).forEach(board -> log.info(board)); }
	 */
	
	//글 목록 출력 테스트
	@Test
	 public void getList() {
	 mapper.getList().forEach(board -> log.info(board));
	 }
	
	//글 등록 테스트
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("테스트2 제목");
		board.setContent("테스트2 내용");
		board.setWriter("테스트2 작성자");
		
		mapper.insert(board);
		
		log.info(board);
	}
	
	//조회테스트
	@Test
	public void read() {
		Long bno=1L;
		
		BoardVO board = mapper.read(bno);
		log.info(board);
	}
	
	//삭제테스트
	@Test
	public void delete() {
		Long bno=2L;
		
		int check=mapper.delete(bno);
		log.info("==1이 나오면 삭제완료==: "+check);		
	}
	
	//수정테스트
	@Test
	public void update() {
		BoardVO board = new BoardVO();
		
		board.setBno(1L);
		board.setTitle("테스트1->윤주영으로 수정테스트");
		board.setContent("테스트1->윤주영으로 수정테스트");
		board.setWriter("윤주영");
		
		int check = mapper.update(board);
		log.info("==1이 나오면 수정완료==: "+check);
	}
	
	//페이징 테스트
	@Test
	public void testPaging() {
		
		Criteria cri = new Criteria();
		cri.setAmount(10);
		cri.setPageNum(3);
		List<BoardVO> list= mapper.getListPaging(cri);
		
		list.forEach(board -> log.info(board));
	}
	
	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("윤주영");
		cri.setType("TC");
		
		List<BoardVO>list=mapper.getListPaging(cri);
		list.forEach(board -> log.info(board));
	}
	
}
