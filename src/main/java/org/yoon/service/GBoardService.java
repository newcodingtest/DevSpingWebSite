package org.yoon.service;

import java.util.HashMap; 
import java.util.List;

import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;


public interface GBoardService {

	//게시글 등록
	public void register(BoardVO bno);
	//게시글 상세보기
	public BoardVO get(Long bno);
	//게시글 수정
	public int modify(BoardVO bno);
	//게시글 삭제
	public int delete(Long bno);
	//게시글 목록 조회
	public List<BoardVO> getList(Criteria cri);
	//페이징에 쓰일 글 전체 개수
	public int getTotal(Criteria cri);
	//글번호로 첨부파일 리스트 조회
	public List<BoardAttachVO> getAttachList(Long bno);
	//추천하기
	public void recommend(HashMap map);
	//추천여부 조회
	public int checkRecommend(HashMap map);
	//추천 취소
	public void cancelRecommend(HashMap map);
	//최신글 조회
	public List<BoardVO> getNewList();
	//베스트글 조회
	public List<BoardVO> getBestList();
}