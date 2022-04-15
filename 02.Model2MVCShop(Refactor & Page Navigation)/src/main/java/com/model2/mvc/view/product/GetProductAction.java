package com.model2.mvc.view.product;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
public class GetProductAction extends Action{
	
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("test1");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		String no = request.getParameter("prodNo");
		
		
		ProductService service = new ProductServiceImpl();
		Product vo = service.getProduct(prodNo);
		System.out.println("test2");
		System.out.println("get���� number"+no);
//		Cookie c = new Cookie("history",no);
//		response.addCookie(c);
//		System.out.println("cookie�� ��ȣ���� �Ϸ�");
//		Cookie[] cookies = request.getCookies();		
		
		String history;
	      
		Cookie cookie = null;
		System.out.println("test3");  
		Cookie[] cookies = request.getCookies();
		if (cookies!=null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("history")) {
            	
					history = URLDecoder.decode(cookie.getValue(),"euc-kr");
					history +=","+vo.getProdNo();
//					System.out.println(history);
					cookie = new Cookie("history",URLEncoder.encode(history,"euc-kr"));
				}else {
					cookie = new Cookie("history", Integer.toString(prodNo));
				}
				
			}
		}
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		request.setAttribute("vo", vo);
		System.out.println("test4");
		return "forward:/product/readProduct.jsp";
	}

}
