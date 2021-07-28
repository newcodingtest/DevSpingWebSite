package org.yoon.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
		private Long rno; // ��� ��ȣ
		private Long bno; // �� ��ȣ
		private String reply; //��� ����
		private String replayer; //��� �ۼ���
		private Date replyDate; //�ۼ���
		private Date updateDate; //������
		private char hide;    //��� ����/���������
		private Long parent; //������ �θ� ��۹�ȣ == rno
		private int deep;  //��� ����
		private int groupno; // ���󿡼� ǥ���Ǵ� ���� ��� ����
		
		
		//�ش� �Խñ� Ÿ��=>���� �� ��۸�� Ȯ�ν� ����
		private String title;
		private String type;
		
	
	
		
}
