<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
.correct{
	color : green;
}
.incorrect{
	color: red;
}
#mail_check_input_box_false{
    background-color:#ebebe4;
}
 
#mail_check_input_box_true{
    background-color:white;
}
 
    .title{
        width:800px;
        margin:auto;
    }
    .title span{
        font-size:17px;
        
    }
    .terms{
        background-color:whitesmoke;
        border:1px solid lightgray;
        margin:auto;
        width:800px;
        height:300px;
        overflow-y: scroll;
    }
    .joinform{
        margin:auto;
        width:800px;
        color:black;
        font-size:17px;
    }
    .joinform tr{
        height:40px;    
    }
    .joinsort{
        background-color:lightgray;
        width:100px;
        text-align: center;-
    }
    .joininputbox{
        border-top:1px solid lightgray;
        border-bottom:1px solid lightgray;
        border-collapse: none;
    }
    .joininput{
        background: none;
        width: 200px;
        text-align: center;
        border-radius: 5px;
        border: 1px solid lightgray;
        height:25px;
    }
    .joininput:focus{
        outline:none;
    }
    .joinsubmit{
        width:150px;
        height:50px;
        font-size:20px;
        font-family: 'NanumSquareB';
        border:none;
        color:white;
        background-color:#EB3232;
        transition: all 0.5s;
    }
        .cancel{
        width:150px;
        height:50px;
        font-size:20px;
        font-family: 'NanumSquareB';
        border:none;
        color:white;
        background-color:#EB3232;
        transition: all 0.5s;
    }
    .joinsubmit:hover{
        width:160px;
        height:60px;
        font-size:22px;
        font-family: 'NanumSquareB';
        border:none;
        color:white;
        background-color:#EB3232;
        border-radius: 5px;
        transition: all 0.5s;
    }
    button{
        border:none;
        background-color:#555555;
        color:white;
        height:20px;
        font-size: 12px;
        transition: all 0.5s;
        
    }
    button:hover{
        border: solid 1px white;
        border-radius: 40px;
        background-color:#222222;
        transition: all 0.5s;
    }
</style>
   
    
<body>
<!------------------------------ ▼ 상단 공통 헤더 ▼ ------------------------------>
<div id="headers"></div>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

</script>
<!------------------------------ ▲ 상단 공통 헤더 ▲ ------------------------------>
<div class="title">
    <span>약관</span>
</div><br>
<div class="terms">
    
홈페이지 이용약관은 고객님의 권익보호와 개인정보 보호를 위해 만들어진 홈페이지 이용에 대한 규정입니다.<br><br>
다음과 같은 내용을 바탕으로 앞으로도 고객에게 더 깊은 믿음과 만족을 드리는 인터넷 쇼핑몰이 되도록 노력 하겠습니다.
<br>
<br>
<br>
<br>
<br>
제1조 (목적)
<br>
<br>
이 약관은 (주)홈페이지 (전자거래 사업자 이하 "회사"라 한다)가 운영하는 인터넷 쇼핑몰(www.smhrd.net 이하 "몰"이라 한다)에서 제공하는 인터넷 관련 서비스(이하 "서비스"라 한다)를 이용함에 있어 사이버몰과 이용자의 권리·의무 및 책임사항을 규정함을 목적으로 합니다.
<br>
<br>
※「PC통신, 모바일 무선 등을 이용하는 전자상거래에 대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다」
<br>
<br>
<br>
<br>
<br>
제2조 (용어의 정의)
<br>
<br>
 ① "몰" 이란 홈페이지 회사가 재화 또는 용역을 이용자에게 제공하기 위하여 컴퓨터등 정보통신설비를 이용하여 재화 또는 용역을 거래할 수 있도록 설정한 가상의 영업장을 말하며, 아울러 사이버몰을 운영하는 사업자의 의미로도 사용합니다.
<br>
<br>
 ② "이용자"란 "몰"에 접속하여 이 약관에 따라 "몰"이 제공하는 서비스를 받는 회원 및 비회원을 말합니다.
<br>
<br>
 ③ "회원"이라 함은 "몰"에 개인정보를 제공하여 회원등록을 한 자로서, "몰"의 정보를 지속적으로 제공받으며, "몰"이 제공하는 서비스를 계속적으로 이용할 수 있는 자를 말합니다.
<br>
<br>
 ④ 아이디(ID): 회원의 식별과 서비스 이용을 위하여 회원이 설정하고 회사가 승인하여 등록된 문자와 숫자의 조합을 말합니다.
<br>
<br>
 ⑤ 비밀번호: 회원의 동일성 확인과 회원의 권익 및 비밀보호를 위하여 회원 스스로가 설정하여 회사에 등록한 영문과 숫자의 조합을 말합니다.
<br>
<br>
 ⑥ "비회원"이라 함은 회원에 가입하지 않고 "몰"이 제공하는 서비스를 이용하는 자를 말합니다.
<br>
<br>
 ⑦  "적립금 및 포인트" 란 "몰"이 특정 재화 등을 구입한 이용자 또는 경품 등의 목적으로 이용자에게 부여한 사이버머니를 말하며, 적립금의 부여 및 사용 등과 관련한 사항은 이 약관 또는 "몰"의 운영정책이 정한 바에 따릅니다.
