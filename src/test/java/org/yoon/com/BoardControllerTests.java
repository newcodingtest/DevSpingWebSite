package org.yoon.com;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.yoon.mapper.BoardMapper;
import org.yoon.service.BoardServiceTests;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration // 컨트롤러 테스트용 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
						"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class BoardControllerTests {
	
	@Setter(onMethod_=@Autowired)
	private WebApplicationContext ctx;

	private MockMvc mvc;
	
	//모든 테스트 전에 가찌Mvc 패턴 적용 해서 테스트 가능
	@Before
	public void setUp() {
		this.mvc=MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	//글 리스트 출력 테스트
	@Test
	public void testList() throws Exception {
		
		log.info(
				mvc.perform(MockMvcRequestBuilders.get("/board/list"))
				.andReturn()
				.getModelAndView()
				.getModelMap());
	}
	
	//글 등록
	@Test
	public void testRegister() throws Exception {
		
		String resultPage = mvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "컨트롤러 테스트1")
				.param("content", "컨트롤러 테스트1")
				.param("writer", "윤주영")).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
	//글 조회
	@Test
	public void testGet() throws Exception {
		
		log.info(mvc.perform(MockMvcRequestBuilders
				.get("/board/get")
				.param("bno", "10"))
				.andReturn()
				.getModelAndView().getModelMap());
	}
	
	//글 수정 테스트
	@Test
	public void testModify() throws Exception {
		
		String resultPage = mvc.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("bno","1")
				.param("title","수정 테스트 제목 ")
				.param("content","수정테스트 내용")
				.param("writer","수정 윤주영"))
				.andReturn().getModelAndView().getViewName();
				
		log.info(resultPage);
		
	}
	
	//글 삭제 테스트
	@Test
	public void testRemove() throws Exception {
		
		String resultPage = mvc.perform(MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "1"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
				
	}
	
	//글 리스트 페이징 테스트
	@Test
	public void testListPaging() throws Exception {
		log.info(mvc.perform(
			MockMvcRequestBuilders.get("/board/list")
			.param("pageNum", "2")
			.param("amount", "50"))
			.andReturn().getModelAndView().getModelMap());
	}
	
}
