package org.yoon.service;

import java.util.List;

import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.GBoardVO;

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
//	//페이징에 따른 글 출력
//	public List<BoardVO> getListPaging(Criteria cri);
	//페이징에 쓰일 글 전체 개수
	public int getTotal(Criteria cri);
	//조회수
	public int visit(Long bno);
	//해당 글의 첨부파일 가져오기
	public List<BoardAttachVO>getAttachList(Long bno);
	//좋아요
	int Like(Long bno);
	//최신글 조회
	public List<GBoardVO> getNewList();
		//베스트글 조회
	public List<GBoardVO> getBestList();
}
	
