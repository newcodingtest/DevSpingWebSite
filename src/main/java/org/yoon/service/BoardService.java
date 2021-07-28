package org.yoon.service;

import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;


public interface BoardService {
	
	//글 등록
	public void register(BoardVO board);
	//글 조회
	public BoardVO get(Long bno);
	//글 삭제
	public int remove(Long bno);
	//글 수정
	public int modify(BoardVO board);
	//글 리스트 출력+페이징 적용
	public List<BoardVO> getList(Criteria cri);
	//내가쓴 글 출력
	public List<BoardVO> getListByUser(Map<String, Object> map);
//	//페이징에 따른 글 출력
//	public List<BoardVO> getListPaging(Criteria cri);
	//페이징에 쓰일 글 전체 개수
	public int getTotal(Criteria cri);
	//페이징에 쓰일 유저 가 쓴 전체 개수
	public int getTotalByUser(String userid);
	//조회수
	public int visit(Long bno);
	//해당 글의 첨부파일 가져오기
	public List<BoardAttachVO>getAttachList(Long bno);
	//최신글 조회
	public List<BoardVO> getNewList();
		//베스트글 조회
	public List<BoardVO> getBestList();
	//추천하기
	public void recommend(HashMap map);
	//추천여부 조회
	public int checkRecommend(HashMap map);
	//추천 취소
	public void cancelRecommend(HashMap map);
	//내가 쓴 글(자유,갤러리) 가져오기
	public List<BoardVO> getNewboard(String userid);
		//내가 쓴 글(자유,갤러리) 가져오기
	public List<ReplyVO> getNewReply(String userid);
		//내가 쓴 글 개수 출력
	public int getAllboardCount(String userid);
		//내가 쓴 댓글 개수 출력
	public int getAllReplyCount(String userid);
}
	
