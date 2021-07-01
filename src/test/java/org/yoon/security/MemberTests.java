package org.yoon.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
@Log4j
public class MemberTests {

	@Setter(onMethod_= @Autowired)
	private PasswordEncoder pwencoder;
	
	@Setter(onMethod_= @Autowired)
	private DataSource ds;
	
	@Test
	public void testInsertMember() throws SQLException {
		
		String sql = "insert into member(userid, userpw, username) values(?,?,?)";
		
		for(int i = 0; i< 100; i++) {
			Connection con = null;
			PreparedStatement psmt = null;
			
			try {
				con = ds.getConnection();
				psmt = con.prepareStatement(sql);
				psmt.setString(2, pwencoder.encode("pw"+i));
				
				if(i<80) {
					psmt.setString(1, "user"+i);
					psmt.setString(3, "일반사용자"+i);
				}else if(i<90) {
					psmt.setString(1, "manager"+i);
					psmt.setString(3, "운영자"+i);
				}else{
					psmt.setString(1, "admin"+i);
					psmt.setString(3, "관리자"+i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(psmt!=null) {
					psmt.close();
				}
				if(con!=null) {
					con.close();
				} 
			}
		}
	}
	
}
