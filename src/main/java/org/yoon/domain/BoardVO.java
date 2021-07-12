package org.yoon.domain;

import java.util.Date; 
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private Long bno; // �� ��ȣ
	private String title; //����
	private String content; // ����
	private String writer; //�ۼ���
	private Date regdate; // ��� ����
	private Date updatedate; //���� ����
	private int replycnt;// ��� ��
	private int visit; // �湮 ��
	private int recomend; //��õ ��
	private String type; //�Խ��� �̸� 
	
	private List<ReplyVO>replyList;//��� ����Ʈ
	
	private List<BoardAttachVO>attachList;
	
	

}