<br>
<br>
 ⑧ 제 2조에서 정의되지 않은 이 약관상의 용어의 의미는 일반적인 거래관행에 의합니다.
<br>
<br>
<br>
<br>
<br>
제3조 (약관의 명시와 개정)
<br>
<br>
 ① "몰"은 이 약관의 내용과 상호, 영업소 소재지, 대표자의 성명, 사업자등록번호, 연락처(전화, 팩스, 전자우편 주소 등) 등을 이용자가 알 수 있도록 "몰"의 초기 서비스화면(전면)에 게시합니다.
<br>
<br>
 ② "몰"은 '약관의 규제에 관한 법률', '전자거래기본법', '전자서명법', '정보통신망이용촉진등에관한법률', '방문판매등에관한법률', '소비자보호법' 등 관련법을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다.
<br>
<br>
 ③ "몰"이 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 "몰"의 초기화면에 그 적용일자 7일이전부터 적용일자 전일까지 공지합니다. 다만, 이용자에게 불리하게 약관내용을 변경하는 경우에는 최소한 30일 이상의 사전 유예기간을 두고 공지합니다. 이 경우 "몰“은 개정 전 내용과 개정 후 내용을 명확하게 비교하여 이용자가 알기 쉽도록 표시합니다.
<br>
<br>
 ④ "몰"이 약관을 개정할 경우에는 그 개정약관은 그 적용일자 이후에 체결되는 계약에만 적용되고 그 이전에 이미 체결된 계약에 대해서는 개정전의 약관조항이 그대로 적용됩니다. 다만 이미 계약을 체결한 이용자가 개정약관 조항의 적용을 받기를 원하는 뜻을 제3항에 의한 개정약관의 공지기간 내에 "몰"에 송신하여 "몰"의 동의를 받은 경우에는 개정약관 조항이 적용됩니다.
<br>
<br>
 ⑤ 이 약관에서 정하지 아니한 사항과 이 약관의 해석에 관하여는 정부가 제정한 전자거래소비자보호지침 및 관계법령 또는 상관례에 따릅니다.
<br>
<br>
<br>
<br>
<br>
제4조(서비스의 제공 및 변경)
<br>
<br>
 ① "몰"은 다음과 같은 업무를 수행합니다.
<br>
<br>
   1. 재화 또는 용역에 대한 정보 제공 및 구매계약의 체결
<br>
<br>
   2. 구매계약이 체결된 재화 또는 용역의 배송
<br>
<br>
   3. 기타 "몰"이 정하는 업무
<br>
<br>
 ② "몰"은 재화의 품절 또는 기술적 사양의 변경 등의 경우에는 장차 체결되는 계약에 의해 제공할 재화·용역의 내용을 변경할 수 있습니다. 이 경우에는 변경된 재화·용역의 내용 및 제공일자를 명시하여 현재의 재화·용역의 내용을 게시한 곳에 즉시 공지합니다.
<br>
<br>
 ③ "몰"이 제공하기로 이용자와 계약을 체결한 서비스의 내용을 재화의 품절 또는 기술적 사양의 변경 등의 사유로 변경할 경우에는 "몰"은 그 사유를 이용자에게 통지 가능한 방법으로 즉시 통지하며, "몰"은 이로 인하여 이용자가 입은 손해를 배상합니다. 단, "몰"에 고의 또는 과실이 없는 경우에는 그러하지 아니합니다.
<br>
<br>
<br>
<br>
<br>
제5조(서비스의 중단)
<br>
<br>
 ① "몰"은 컴퓨터 등 정보통신설비의 보수점검·교체 및 고장, 통신의 두절 등의 사유가 발생한 경우에는 서비스의 제공을 일시적으로 중단할 수 있습니다.
<br>
<br>
 ② 제1항에 의한 서비스 중단의 경우 "몰"은 제8조에 정한 방법으로 이용자에게 통지합니다.
<br>
<br>
 ③ "몰"은 제1항의 사유로 서비스의 제공이 일시적으로 중단됨으로 인하여 이용자 또는 제3자가 입은 손해에 대하여 배상합니다. 단 "몰"에 고의 또는 과실이 없는 경우에는 그러하지 아니합니다.
<br>
<br>
 ④ 사업종목의 전환, 사업의 포기, 업체 간의 통합 등의 이유로 서비스를 제공할 수 없게 되는 경우에는 “몰”은 제8조에 정한 방법으로 이용자에게 통지하고 당초 “몰”에서 제시한 조건에 따라 소비자에게 보상합니다. 다만, “몰”이 보상기준 등을 고지하지 아니한 경우에는 이용자들의 마일리지 또는 적립금 등을 “몰”에서 통용되는 통화가치에 상응하는 현물 또는 현금으로 이용자에게 지급합니다.
<br>
<br>
<br>
<br>
<br>
제6조(회원가입)
<br>
<br>
 ① 이용자는 "몰"이 정한 가입 양식에 따라 회원정보를 기입한 후 이 약관에 동의한다는 의사표시를 함으로서 회원가입을 신청합니다.
<br>
<br>
 ② "몰"은 제1항과 같이 회원으로 가입할 것을 신청한 이용자 중 다음 각 호에 해당하지 않는 한 회원으로 등록합니다.
