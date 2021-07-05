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
		log.info("=============�Խñ� ���==============");
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
		log.info("=============�Խñ� �󼼺���==============");
		
		//��ȸ�� �ø���===============================================
		mapper.getMoreVisit(bno);
		
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public int modify(BoardVO board) {
		log.info("=============�Խñ� ����==============");
		
		gattachMapper.deleteAll(board.getBno());
		int cnt = mapper.update(board);
		//1.�� ������ �Ϸ�, 2.������ ���� ������ ����� 3. ������ ���� ���� ũ�Ⱑ �����  �ռ� 3���� ������ �����ϸ� 
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
		log.info("=============�Խñ� ����==============");
		gattachMapper.deleteAll(bno);
		return mapper.delete(bno);
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("=============�Խñ� ��� ��ȸ==============");
		return mapper.getListPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("=============�Խñ� �� ����==============");
		return mapper.getTotal(cri);
	}
	
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("======�۹�ȣ�� ÷������ ����Ʈ ��ȸ=======:"+bno);
		return gattachMapper.findByBno(bno);
	}

	@Transactional
	@Override
	public void recommend(HashMap map) {
		log.info("=============�Խñ� ��õ�ϱ�==============");
		log.info(map.get(map));
		//�Խñ� ��õ �� ����
		mapper.getMoreRecomend(map.get("bno"));
		
		//��õ���̵� �� �۹�ȣ ����
		mapper.recomend(map);
	}

	@Override
	public int checkRecommend(HashMap map) {
		log.info("=============�Խñ� ��õ���� ��ȸ==============");
		log.info(map);
		return mapper.checkRecomend(map);
	}

	@Transactional
	@Override
	public void cancelRecommend(HashMap map) {
		log.info("=============�Խñ� ��õ���==============");
		//�Խñ� ��õ �� ����
		mapper.reduceRecomend(map.get("bno"));
		
		//��õ���̵� �� �۹�ȣ ����
		mapper.cancelRecomend(map);
	}

	@Override
	public List<BoardVO> getNewList() {
		log.info("============�������Խ��� �ֽű� ��ȸ==============");
		return mapper.getNewList();
	}

	@Override
	public List<BoardVO> getBestList() {
		log.info("============�������Խ��� ����Ʈ�� ��ȸ==================");
		return mapper.getBestList();
	}
	
	
	
	

}