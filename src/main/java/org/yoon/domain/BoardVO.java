package org.yoon.domain;

import java.util.Date; 
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private Long bno; // 글 번호
	private String title; //제목
	private String content; // 내용
	private String writer; //작성자
	private Date regdate; // 등록 일자
	private Date updatedate; //수정 일자
	private int replycnt;// 댓글 수
	private int visit; // 방문 수
	private int recomend; //추천 수
	private String type; //게시판 이름 
	
	private List<ReplyVO>replyList;//댓글 리스트
	
	private List<BoardAttachVO>attachList;
	
	

}
