package org.yoon.mapper;


import java.util.List; 

import org.junit.Test; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.MemberVO;
import org.yoon.service.BoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//DB  CRUD 테스트
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class MemberMapperTests {

	@Setter(onMethod_=@Autowired)
	private MemberMapper mapper;
	
	
	//회원 출력test
	@Test
	 public void getList() {
		MemberVO vo= mapper.read("");
		log.info(vo);
		vo.getAuthList().forEach(authVO -> log.info(authVO));
	 }
	
	//내가 작성한 최신글 출력 test
	@Test
	public void getAllBoard() {
		
		String userid="admin90";
		
		List<BoardVO> list= mapper.getAllboard(userid);
		list.forEach(board -> log.info(board));
	}

}
