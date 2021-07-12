package org.yoon.mapper;

import java.util.HashMap;  
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;


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
	//게시글 추천
	public void recommend(HashMap map);
	//게시글 추천수 증가
	public int getMoreRecommend(Object bno);
	//게시글 추천수 감소
	public int reduceRecommend(Object bno);
	//추천여부 조회
	public int checkRecommend(HashMap map);
	//추천취소하기
	public void cancelRecommend(HashMap map);
	//최신글 조회
	public List<BoardVO> getNewList();
	//베스트글 조회
	public List<BoardVO> getBestList();

	//내가 쓴 글(자유,갤러리) 가져오기
		public List<BoardVO> getNewboard(String userid);
		//내가 쓴 글(자유,갤러리) 가져오기
		public List<ReplyVO> getNewReply(String userid);
		//내가 쓴 글 개수 출력
		public int getAllboardCount(String userid);
		//내가 쓴 댓글 개수 출력
		public int getAllReplyCount(String userid);
	
}
