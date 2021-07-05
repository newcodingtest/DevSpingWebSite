package org.yoon.mapper;

import java.util.HashMap; 
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;



public interface GBoardMapper {

	//게시글 목록 조회
	public List<BoardVO> getListPaging(Criteria cri);
	//게시글 등록
	public void insert(BoardVO Board);
	//글번호 확인 및 게시글 등록
	public void insertSelectKey(BoardVO Board);
	//게시글 보기
	public BoardVO read(Long bno);
	//게시글 삭제
	public int delete(Long bno);
	//게시글 수정
	public int update(BoardVO vo);
	//게시글 총 개수
	public int getTotal(Criteria cri);
	//조회수 증가
	public int getMoreVisit(Long bno);
	//게시글 추천
	public void recomend(HashMap map);
	//게시글 추천수 증가
	public int getMoreRecomend(Object bno);
	//게시글 추천수 감소
	public int reduceRecomend(Object bno);
	//추천여부 조회
	public int checkRecomend(HashMap map);
	//추천취소하기
	public void cancelRecomend(HashMap map);
	//최신글 가져오기
	public List<BoardVO> getNewList();
	//베스트글 가져오기
	public List<BoardVO> getBestList();
	
}