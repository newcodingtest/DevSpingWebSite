package org.yoon.domain;

import lombok.Data;

@Data
public class BoardAttachVO {

  private String uuid; 
  private String uploadPath;
  private String fileName;
  private boolean fileType;
  
  private Long bno;
  private String userid;
  private String type; //게시판 타입
 
}
