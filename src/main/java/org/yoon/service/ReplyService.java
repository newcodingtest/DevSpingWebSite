package org.yoon.service;

import java.util.List;
import java.util.Map;

import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyPageDTO;
import org.yoon.domain.ReplyVO;

public interface ReplyService {
	
	//댓글 등록
	public int register(ReplyVO reply);
	//댓글 조회
	public ReplyVO get(Long rno);
	//댓글 삭제
	public int remove(Long rno);
	//댓글 수정
	public int modify(ReplyVO reply);
	//댓글 리스트 출력+페이징 적용
	public ReplyPageDTO getList(Criteria cri,Long rno);
	//내가쓴 e댓글 출력
	public List<ReplyVO> getListByUser(Criteria cri,String userid);
	//게시글 번호로 댓글 삭제
	public int deleteBno(Long bno);
	//내가 쓴 댓글 수 가져오기
	public int getTotalByUser(String userid);

}
