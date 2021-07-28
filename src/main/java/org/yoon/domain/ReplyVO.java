package org.yoon.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
		private Long rno; // 댓글 번호
		private Long bno; // 글 번호
		private String reply; //댓글 내용
		private String replayer; //댓글 작성자
		private Date replyDate; //작성일
		private Date updateDate; //수정일
		private char hide;    //댓글 공개/비공개여부
		private Long parent; //대댓글의 부모 댓글번호 == rno
		private int deep;  //댓글 깊이
		private int groupno; // 웹상에서 표현되는 실제 댓글 순서
		
		
		//해당 게시글 타입=>내가 쓴 댓글목록 확인시 사용됨
		private String title;
		private String type;
		
	
	
		
}
