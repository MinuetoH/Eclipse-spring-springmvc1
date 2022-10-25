package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService;
/*
 * @Component : 객체화 대상이 되는 클래스
 * Controller 기능 : url의 요청시 호출되는 클래스 
 */
@Controller  //@Component + Controller 기능
@RequestMapping("item") //http://localhost:8088/springmvc1/item
public class ItemController {
	@Autowired  //ShopService 객체를 주입 
	private ShopService service;
	//http://localhost:8088/springmvc1/item/list 요청시 호출되는 메서드
	@RequestMapping("list")
	public ModelAndView list() {
		//ModelAndView : 데이터+뷰 정보 저장 
		ModelAndView mav = new ModelAndView();
		//itemList : db의 item 테이블의 모든 데이터를 Item 객체들로 저장 객체
		List<Item> itemList = service.itemList();
		mav.addObject("itemList",itemList); //데이터 저장.
		//뷰의 이름은 기본적으로 요청 url 정보로 설정 : "item/list" 설정
		return mav;	
	}
	//http://localhost:8088/springmvc1/item/detail?id=1
	@RequestMapping("detail") 
	public ModelAndView detail(Integer id) {
		//request.getParameter("id") : id 매개변수명 == 파라미터이름
		ModelAndView mav = new ModelAndView();
		Item item = service.getItem(id);
		mav.addObject("item",item);
		return mav;
	}
}