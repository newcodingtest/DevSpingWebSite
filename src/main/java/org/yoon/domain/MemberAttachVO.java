package org.yoon.domain;

import lombok.Data;

@Data
public class MemberAttachVO {
	
	private String fileName;
	private String uploadPath;
	private String uuid;
	private boolean fileType;
	
	private String userid;

}