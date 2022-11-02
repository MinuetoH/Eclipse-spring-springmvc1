package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Cart;
import logic.Item;
import logic.ItemSet;
import logic.Sale;
import logic.ShopService;
import logic.User;

@Controller
@RequestMapping("cart")
public class CartController {
	@Autowired
	private ShopService service;
	//http://localhost:8088/springmvc1/cart/cartAdd?id=10&quantity=1
	@RequestMapping("cartAdd")
	public ModelAndView add
		(Integer id, Integer quantity, HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");	//뷰이름
		Item item = service.getItem(id);	//상품정보를 db에서 읽어오기
		//session의 "CART" 속성에 장바구니 정보를 저장.
		Cart cart = (Cart)session.getAttribute("CART");
		if(cart == null) {
			cart = new Cart();
			session.setAttribute("CART", cart);	//CART 객체 속성에 등록
		}
		//cart의 itemSetList에 추가
		//itemSetList 존재하는 상품이 추가되는 경우 수량만 증가시킴
		cart.push(new ItemSet(item,quantity));
		mav.addObject("cart",cart);
		mav.addObject
			("message",item.getName()+":"+quantity+"개 장바구니 추가");
		return mav;
	}
	//http://localhost:8088/springmvc1/cart/carDelete?index=0
	@RequestMapping("cartDelete")
	public ModelAndView delete(int index, HttpSession session) {
		//index : cart.getItemSetList() 의 인덱스
		ModelAndView mav = new ModelAndView("cart/cart");
		Cart cart = (Cart)session.getAttribute("CART");
		//요소 List.remove(index) : 삭제되는 요소를 리턴
		//delSet : 삭제되는 ItemSet 객체
		ItemSet delSet = cart.getItemSetList().remove(index);
		mav.addObject("cart", cart);
		mav.addObject("message", delSet.getItem().getName() + "이(가) 장바구니에서 삭제");
		return mav;
	}
	@RequestMapping("cartView")
	public ModelAndView view(HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		Cart cart = (Cart)session.getAttribute("CART");
		mav.addObject("cart",cart);
		return mav;
		
	}
	//AOP 클래스 : CartAspect 클래스
	//로그인이 되어야 실행가능하도록 AOP 부분 추가하기
	//장바구니에 주문상품이 없는 경우 실행불가 AOP 부분 추가하기
	@RequestMapping("checkout")
	public String checkout(HttpSession session) {
		return null;
	}
	/* 주문 확정 : end 요청
	 * 1. 로그인, 장바구니상품 검증 필요 => aop로 설정. 
	 * 2. 장바구니 상품을 saleitem 테이블에 저장하기
	 * 3. 로그인 정보로 주문 정보(sale)테이블에 저장.
	 * 4. 장바구니 상품 제거
	 * 5. 주문 정보 end.jsp 페이지로 출력
	 */
	@RequestMapping("end")
	public ModelAndView checkend(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Cart cart = (Cart)session.getAttribute("CART");
		User loginUser = (User)session.getAttribute("loginUser");
		Sale sale = service.checkend(loginUser, cart);
		session.removeAttribute("CART");
		mav.addObject("sale",sale);
		return mav;
	}
}
