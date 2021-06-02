package org.yoon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;

public interface BoardMapper {
	
	//단순 글 출력
	public List<BoardVO> getList();
	//글 페이징 출력
	public List<BoardVO> getListPaging(Criteria cri);
	//글 등록
	public void insert(BoardVO board);
	//글 등록[등록된 글번호 확인가능]
	public void insertSelectKey(BoardVO board);
	//글 조회
	public BoardVO read(Long bno);
	//글 삭제
	public int delete(Long bno);
	//글 수정
	public int update(BoardVO board);
	//페이징에 쓰일 글 전체 개수
	public int getTotal(Criteria cri);
	//조회수 증가
	public int visit(Long bno);
	//댓글수 증가
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount); 
	//좋아요 증가
	public int like(Long bno);
}
