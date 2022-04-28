package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;

public class ListPurchaseAction extends Action{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Search search = new Search();
		HttpSession session = request.getSession();
		
		int page =1;
		if(request.getParameter("page")!=null)
			page = Integer.parseInt(request.getParameter("page"));
		
		search.setCurrentPage(page);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		String buyer = ((User)session.getAttribute("user")).getUserId();
		//String buyer = (String) request.getAttribute("user");
		System.out.println("====ListPurchaseAction");
		System.out.println(buyer);
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Map<String,Object> map = purchaseService.getPurchaseList(search,buyer);
			
		Page resultPage = 
				new Page(page,((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction:: "+resultPage);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		System.out.println("========<<map>>=======");
		System.out.println(map);
		return "forward:/purchase/listPurchase.jsp";
	}
}
