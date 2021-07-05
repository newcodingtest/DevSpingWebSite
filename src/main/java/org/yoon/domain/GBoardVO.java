
package org.yoon.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GBoardVO {

	private long gno;
	private String g_title;
	private String g_content;
	private String g_writer;
	private Date g_regdate;
	private Date g_updatedate;
	private int g_replycnt;
	private int g_visit;
	private int g_recommend;
	private String g_type; //�Խ��� �̸� 
	
	private List<GBoardAttachVO> attachList;
}