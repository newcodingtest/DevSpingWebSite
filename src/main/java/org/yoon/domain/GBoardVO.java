
package org.yoon.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GBoardVO {

	private long gno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updatedate;
	private int replycnt;
	private int visit;
	private int recommend;
	
	private List<GBoardAttachVO> attachList;
}