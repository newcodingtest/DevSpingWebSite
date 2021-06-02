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
public class ReplyControllerTests {
	
	@Setter(onMethod_=@Autowired)
	private WebApplicationContext ctx;

	private MockMvc mvc;
	
	//모든 테스트 전에 가찌Mvc 패턴 적용 해서 테스트 가능
	@Before
	public void setUp() {
		this.mvc=MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	

	
}
