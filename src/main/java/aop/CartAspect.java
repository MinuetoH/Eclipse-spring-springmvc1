package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import exception.LoginException;
import logic.Cart;
import logic.User;

@Component
@Aspect
public class CartAspect {
	@Before("execution(* controller.Cart*.check*(..)) && args(.., session)")
	public void cartCheck(HttpSession session) throws Throwable {
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException("회원만 주문 가능합니다. 로그인하세요","../user/login");
		}
		Cart cart = (Cart)session.getAttribute("CART");
		if(cart == null || cart.getItemSetList().size() == 0) {
			throw new LoginException("장바구니에 주문 상품이 없습니다.","../item/list");
		}
	}
}
