package org.yoon.service;

import java.util.HashMap; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;

import org.yoon.mapper.AttachMapper;
import org.yoon.mapper.GBoardAttachMapper;
import org.yoon.mapper.GBoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class GBoardServiceImpl implements GBoardService{

	@Setter(onMethod_= @Autowired)
	private GBoardMapper mapper;
	
	@Setter(onMethod_= @Autowired)
	private AttachMapper gattachMapper;
	
	@Transactional
	@Override
	public void register(BoardVO bno) {
		log.info("=============게시글 등록==============");
		 mapper.insertSelectKey(bno);
		 
		 if(bno.getAttachList() == null || bno.getAttachList().size() <= 0) {
			 return;
		 }
		 bno.getAttachList().forEach(attach -> {
			 attach.setBno(bno.getBno());
			 gattachMapper.insert(attach);
		 });
	}

	@Transactional
	@Override
	public BoardVO get(Long bno) {
		log.info("=============게시글 상세보기==============");
		
		//조회수 올리기===============================================
		mapper.getMoreVisit(bno);
		
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public int modify(BoardVO board) {
		log.info("=============게시글 수정==============");
		
		gattachMapper.deleteAll(board.getBno());
		int cnt = mapper.update(board);
		//1.글 수정이 완료, 2.수정된 글의 파일이 존재시 3. 수정된 글의 파일 크기가 존재시  앞선 3개의 조건이 만족하면 
		if(cnt!=0 && board.getAttachList()!=null && board.getAttachList().size()>0) {
			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				gattachMapper.insert(attach);
			});
		}
		return cnt;
	}
	
	@Transactional
	@Override
	public int delete(Long bno) {
		log.info("=============게시글 삭제==============");
		gattachMapper.deleteAll(bno);
		return mapper.delete(bno);
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("=============게시글 목록 조회==============");
		return mapper.getListPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("=============게시글 총 갯수==============");
		return mapper.getTotal(cri);
	}
	
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("======글번호로 첨부파일 리스트 조회=======:"+bno);
		return gattachMapper.findByBno(bno);
	}

	@Transactional
	@Override
	public void recommend(HashMap map) {
		log.info("=============게시글 추천하기==============");
		log.info(map.get(map));
		//게시글 추천 수 증가
		mapper.getMoreRecomend(map.get("bno"));
		
		//추천아이디 및 글번호 저장
		mapper.recomend(map);
	}

	@Override
	public int checkRecommend(HashMap map) {
		log.info("=============게시글 추천여부 조회==============");
		log.info(map);
		return mapper.checkRecomend(map);
	}

	@Transactional
	@Override
	public void cancelRecommend(HashMap map) {
		log.info("=============게시글 추천취소==============");
		//게시글 추천 수 감소
		mapper.reduceRecomend(map.get("bno"));
		
		//추천아이디 및 글번호 삭제
		mapper.cancelRecomend(map);
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
	
	
	
	

}