<br>
<br>
   1. 가입신청자가 이 약관 제7조제3항에 의하여 이전에 회원자격을 상실한 적이 있는 경우, 다만 제7조제3항에 의한 회원자격 상실 후 3년이 경과한 자로서 "몰"의 회원 재 가입 승낙을 얻은 경우에는 예외로 한다.
<br>
<br>
   2. 등록 내용에 허위, 기재누락, 오기가 있는 경우
<br>
<br>
   3. 기타 회원으로 등록하는 것이 "몰"의 기술상 현저히 지장이 있다고 판단되는 경우
<br>
<br>
 ③ 회원가입계약의 성립시기는 "몰"의 승낙이 회원에게 도달한 시점으로 합니다.
<br>
<br>
 ④ 회원은 제19조제1항에 의한 등록사항에 변경이 있는 경우, 즉시 전자우편 및 회원정보 수정 등의 방법으로 그 변경사항을 "몰"에 알려야 합니다.
<br>
<br>
<br>
<br>
<br>
제7조(회원 탈퇴 및 자격 상실 등)
<br>
<br>
 ① 회원은 "몰"에 언제든지 탈퇴를 요청할 수 있으며 "몰"은 즉시 회원탈퇴를 처리합니다.
<br>
<br>
 ② 회원이 다음 각 호의 사유에 해당하는 경우, "몰"은 회원자격을 제한 및 정지시킬 수 있습니다.
<br>
<br>
   1. 가입 신청 시에 허위 내용을 등록한 경우
<br>
<br>
   2. "회사" 및 "몰"을 이용하여 구입한 재화·용역 등의 대금, 기타 "회사" 및 "몰" 이용에 관련하여 회원이 부담하는 채무를 기일에 지급하지 않는 경우
<br>
<br>
   3. 제 3자의 "몰" 이용을 방해하거나 그 정보를 도용하는 등 전자상거래질서를 위협하는 경우
<br>
<br>
   4. 타인의 ID와 비밀번호 또는 그 개인정보를 도용한 경우
<br>
<br>
   5.  "회사" 및 "몰"에서 타인의 명예를 손상시키거나 불이익을 주는 행위를 한 경우
<br>
<br>
   6.  "회사" 및 "몰"에 음란물을 게재하거나 음란사이트를 링크시키는 경우
<br>
<br>
   7. "회사" 및 "몰"을 이용하여 법령과 이 약관이 금지하거나 공서 양속에 반하는 행위를 하는 경우
<br>
<br>
   8 기타 서비스 운영을 고의로 방해하는 행위를 하는 경우
<br>
<br>
 ③ "몰"이 회원 자격을 제한 정지 시킨후, 동일한 행위가 2회이상 반복되거나 30일이내에 그 사유가 시정되지 아니하는 경우 "몰"은 회원자격을 상실시킬 수 있습니다.
<br>
<br>
 ④ "몰"이 회원자격을 상실시키는 경우에는 회원등록을 말소합니다. 이 경우 회원에게 이를 통지하고, 회원등록 말소 전에 소명할 기회를 부여합니다.
<br>
<br>
<br>
<br>
<br>
제8조(회원에 대한 통지)
<br>
<br>
 ① "몰"이 회원에 대한 통지를 하는 경우, 회원이 "몰"에 제출한 전자우편 주소로 할 수 있습니다.
<br>
<br>
 ② "몰"은 불특정다수 회원에 대한 통지의 경우 1주일이상 "몰" 게시판에 게시함으로서 개별 통지에 갈음할 수 있습니다.
<br>
<br>
<br>
<br>
<br>
제9조(구매신청)
<br>
<br>
 ① "몰"이용자는 "몰"상에서 이하의 방법에 의하여 구매를 신청합니다.
<br>
<br>
   1. 재화 등의 검색 및 선택
<br>
<br>
   2. 받는 사람의 성명, 주소, 전화번호, 전자우편주소 등의 입력
<br>
<br>
   3. 약관내용, 청약철회권이 제한되는 서비스, 배송료, 설치비 등의 비용부담과 관련한 내용에 대한 확인
<br>
<br>
   4. 이 약관에 동의하고 위 3.호의 사항을 확인하거나 거부하는 표시 (예, 마우스 클릭)
<br>
<br>
   5. 재화 등의 구매신청 및 이에 관한 확인 또는 “몰”의 확인에 대한 동의
<br>
<br>
   6. 결제방법의 선택
<br>
<br>
 ② “몰”이 제3자에게 구매자 개인정보를 제공할 필요가 있는 경우 1) 개인정보를 제공받는 자, 2)개인정보를 제공받는 자의 개인정보 이용목적, 3) 제공하는 개인정보의 항목, 4) 개인정보를 제공받는 자의 개인정보 보유 및 이용기간을 구매자에게 알리고 동의를 받아야 합니다. (동의를 받은 사항이 변경되는 경우에도 같습니다.)
