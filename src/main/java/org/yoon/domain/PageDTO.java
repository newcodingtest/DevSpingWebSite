package org.yoon.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

	private int startPage; //시작 페이지 번호(양을 표현)
	private int endPage; //끝 페이지 번호(양을 표현)
	private boolean prev; //이전 페이지
	private boolean next; //다음 페이지
	
	private int total; //전체 페이지 개수
	private Criteria cri; 
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
	
		//기본 값 -> endPage:1 startPage: -8 
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		this.startPage = this.endPage - 9;
		
		//총 글 갯수 기준으로 끝나는 페이지 번호
		//mapper로 구한 총 글 개수->total 적용->실제 끝번호 생성
		int realEnd = (int) (Math.ceil(total * 10.0) / cri.getAmount());
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
	
	
}
