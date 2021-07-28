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
		
		log.info("==========�� ���========:    "+ board);
		mapper.insertSelectKey(board);
		//�� ��Ͻ� ���� ����
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

		log.info("==========�� ��ȸ========:    "+ bno);
		//��ȸ�� ����
		mapper.visit(bno);
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public int remove(Long bno) {
		//�� ������-����-��� ���ӻ�����(����Ŭ)
		return mapper.delete(bno);
	}

	@Transactional
	@Override
	public int modify(BoardVO board) {
		log.info("==========�� ����========:    "+ board);
		
		//������ ÷������ �������� �ٽ� ÷������ �߰��ϴ� ������� ����
		AttachMapper.deleteAll(board.getBno());
		int cnt=mapper.update(board);
		
		//1.�� ������ �Ϸ�, 2.������ ���� ������ ����� 3. ������ ���� ���� ũ�Ⱑ �����  �ռ� 3���� ������ �����ϸ� 
		if(cnt!=0 && board.getAttachList()!=null && board.getAttachList().size()>0) {
			//���ο� ���� ���(���� ���� ���)
			board.getAttachList().forEach(attach ->{
				attach.setBno(board.getBno());
				AttachMapper.insert(attach);
			});
		}
		
		return cnt;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("==========�� ����Ʈ ���========");
		return mapper.getListPaging(cri);
	}

//	@Override
//	public List<BoardVO> getListPaging(Criteria cri) {
//		log.info("==========�� ����Ʈ ����¡ ���========");
//		return mapper.getListPaging(cri);
//	}

	@Override
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getTotal(cri);
	}

	@Override
	public int visit(Long bno) {
		log.info("��ȸ�� ����");
		return mapper.visit(bno);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info(bno+"�� ÷������ ��������");
		return AttachMapper.findByBno(bno);
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

	@Transactional
	@Override
	public void recommend(HashMap map) {
		log.info("=============�Խñ� ��õ�ϱ�==============");
		log.info(map.get(map));
		//�Խñ� ��õ �� ����
		mapper.getMoreRecommend(map.get("bno"));
		
		//��õ���̵� �� �۹�ȣ ����
		mapper.recommend(map);
	}

	@Override
	public int checkRecommend(HashMap map) {
		log.info("=============�Խñ� ��õ���� ��ȸ==============");
		log.info(map);
		return mapper.checkRecommend(map);
	}

	@Transactional
	@Override
	public void cancelRecommend(HashMap map) {
		log.info("=============�Խñ� ��õ���==============");
		//�Խñ� ��õ �� ����
		mapper.reduceRecommend(map.get("bno"));
		
		//��õ���̵� �� �۹�ȣ ����
		mapper.cancelRecommend(map);
	}

	@Override
	public List<BoardVO> getNewboard(String userid) {
		log.info("=============�Խ��� �ֽű� ���==============");
		return mapper.getNewboard(userid);
	}

	@Override
	public List<ReplyVO> getNewReply(String userid) {
		log.info("=============�ֽ� ��� ���==============");
		return mapper.getNewReply(userid);
	}

	@Override
	public int getAllboardCount(String userid) {
		log.info("=============��� �Խñ� �� ���==============");
		return mapper.getAllboardCount(userid);
	}

	@Override
	public int getAllReplyCount(String userid) {
		log.info("=============��� ��� �� ���==============");
		return mapper.getAllReplyCount(userid);
	}

	@Override
	public List<BoardVO> getListByUser(Map<String,Object> map) {
		log.info("=============���� �� �Խñ� ���==============");
		return mapper.getListByUser(map);
	}

	@Override
	public int getTotalByUser(String userid) {
		log.info("=============���� �� �Խñ� ���� ���==============");
		return mapper.getTotalByUser(userid);
	}

	


	
}