<br>
<br>
 ③ “몰”이 제3자에게 구매자의 개인정보를 취급할 수 있도록 업무를 위탁하는 경우에는 1) 개인정보 취급위탁을 받는 자, 2) 개인정보 취급위탁을 하는 업무의 내용을 구매자에게 알리고 동의를 받아야 합니다. (동의를 받은 사항이 변경되는 경우에도 같습니다.) 다만, 서비스제공에 관한 계약이행을 위해 필요하고 구매자의 편의증진과 관련된 경우에는 「정보통신망 이용촉진 및 정보보호 등에 관한 법률」에서 정하고 있는 방법으로 개인정보 취급방침을 통해 알림으로써 고지절차와 동의절차를 거치지 않아도 됩니다.
<br>
<br>
<br>
<br>
<br>
제10조 (계약의 성립)
<br>
<br>
 ① "몰"은 제9조와 같은 구매신청에 대하여 다음 각호에 해당하지 않는 한 승낙합니다.
<br>
<br>
   1. 신청 내용에 허위, 기재누락, 오기가 있는 경우
<br>
<br>
   2. 미성년자가 담배, 주류등 청소년보호법에서 금지하는 재화 및 용역을 구매하는 경우
<br>
<br>
   3. 기타 구매신청에 승낙하는 것이 "몰" 기술상 현저히 지장이 있다고 판단하는 경우
<br>
<br>
 ② "몰"의 승낙이 제12조제1항의 수신확인통지형태로 이용자에게 도달한 시점에 계약이 성립한 것으로 봅니다.
<br>
<br>
<br>
<br>
<br>
제11조(지급방법)
<br>
<br>
 ① "몰"에서 구매한 재화 또는 용역에 대한 대금지급방법은 다음 각호의 하나로 할 수 있습니다.
<br>
<br>
   1. 계좌이체
<br>
<br>
   2. 신용카드결제
<br>
<br>
   3. 온라인무통장입금
<br>
<br>
   4. 전자화폐에 의한 결제 (향후 서비스 제공 예정)
<br>
<br>
   5. 마일리지 등“몰”이 지급한 포인트에 의한 결제
<br>
<br>
<br>
<br>
<br>
제12조(수신확인통지·구매신청 변경 및 취소)
<br>
<br>
 ① "몰"은 이용자의 구매신청이 있는 경우 이용자에게 수신확인통지를 합니다.
<br>
<br>
 ② 수신확인통지를 받은 이용자는 의사표시의 불일치 등이 있는 경우에는 수신확인통지를 받은 후 즉시 구매신청 변경 및 취소를 요청할 수 있습니다.
<br>
<br>
 ③ "몰"은 배송전 이용자의 구매신청 변경 및 취소 요청이 있는 때에는 지체 없이 그 요청에 따라 처리합니다.
<br>
<br>
<br>
<br>
<br>
제13조(재화등의 배송 및 공급)
<br>
<br>
 ① "몰"은 이용자가 구매한 재화에 대해 배송수단, 수단별 배송비용 부담자, 수단별 배송기간 등을 명시 또는 제공합니다.
<br>
<br>
 ② 만약 “몰”이 약정 배송기간을 초과한 경우에는 그로 인한 이용자의 손해를 배상하여야 합니다. 다만 “몰”이 고의, 과실이 없음을 입증한 경우에는 그러하지 아니합니다.
<br>
<br>
<br>
<br>
<br>
제14조(환급, 반품 및 교환)
<br>
<br>
 ① “몰”은 이용자가 구매신청한 재화 등이 품절 등의 사유로 인도 또는 제공을 할 수 없을 때 에는 지체 없이 그 사유를 이용자에게 통지하고 사전에 재화 등의 대금을 받은 경우에는 대 금을 받은 날부터 3영업일 이내에 환급하거나 환급에 필요한 조치를 취합니다.
<br>
<br>
 ② 다음 각호의 경우에는 "몰"은 배송된 재화일지라도 재화를 반품받은 다음 영업일 이내에 이용자의 요구에 따라 즉시 환급, 반품 및 교환 조치를 합니다. 다만 그 요구기한은 배송된 날로부터 7일 이내로 합니다.
<br>
<br>
   1. 배송된 재화가 주문내용과 상이하거나 "몰"이 제공한 정보와 상이할 경우
<br>
<br>
   2. 배송된 재화가 파손, 손상되었거나 오염되었을 경우
<br>
<br>
   3. 재화가 광고에 표시된 배송기간보다 늦게 배송된 경우
<br>
<br>
   4. 방문판매등에 관한 법률 제18조에 의하여 광고에 표시하여야 할 사항을 표시하지 아니한 상태에서 이용자의 청약이 이루어진 경우
<br>
<br>
<br>
<br>
<br>
제15조(청약철회 등)
<br>
<br>
 ① “몰”과 재화등의 구매에 관한 계약을 체결한 이용자는 「전자상거래 등에서의 소비자보호에 관한 법률」 제13조 제2항에 따른 계약내용에 관한 서면을 받은 날(그 서면을 받은 때보다 재화 등의 공급이 늦게 이루어진 경우에는 재화 등을 공급받거나 재화 등의 공급이 시작된 날을 말합니다)부터 7일 이내에는 청약의 철회를 할 수 있습니다. 다만, 청약철회에 관하여 「전자상거래 등에서의 소비자보호에 관한 법률」에 달리 정함이 있는 경우에는 동 법 규정에 따릅니다.
<br>
<br>
 ② 이용자는 재화 등을 배송 받은 경우 다음 각 호의 1에 해당하는 경우에는 반품 및 교환을 할 수 없습니다.
