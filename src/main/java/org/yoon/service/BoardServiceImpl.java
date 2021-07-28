package org.yoon.service;

import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;
import org.yoon.mapper.AttachMapper;
import org.yoon.mapper.BoardMapper;
import org.yoon.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

	private BoardMapper mapper;
	private AttachMapper AttachMapper;
	private ReplyMapper ReplyMapper;

	@Transactional
	@Override
	public void register(BoardVO board) {
		
		log.info("==========글 등록========:    "+ board);
		mapper.insertSelectKey(board);
		//글 등록시 파일 정보
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			AttachMapper.insert(attach);
		});
	
	}

	@Override
	public BoardVO get(Long bno) {

		log.info("==========글 조회========:    "+ bno);
		//조회수 증가
		mapper.visit(bno);
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public int remove(Long bno) {
		//글 삭제시-파일-댓글 종속삭제됨(오라클)
		return mapper.delete(bno);
	}

	@Transactional
	@Override
	public int modify(BoardVO board) {
		log.info("==========글 수정========:    "+ board);
		
		//기존의 첨부파일 삭제한후 다시 첨부파일 추가하는 방식으로 동작
		AttachMapper.deleteAll(board.getBno());
		int cnt=mapper.update(board);
		
		//1.글 수정이 완료, 2.수정된 글의 파일이 존재시 3. 수정된 글의 파일 크기가 존재시  앞선 3개의 조건이 만족하면 
		if(cnt!=0 && board.getAttachList()!=null && board.getAttachList().size()>0) {
			//새로운 파일 등록(파일 수정 등록)
			board.getAttachList().forEach(attach ->{
				attach.setBno(board.getBno());
				AttachMapper.insert(attach);
			});
		}
		
		return cnt;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("==========글 리스트 출력========");
		return mapper.getListPaging(cri);
	}

//	@Override
//	public List<BoardVO> getListPaging(Criteria cri) {
//		log.info("==========글 리스트 페이징 출력========");
//		return mapper.getListPaging(cri);
//	}

	@Override
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getTotal(cri);
	}

	@Override
	public int visit(Long bno) {
		log.info("조회수 증가");
		return mapper.visit(bno);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info(bno+"의 첨부파일 가져오기");
		return AttachMapper.findByBno(bno);
	}

	@Override
	public List<BoardVO> getNewList() {
		log.info("============갤러리게시판 최신글 조회==============");
		return mapper.getNewList();
	}

	@Override
	public List<BoardVO> getBestList() {
		log.info("============갤러리게시판 베스트글 조회==================");
		return mapper.getBestList();
	}

	@Transactional
	@Override
	public void recommend(HashMap map) {
		log.info("=============게시글 추천하기==============");
		log.info(map.get(map));
		//게시글 추천 수 증가
		mapper.getMoreRecommend(map.get("bno"));
		
		//추천아이디 및 글번호 저장
		mapper.recommend(map);
	}

	@Override
	public int checkRecommend(HashMap map) {
		log.info("=============게시글 추천여부 조회==============");
		log.info(map);
		return mapper.checkRecommend(map);
	}

	@Transactional
	@Override
	public void cancelRecommend(HashMap map) {
		log.info("=============게시글 추천취소==============");
		//게시글 추천 수 감소
		mapper.reduceRecommend(map.get("bno"));
		
		//추천아이디 및 글번호 삭제
		mapper.cancelRecommend(map);
	}

	@Override
	public List<BoardVO> getNewboard(String userid) {
		log.info("=============게시판 최신글 출력==============");
		return mapper.getNewboard(userid);
	}

	@Override
	public List<ReplyVO> getNewReply(String userid) {
		log.info("=============최신 댓글 출력==============");
		return mapper.getNewReply(userid);
	}

	@Override
	public int getAllboardCount(String userid) {
		log.info("=============모든 게시글 수 출력==============");
		return mapper.getAllboardCount(userid);
	}

	@Override
	public int getAllReplyCount(String userid) {
		log.info("=============모든 댓글 수 출력==============");
		return mapper.getAllReplyCount(userid);
	}

	@Override
	public List<BoardVO> getListByUser(Map<String,Object> map) {
		log.info("=============내가 쓴 게시글 출력==============");
		return mapper.getListByUser(map);
	}

	@Override
	public int getTotalByUser(String userid) {
		log.info("=============내가 쓴 게시글 개수 출력==============");
		return mapper.getTotalByUser(userid);
	}

	


	
}
