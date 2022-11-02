package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import exception.LoginException;
import logic.User;

@Component   //객체화
@Aspect      //AOP 기능의 클래스
public class AdminLoginAspect {
	@Around("execution(* controller.AdminController.*(..)) "
			+ "&& args(.., session)")
	public Object adminCheck(ProceedingJoinPoint joinPoint,
			HttpSession session) throws Throwable {
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException
				("[adminCheck]로그인 후 거래하세요","../user/login");
		} else if(!loginUser.getUserid().equals("admin")) {
			throw new LoginException
				("[adminCheck]관리자만 가능한 거래입니다","../user/mypage?id="+loginUser.getUserid());
		}
		return joinPoint.proceed();
	}
}