<br>
<br>
   1. 이용자에게 책임 있는 사유로 재화 등이 멸실 또는 훼손된 경우(다만, 재화 등의 내용을 확인하기 위하여 포장 등을 훼손한 경우에는 청약철회를 할 수 있습니다)
<br>
<br>
   2. 이용자의 사용 또는 일부 소비에 의하여 재화 등의 가치가 현저히 감소한 경우
<br>
<br>
   3. 시간의 경과에 의하여 재판매가 곤란할 정도로 재화등의 가치가 현저히 감소한 경우
<br>
<br>
   4. 같은 성능을 지닌 재화 등으로 복제가 가능한 경우 그 원본인 재화 등의 포장을 훼손한 경우
<br>
<br>
 ③ 제2항제2호 내지 제4호의 경우에 “몰”이 사전에 청약철회 등이 제한되는 사실을 소비자가 쉽게 알 수 있는곳에 명기하거나 시용상품을 제공하는 등의 조치를 하지 않았다면 이용자의 청약철회 등이 제한되지 않습니다.
<br>
<br>
 ④ 이용자는 제1항 및 제2항의 규정에 불구하고 재화 등의 내용이 표시·광고 내용과 다르거나 계약내용과 다르게 이행된 때에는 당해 재화 등을 공급받은 날부터 3월 이내, 그 사실을 안 날 또는 알 수 있었던 날부터 30일 이내에 청약철회 등을 할 수 있습니다.
<br>
<br>
 ⑤ 이용자의 단순 구매의사 변경에 의한 반품일 경우 배송비용은 이용자가 부담하도록 합니다.
<br>
<br>
<br>
<br>
<br>
제16조(청약철회 등의 효과)
<br>
<br>
 ① “몰”은 이용자로부터 재화 등을 반환받은 경우 3영업일 이내에 이미 지급받은 재화 등의 대금을 환급합니다. 이 경우 “몰”이 이용자에게 재화등의 환급을 지연한때에는 그 지연기간에 대하여 「전자상거래 등에서의 소비자보호에 관한 법률 시행령」제21조의2에서 정하는 지연이자율을 곱하여 산정한 지연이자를 지급합니다.
<br>
<br>
 ② “몰”은 위 대금을 환급함에 있어서 이용자가 신용카드 또는 전자화폐 등의 결제수단으로 재화 등의 대금을 지급한 때에는 지체 없이 당해 결제수단을 제공한 사업자로 하여금 재화 등의 대금의 청구를 정지 또는 취소하도록 요청합니다.
<br>
<br>
 ③ 청약철회 등의 경우 공급받은 재화 등의 반환에 필요한 비용은 이용자가 부담합니다. “몰”은 이용자에게 청약철회 등을 이유로 위약금 또는 손해배상을 청구하지 않습니다. 다만 재화 등의 내용이 표시·광고 내용과 다르거나 계약내용과 다르게 이행되어 청약철회 등을 하는 경우 재화 등의 반환에 필요한 비용은 “몰”이 부담합니다.
<br>
<br>
 ④ 이용자가 재화 등을 제공받을 때 발송비를 부담한 경우에 “몰”은 청약철회 시 그 비용을 누가 부담하는지를 이용자가 알기 쉽도록 명확하게 표시합니다.
<br>
<br>
<br>
<br>
<br>
제17조(개인정보보호)
<br>
<br>
 ① "몰"은 이용자의 정보수집시 구매계약 이행에 필요한 최소한의 정보를 수집합니다.
    다음 사항을 필수사항으로 하며 그 외 사항은 선택사항으로 합니다.
<br>
<br>
   1. 성명
<br>
<br>
   2. 주민등록번호(회원의 경우)
<br>
<br>
   3. 주소
<br>
<br>
   4. 전화번호
<br>
<br>
   5. 희망ID(회원의 경우)
<br>
<br>
   6. 비밀번호(회원의 경우)
<br>
<br>
 ② "몰"이 이용자의 개인식별이 가능한 개인정보를 수집하는 때에는 반드시 당해 이용자의 동의를 받습니다.
<br>
<br>
 ③ 제공된 개인정보는 당해 이용자의 동의 없이 목적 외의 이용이나 제3자에게 제공할 수 없으며, 이에 대한 모든 책임은 "몰"이 집니다. 다만, 다음의 경우에는 예외로 합니다.
<br>
<br>
   1. 배송업무상 배송업체에게 배송에 필요한 최소한의 이용자의 정보(성명, 주소, 전화번호)를 알려주는 경우
<br>
<br>
   2. 통계작성, 학술연구 또는 시장조사를 위하여 필요한 경우로서 특정 개인을 식별할 수 없는 형태로 제공하는 경우
<br>
<br>
 ④ "몰"이 제2항과 제3항에 의해 이용자의 동의를 받아야 하는 경우에는 개인정보관리 책임자의 신원(소속, 성명 및 전화번호 기타 연락처), 정보의 수집목적 및 이용목적, 제3자에 대한 정보제공 관련사항(제공받는자, 제공목적 및 제공할 정보의 내용)등 정보통신망이용촉진등에관한법률 제18조제3항이 규정한 사항을 미리 명시하거나 고지해야 하며 이용자는 언제든지 이 동의를 철회할 수 있습니다.
