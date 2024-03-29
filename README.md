# DevSpingWebSite
🚩스프링 개인 커뮤니티 구축<BR>
🚩프로젝트 기간: 2021년 06월 15일~2021년 07월15일

# 1.프로젝트 소개
* 대부분의 커뮤니티 기능들이 활성화 된 사이트 입니다.
* 웹개발에 기본적인 것들을 숙지하고자 주제를 선정하였습니다.

# 🔧2. 사용툴
  ![image](https://user-images.githubusercontent.com/57785267/127409268-51f15498-e6b5-4624-8d57-f0a7f20f91c3.png)
  
  
# 💻3. 핵심기능
* 회원가입 / 로그인 / 로그아웃
  - 아이디, 비밀번호, 이메일 유효성 확인
  - 이메일 인증 및 인증 번호 전송
  - Spring security 로그인/로그아웃
  - 비밀번호 암호화(BCryptPasswordEncoder)
  - 아이디 저장(remember-me)
  - 소셜 로그인 연동(네이버 로그인 api)
  - 아이디/비밀번호 찾기(이메일 인증후 진행)
 
 * 마이프로필
   - 내가 작성한 글 목록 확인, 삭제
   - 내가 작성한 댓글 목록 확인, 삭제
   - 회원정보 수정(소개글, 프로필 사진)
   - 회원탈퇴
   - 쪽지함 송/수신(상대방 읽음 여부 확인) 및 송신 취소
  
 * 게시판
    - Spring security 접근제어(글/댓글 -> 작성, 수정, 삭제)
    - Transaction 적용(작성, 수정, 삭제)
    - 댓글 등록(공개/비공개 활성화)
    - 추천 하기/취소
    - 파일 업로드/다운로드(파일타입,크기 유효성 검사)
    - 검색/페이징 처리
    - 자유게시판(일반 글 리스트)/사진게시판(사진 형태 리스트)
    - 메인게시판(자유/사진 최신글 기준 5개노출/ 추천수 기준 5개노출)

 * 스케줄러
   - 데이터베이스 <-> 서버 파일 검사, 파일 자동제거(새벽2시)
  
 * 관리자페이지 
   - 사이트 회원 정보 확인
  
  * 시연영상
  https://www.youtube.com/watch?v=iahEgZYWmok&ab_channel=civilizationonline
  
 # 3.구조도
   * 클래스 설계도<BR>
  ![image](https://user-images.githubusercontent.com/57785267/127409557-a57ca596-365e-4624-92b8-6deecfa4c721.png)

   * DB 설계도
  ![image](https://user-images.githubusercontent.com/57785267/127409459-bca53d12-766c-48d2-9e13-2125dff0e4e0.png)

