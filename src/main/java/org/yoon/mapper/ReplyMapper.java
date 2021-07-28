package org.yoon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;

public interface ReplyMapper {
	//회원이 쓴 댓글 출력
	public List<ReplyVO> getListByUser(@Param("cri") Criteria cri,@Param("userid") String userid);
	//댓글 등록	
	public int insert(ReplyVO vo);
	//댓글 조회
	public ReplyVO read(Long rno);
	//댓글 삭제
	public int delete(Long rno);
	//댓글 수정
	public int update(ReplyVO reply);
	//두개이상 데이터 파라미터로 전 1.map 2.별도객체 3.param
	public List<ReplyVO> getListPaging(@Param("cri") Criteria cri,@Param("bno") Long bno);
	//댓글 숫자 파악
	public int getCountByBno(Long bno);
	//게시글 삭제에 따른 댓글 삭제
	public int deleteBno(Long bno);
	//내가 쓴 댓글 개수 
	public int getTotalByUser(String userid);
	
}