<br>
<br>
 ⑤ 이용자는 언제든지 "몰"이 가지고 있는 자신의 개인정보에 대해 열람 및 오류정정을 요구할 수 있으며 "몰"은 이에 대해 지체 없이 필요한 조치를 취할 의무를 집니다. 이용자가 오류의 정정을 요구한 경우에는 "몰"은 그 오류를 정정할 때까지 당해 개인정보를 이용하지 않습니다.
<br>
<br>
 ⑥ "몰"은 개인정보 보호를 위하여 관리자를 한정하여 그 수를 최소화하며 신용카드, 은행계좌 등을 포함한 이용자의 개인정보의 분실, 도난, 유출, 변조 등으로 인한 이용자의 손해에 대하여 모든 책임을 집니다.
<br>
<br>
 ⑦ "몰" 또는 그로부터 개인정보를 제공받은 제3자는 개인정보의 수집목적 또는 제공받은 목적을 달성한 때에는 당해 개인정보를 지체 없이 파기합니다.
<br>
<br>
<br>
<br>
<br>
제18조("몰"의 의무)
<br>
<br>
 ① "몰은 법령과 이 약관이 금지하거나 공서 양속에 반하는 행위를 하지 않으며 이 약관이 정하는 바에 따라 지속적이고, 안정적으로 재화·용역을 제공하는 데 최선을 다하여야 합니다.
<br>
<br>
 ② "몰"은 이용자가 안전하게 인터넷 서비스를 이용할 수 있도록 이용자의 개인정보(신용정보 포함)보호를 위한 보안 시스템을 갖추어야 합니다.
<br>
<br>
 ③ "몰"이 상품이나 용역에 대하여 「표시·광고의공정화에관한법률」 제3조 소정의 부당한 표시·광고행위를 함으로써 이용자가 손해를 입은 때에는 이를 배상할 책임을 집니다.
<br>
<br>
 ④ "몰"은 이용자가 원하지 않는 영리목적의 광고성 전자우편을 발송하지 않습니다.
<br>
<br>
<br>
<br>
<br>
제19조( 회원의 ID 및 비밀번호에 대한 의무)
<br>
<br>
 ① 제17조의 경우를 제외한 ID와 비밀번호에 관한 관리책임은 회원에게 있습니다.
<br>
<br>
 ② 회원은 자신의 ID 및 비밀번호를 제3자에게 이용하게 해서는 안됩니다.
<br>
<br>
 ③ 회원이 자신의 ID 및 비밀번호를 도난 당하거나 제3자가 사용하고 있음을 인지한 경우에는 바로 "몰"에 통보하고 "몰"의 안내가 있는 경우에는 그에 따라야 합니다.
<br>
<br>
<br>
<br>
<br>
제20조(이용자의 의무)
<br>
<br>
 ① 이용자는 다음 행위를 하여서는 안됩니다.
<br>
<br>
   1. 신청 또는 변경시 허위내용의 등록
<br>
<br>
   2. "몰"에 게시된 정보의 변경
<br>
<br>
   3. "몰"이 정한 정보 이외의 정보(컴퓨터 프로그램 등)의 송신 또는 게시
<br>
<br>
   4. "몰" 기타 제3자의 저작권 등 지적재산권에 대한 침해
<br>
<br>
   5. "몰" 기타 제3자의 명예를 손상시키거나 업무를 방해하는 행위
<br>
<br>
   6. 외설 또는 폭력적인 메시지·화상·음성 기타 공서 양속에 반하는 정보를 몰에 공개 또는 게시하는 행위
<br>
<br>
   7. 타인의 정보 도용
<br>
<br>
<br>
<br>
<br>
제21조(연결"몰"과 피연결"몰" 간의 관계)
<br>
<br>
 ① 상위 "몰"과 하위 "몰"이 하이퍼링크(예: 하이퍼링크의 대상에는 문자, 그림 및 동화상 등이 포함됨)방식 등으로 연결된 경우, 전자를 연결 "몰"(웹 사이트)이라고 하고 후자를 피연결 "몰"(웹사이트)이라고 합니다.
<br>
<br>
 ② 연결 "몰"은 피연결 "몰"이 독자적으로 제공하는 재화·용역에 의하여 이용자와 행하는 거래에 대해서 보증책임을지지 않는다는 뜻을 연결 "몰"의 사이트에서 명시한 경우에는 그 거래에 대한 보증책임을지지 않습니다.
<br>
<br>
<br>
<br>
<br>
제22조(저작권의 귀속 및 이용제한)
<br>
<br>
 ① "몰"이 작성한 저작물에 대한 저작권 기타 지적재산권은 "몰"에 귀속합니다.
<br>
<br>
 ② 이용자는 "몰"을 이용함으로써 얻은 정보를 "몰"의 사전 승낙 없이 복제, 송신, 출판, 배포, 방송 기타 방법에 의하여 영리목적으로 이용하거나 제3자에게 이용하게 하여서는 안됩니다.
<br>
<br>
 ③ “몰”은 약정에 따라 이용자에게 귀속된 저작권을 사용하는 경우 당해 이용자에게 통보하여야 합니다.
