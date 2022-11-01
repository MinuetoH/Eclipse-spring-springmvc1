<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- /springmvc1/src/main/webapp/WEB-INF/view/user/password.jsp
   1. password.jsp 페이지 화면에 출력
     - 로그인 여부 확인하기. :UserLoginAspect.userLoginCheck() 메서드 실행
                         1. 메서드이름 : loginCheck*  시작
                         2. 매개변수 : session 객체 
--%>   
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>비밀번호 변경</title>
<script type="text/javascript">
   function inchk(f) {
	   if(f.chgpass.value != f.chgpass2.value) {
		   alert("변경 비밀번호와 변경 비밀번호 재입력이 다릅니다.");
		   f.chgpass2.value="";
		   f.chgpass2.focus();
		   return false; 
	   }
	   //chgpass 값이 3자리 미만경우 오류 발생하기
	   let passlen = f.chgpass.value.length
	   if(passlen < 3 || passlen > 10) {
		   alert("변경 비밀번호는 3자 이상 10자 이하 이어야 합니다.");
		   f.chgpass.focus();
		   return false; 
	   }
	   return true;
   }</script>
 </head><body> 
<form action="password" method="post" name="f" 
       onsubmit="return inchk(this)">
<table class="w3-table"><caption>비밀번호 변경</caption>
  <tr><th>현재 비밀번호</th><td><input type="password" name="password"></td></tr>
  <tr><th>변경 비밀번호</th><td><input type="password" name="chgpass"></td></tr>
  <tr><th>변경 비밀번호 재입력</th><td><input type="password" name="chgpass2"></td></tr>
  <tr><td colspan="2"><input type="submit" value="비밀번호 변경"></td></tr>
</table></form></body></html>