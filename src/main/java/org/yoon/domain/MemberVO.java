package org.yoon.domain;

import java.util.Date; 
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

	private String userid;
	private String userpw;
	private String username;
	private String useremail;
	private boolean enabled;
	private String naverLogin;
	private String introduce;
	
	private Date regDate;
	private Date updateDate;
	private List<AuthVO> authList; //���� ����
	private List<BoardAttachVO>attachList; //����� ������ ����
}