<br>
<br>
 ④ '몰' 내에서 이용자가 작성한 상품평, 리뷰 등 상품 및 서비스와 관련된 모든 컨텐츠의 소유권은 '몰'에 귀속됩니다.
<br>
<br>
<br>
<br>
<br>
제23조(적립 및 포인트 운영)
<br>
<br>
 ① "몰" 서비스의 효율적 이용 및 운영을 위해 사전 공지 후 "포인트" 의 일부 또는 전부를 조정할 수 있으며, "포인트" 는 회사가 정한 기간에 따라 주기적으로 소멸할 수 있습니다.
<br>
<br>
<br>
<br>
<br>
제24조(분쟁해결)
<br>
<br>
 ① "몰"은 이용자가 제기하는 정당한 의견이나 불만을 반영하고 그 피해를 보상처리하기 위하여 피해보상처리기구를 설치·운영합니다.
<br>
<br>
 ② "몰"은 이용자로부터 제출되는 불만사항 및 의견은 우선적으로 그 사항을 처리합니다. 다만, 신속한 처리가 곤란한 경우에는 이용자에게 그 사유와 처리일정을 즉시 통보해 드립니다.
<br>
<br>
 ③ "몰"과 이용자간에 발생한 분쟁은 전자거래기본법 제28조 및 동 시행령 제15조에 의하여 설치된 전자거래분쟁조정위원회의 조정에 따를 수 있습니다.
<br>
<br>
<br>
<br>
<br>
제25조(재판권 및 준거법)
<br>
<br>
 ① "몰"과 이용자간에 발생한 전자거래 분쟁에 관한 소송은 민사소송법상의 관할법원에 제기합니다.
<br>
<br>
 ② "몰"과 이용자간에 제기된 전자거래 소송에는 한국 법을 적용합니다.
    
    

</div>
<br><br>
<div class="title">
    <span>회원가입</span>
</div><br>
<div>
    <form id="actionForm" action="/member/Register" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" name="naverLogin" value="${user.naverLogin}" />
    <table class="joinform">
        <tr>
        <td class="joinsort">ID</td>
        <td class="joininputbox">
            <input type="text" placeholder="아이디 입력" name="userid" id="input_id" value="${user.userid}" class="joininput" required="required">
            <input type="button" value="ID 중복 체크" onclick="idCheck()"><span id="checkId"></span>

        </td>
 
        </tr>
        <tr>
        <td class="joinsort">패스워드</td>
        <td colspan="2" class="joininputbox">
            <input type="password" placeholder="비밀번호를 입력하세요" name="userpw" id="password" class="joininput" required="required"> 
            <button type="button" onclick="chkPW()">확인<span id="check"></span>
        </td>
        </tr>
        
        <tr>
        <td class="joinsort">이메일</td>
        <td colspan="2">
            <input class="mail_input" name="useremail" required="required">
            <input type="button" value="email 중복 체크" onclick="emailCheck()"><span id="checkEmail"></span>
            <button type="button" class="mail_check_button" >인증번호 전송</button>
        </td>
        </tr>
        
        <tr>
        <td class="joinsort">인증 확인</td>
        <td colspan="2" class="joininputbox">
            <input type="text" class="mail_check_input" required="required">
            <span id="mail_check_input_box_warn"></span>
        </td>
        </tr>
        
        <tr>
        <td class="joinsort">이름</td>
        <td colspan="2" class="joininputbox">
            <input type="text" placeholder="이름을 입력하세요" name="username" value="${user.username}" class="joininput" required="required">
        </td>
        </tr>
    <!--     <tr>
        <td class="joinsort">전화번호</td>
        <td colspan="2" class="joininputbox">
            <input type="text" placeholder="전화번호를 입력하세요" name="tel" class="joininput">
        </td>
        </tr>
        <tr>
        <td class="joinsort">집주소</td>
        <td colspan="2" class="joininputbox">
            <input type="text" placeholder="집주소를 입력하세요" name="addr" class="joininput">
        </td>
        </tr> -->
  </form>
        <tr>
        <td colspan="3" align="center">
        	<button type="submit" value="가입하기" class="joinsubmit" >가입하기</button>
         	<button id='move' type="submit" value="취소" class="cancel" >취소하기</button>
        </td>
     
        </tr>
    </table>
  
</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
	
	<script type="text/javascript">
	var finalCheck = false;
	var booleanEmail = false;
	
	$(".joinsubmit").on("click",function(e){
		e.preventDefault();
		console.log(finalCheck);
		
		if(finalCheck==true){
			$("#actionForm").submit();
		}else if(finalCheck==false){
			alert("해당 조건을 다 충족 해주시길 바랍니다.");
			return;
		}
	});
	
	var code=""; //이메일 인증번호 저장코드
	
