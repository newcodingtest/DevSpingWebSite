package org.yoon.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
//페이징 처리
public class Criteria {
	
	private int pageNum; //페이지 번호
	private int amount;  //한 페이지당 보여줄 글의 총 개수
	
	private String type; //검색 조건
	private String keyword; //검색내용
	

	//페이지 번호 1, 페이지 당 글 개수 10개
	public Criteria() {
		this(1,10);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		
		return type == null? new String[] {}:type.split("");
	}
	
	//웹페이지에서 항상 파라미터값 유지, 인코딩 자동 설정됨
	public String getListLink() {

		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());

		return builder.toUriString();

	}

}
