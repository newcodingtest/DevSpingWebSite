package org.yoon.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.PageDTO;
import org.yoon.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor // 모든 필드값 생성자 주입
public class BoardController {

	private BoardService service;
	
	//글삭제와 함께 진행되는 첨부파일 삭제
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		log.info("글에 포함되있는 첨부파일 삭제");
		log.info(attachList);
		
		attachList.forEach(attach ->{
			try {
				//java.nio.file의 Path이용->파일의 절대 경로 가져오기
				Path file = Paths.get("c:\\upload1\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				//삭제대상 파일이 존재하는지 확인
				Files.deleteIfExists(file);
				//파일의 타입이 이미지로 시작하면?
				if(Files.probeContentType(file).startsWith("image")) {
					//썸네일 경로 가져와서
					Path thumNail = Paths.get("c:\\upload1\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					//삭제
					Files.delete(thumNail);		
				}
			} catch (Exception e) {
				log.error("첨부파일 삭제 에러: "+e.getMessage());
			}//End catch
		}); //End forEach
	}//End deleteFiles
	
	
	//글 리스트 출력
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("글 목록 생성(list)");
		log.info("======[list]: ");
		//글 정보 넘기기
		model.addAttribute("list", service.getList(cri));
		
		int total = service.getTotal(cri);
		//페이징 정보
		model.addAttribute("pageMaker", new PageDTO(cri,total));
	}
	
	//글 등록 페이지로 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/register")
	public void register() {
		log.info("글 등록페이지로 이동");
		}

	//글 등록
	@PostMapping("/register")
	//어떤 사용자든 로그인 성공한사람만
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("글 등록: " + board);
		if(board.getAttachList()!=null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno()); // 화면단에서 oo번째 글이 등록됬는지 정보 전송
		
		log.info(board.getBno());
		return "redirect:/board/list";
	}
	
	//글 조회 페이지로 이동
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("/get");
		//조회수 증가
		service.visit(bno);
		model.addAttribute("board",service.get(bno));
		
	}
	
	@PreAuthorize("principal.username == #writer")
	//글 수정
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri,RedirectAttributes rttr) {
		log.info("/modify");
		
		if(service.modify(board)==1) {
			rttr.addFlashAttribute("result","success"); // 화면단으로 성공 메시지 전송
		}
		
		return "redirect:/board/list";
	}
	
	@PreAuthorize("principal.username == #writer")
	//글 삭제
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("삭제: "+bno);
		//파일 목록 가쟈오기
		List<BoardAttachVO>list=service.getAttachList(bno);
		
		//글이 삭제되면?
		if(service.remove(bno)==1) {
			//가져온 파일목록 또한 같이 삭제하기
			deleteFiles(list);
			rttr.addFlashAttribute("result","success"); // 화면단으로 성공 메시지 전송
		}
		
		return "redirect:/board/list"+cri.getListLink();
	}

	//파일 가져오기
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>>getAttachList(Long bno){
		log.info("getAttachList"+bno);
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	
	//해당글 좋아요 
	@PostMapping("/like")
	@ResponseBody
	public int like(Long bno){
		log.info(bno+"좋아요 버튼 클릭");
		return service.Like(bno);
	}
	
	
}