<!-- 이메일 인증번호 전송==========================================   -->
	$(".mail_check_button").click(function(e){
		e.preventDefault();
		var email= $(".mail_input").val(); //입력한 이메일
		var checkBox = $(".mail_check_input").val(); //인증번호 입력란
		var boxWrap = $(".mail_check_input_box").val();
		console.log(booleanEmail);
		if(booleanEmail==true){
		alert("인증번호가 전송되었습니다.");
		$.ajax({
			type:'get',
			dataType: "json", 
			contentType: "application/json; charset=UTF-8",
			url: "/member/mailCheck?email="+email,
			success:function(data){
				code=data;
				
			}		
		});
		booleanEmail=true;
		finalCheck=true;
		}else if(booleanEmail==false){
			alert("이메일 조건을 모두 충족해주세요");
			finalCheck=false;
		}
	});
	
	<!--인증번호 비교======================================== -->
	$(".mail_check_input").blur(function(){
		var inputCode = $(".mail_check_input").val(); //인증 입력 코드
		var checkResult = $("#mail_check_input_box_warn"); //비교결과
		console.log(inputCode);
		  if(inputCode == code && inputCode.length>0){                            // 일치할 경우
		        checkResult.html("인증번호가 일치합니다.");
		        checkResult.attr("class", "correct");        
		        finalCheck=true;
		  }else {                                            // 일치하지 않을 경우
		        checkResult.html("인증번호를 다시 확인해주세요.");
		        checkResult.attr("class", "incorrect");
		        finalCheck=false;
		    }  
	});
	/*입력 이메일 유효성 검사*/
	function mailFormCheck(email){
		 var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		 if(form.test(email)==true){
			 finalCheck=true;
			 booleanEmail=true;
		 }else{
			 finalCheck=false;
			 booleanEmail=false;
		 }
		 return form.test(email);
	}
	
	
	$("#move").on("click", function(e){
		e.preventDefault();
		self.location="/board/list";
	});
	
	<!-- 비밀번호 유효성 체크 알림  -->
	function chkPW(){
		 var pw = $("#password").val();
		 var num = pw.search(/[0-9]/g);
		 var eng = pw.search(/[a-z]/ig);
		 var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
		 if(pw.length < 10 || pw.length > 20){
		  alert("10자리 ~ 20자리 이내로 입력해주세요.");
		  finalCheck=false;
		  return false;
		 }else if(pw.search(/\s/) != -1){
		  alert("비밀번호는 공백 없이 입력해주세요.");
		  finalCheck=false;
		  return false;
		 }else if( (num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0) ){
		  alert("영문,숫자, 특수문자 중 2가지 이상을 혼합하여 입력해주세요.");
		  finalCheck=false;
		  return false;
		 }else {
			 alert("사용가능한 비밀번호입니다.");
			console.log("통과");	 
		 }
		 finalCheck=true;
		}
	
       // id 중복 알림========================================
		function idCheck() { // 
			var userid = $('input[name=userid]').val();
			var check=document.getElementById("checkId");
			
			//value: 태그의 value 값을 가져올때
			//innerhtml: 태그사이에 있는 값을 가져올때 ex)<span>이메일</span>    <p>아이디</p>
			if(userid.length == 0){
				alert("아이디를 입력해주세요");
				finalCheck=false;
				return;
			}else if(mailFormCheck()==false){
				alert("이메일 타입이 아닙니다. 다시 입력해주세요");
				booleanEmail=false;
				return;
			}
			
			//ajaxCall 메소드 구현
			$.ajax({
				type : "get", //데이터 전송방식
				url : "/member/idCheckService?userid="+userid, //데이터를 전송할 서버 파일 이름
				dataType: "json", 
				contentType: "application/json; charset=UTF-8",
				success : function(data) {
					if(data == 1){
						console.log(data);
						alert("아이디가 이미 존재합니다");
						check.innerHTML="불가능한 ID입니다";
						finalCheck=false;
						return;
					}else if(data == 0){
						console.log(data);
						alert("사용 가능한 아이디입니다.")
						check.innerHTML="가능한 ID입니다";
					}
					 finalCheck=true;
				},
				error : function() {
					alert("요청실패");
				}
			});
		}//End idCheck
		
		// 이메일 중복 알림========================================
		function emailCheck() { // 
			var useremail = $('input[name=useremail]').val();
			var check=document.getElementById("checkEmail");
			
			//console.log(useremail);
			//value: 태그의 value 값을 가져올때
			//innerhtml: 태그사이에 있는 값을 가져올때 ex)<span>이메일</span>    <p>아이디</p>
			if(useremail.length == 0){
				alert("이메일을 입력해주세요");
				finalCheck=false;
				return;
			}
			
			//ajaxCall 메소드 구현
			$.ajax({
				type : "get", //데이터 전송방식
				url : "/member/emailCheckService?useremail="+useremail, //데이터를 전송할 서버 파일 이름
				dataType: "json",
				contentType: "application/json; charset=UTF-8",
				success : function(data) {
					if(data > 0){
						console.log(data);
						alert("이미 사용중인 이메일입니다.");
						check.innerHTML="사용 불가능한 이메일입니다.";
						finalCheck=false;
						return;
					}else{
						console.log(data);
						alert("사용 가능한 이메일입니다.")	
						check.innerHTML="사용 가능한 이메일입니다.";
					}
					finalCheck=true;
					booleanEmail=true;
					
				},
				error : function() {
					alert("요청실패");
				}
			});
			console.log(booleanEmail);
		}//End emailCheck
	</script>
<!------------------------------ ▼ 하단 Footer ▼ ------------------------------>

<div id="footers"></div>

<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

<script type="text/javascript">   

</script>

<!------------------------------ ▲ 하단 Footer ▲ ------------------------------>
</body>
</html>