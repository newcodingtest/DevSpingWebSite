package org.yoon.controller;

import java.io.File; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.PageDTO;
import org.yoon.service.GBoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@RequestMapping("/Gboard/*")
@Log4j
@AllArgsConstructor
public class GalleryBoardController {

	private GBoardService service;
	
	
	//�Խñ۸�� ��ȸ
	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		log.info("=============�Խñ� ��� ��ȸ==============");
		//�� ����
		model.addAttribute("list", service.getList(cri));
		
		//�Խñ� �� ����
		int total = service.getTotal(cri);
		
		//����¡ ����
		model.addAttribute("pageMaker", new PageDTO(cri,total));
	}
	
	//�Խñ� �󼼺��� �� ����������
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, Model model, @ModelAttribute("cri") Criteria cri ) {
		log.info("=============�� �󼼺��� �� ����================");
		model.addAttribute("Gboard",service.get(bno));
		
		String userId = null;
		userId = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info(userId);
		int result = 0;

		//1. �α����� ���
		if(!userId.equals("anonymousUser")) {
			HashMap<String, Object> map = new HashMap<>();
			
			map.put("userid", userId);
			map.put("bno", bno);
			
			//���̵�� �۹�ȣ�� ��õ���� ��ȸ
			result = service.checkRecommend(map);
			
			//2. �α��� �ߴµ� ��õ���� �ƴ� ��� & �α��� �� �� ���
			if(result == 0) {
				model.addAttribute("isRecomend", false);
			}else {
			//��õ���� ���
				model.addAttribute("isRecomend", true);
			}
		}
	}
	
	//�Խñ� ���������
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void register() {}
	
	
	//�Խñ� ���
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO vo, RedirectAttributes rttr) {
		log.info("===============�Խñ� ���=================");
		log.info("register : "+vo);
		
		if(vo.getAttachList()!=null) {
			vo.getAttachList().forEach(attach -> log.info(attach));
		}
		
		log.info("=========================================");
		
		service.register(vo);
		rttr.addFlashAttribute("result", vo.getBno());
		
		return "redirect:/Gboard/list";
	}

	//�Խñ� ����
	@PreAuthorize("principal.username == #vo.writer")
	@PostMapping("/modify")
	public String modify(BoardVO vo, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("================�Խñ� ����===============:"+vo);
		
		if(service.modify(vo)==1) {
			rttr.addFlashAttribute("result","msuccess");
		}
		return "redirect:/Gboard/list";
	}
	
	//�Խñ� ����
	@PreAuthorize("principal.username == #writer")
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr,String writer) {
		log.info("==============�Խñ� ����============="+bno);
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		
		if(service.delete(bno)==1) {
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "dsuccess");
		}
		return "redirect:/Gboard/list"+cri.getListLink();
	}
	
	//���� ���ε� ���� ���� ��¥���� ����
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("-",File.separator);
		
	}
	
	//���ε� ���� ���
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/uploadAjaxAction", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		List<BoardAttachVO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload1";

		String uploadFolderPath = getFolder();

		//make folder----------
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile: uploadFile) {
			BoardAttachVO attachVO = new BoardAttachVO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			//IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			attachVO.setFileName(uploadFileName);
			
			//�ߺ� ������ ���� UUID ����
			UUID uuid= UUID.randomUUID();
			uploadFileName = uuid.toString()+"_"+uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachVO.setUuid(uuid.toString());
				attachVO.setUploadPath(uploadFolderPath);
				
				
				attachVO.setFileType(true);
				FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
				Thumbnailator.createThumbnail(multipartFile.getInputStream(),thumbnail, 100, 100);
				thumbnail.close();
				
				list.add(attachVO);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//������ �̹��� ������ ����
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		
		File file = new File("C:\\upload1\\"+fileName);
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type",Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//�̹��� Ŭ�� �� �ٿ�ε�
	@GetMapping(value="/download", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent")String userAgent, String fileName){
		log.info("download file: "+fileName);
		Resource resource = new FileSystemResource("c:\\upload\\"+fileName);
		
		if(resource.exists()==false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		String resourceName = resource.getFilename();
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		HttpHeaders headers = new HttpHeaders();
		
		try {
			String downloadName = null;
			if(userAgent.contains("Trident")) {
				log.info("IE browser");
				downloadName=URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+"," ");
				
			}else if(userAgent.contains("Edge")) {
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
			}else {
				log.info("chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1"); 
			}
			
			headers.add("Content-Disposition", "attachment;filename="+downloadName); 
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers,HttpStatus.OK);
	}
	
	//���ε� ���� ����
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		File file;
		
		try {
			file = new File("c:\\upload1\\"+URLDecoder.decode(fileName,"UTF-8"));
			file.delete();
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				file = new File(largeFileName);
				file.delete();
			}
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
	
	
	//�� ��ȣ�� ÷������ ��� ��ȸ
	@GetMapping(value= "/getAttachList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		log.info("getAttachList"+bno);
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}
	
	
	//÷������ ��� ����
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("C:\\upload1\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				Files.deleteIfExists(file);
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\upload1\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumbNail);
				}
			}catch(Exception e) {
				log.error("delete file error" + e.getMessage());
			}
		});
	}
	
	//�Խñ� ��õ
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/recomend")
	@ResponseBody
	public String recommend(@RequestParam("id") String userid, @RequestParam("bno") long bno) {
		log.info("�Խñ� ��õ�ϱ�");
		HashMap<String,Object > map = new HashMap<>();
		map.put("userid", userid);
		map.put("bno", bno);
		log.info("userid: "+userid +"gno: "+bno);
		int result = service.checkRecommend(map);
		log.info(result);
		//ó�� ��õ�ϴ� ���
		if(result == 0) {
			service.recommend(map);	
			return "recomend";
		}
		//��õ�ϱ� ����ϴ� ���
		else {
			service.cancelRecommend(map);
			return "cancel";
		}
	}


	
	
}