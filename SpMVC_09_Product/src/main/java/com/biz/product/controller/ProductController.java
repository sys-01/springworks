package com.biz.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.product.domain.ProductDTO;
import com.biz.product.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService pService;
	
	/*
	 * RequestMapping의 produces에서 contentType을 text/json으로 바꾸면
	 * 서비스에서 가져온 DTO에 들어있는 값을 자동으로 json형으로 바꿔서 response 하려고 한다
	 * 
	 * 하지만 자바에는 기본적으로 json형으로 바꿔주는 클래스가 없기 때문에
	 * Jackson Databind를 받아서 이용하기로 한다
	 */
	// @ResponseBody
	@RequestMapping(value="plist", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String getAllList(Model model) {
		
		List<ProductDTO> proList = pService.selectAll();
		model.addAttribute("PLIST", proList);
		
		return "p-list";
	}
	
	@ResponseBody
	@RequestMapping(value="plist/name", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public List<ProductDTO> getPNames(String p_name) {
		
		List<ProductDTO> proList = pService.selectByPName(p_name);
		
		return proList;
	}
	
	/*
	 * produces의 content-Type
	 * 서버에서 문자열, 객체 등등의 실제 데이터를 response 할 때
	 * 어떤 type으로 보낼 것인가를 나타내는 문자열
	 */
	@ResponseBody
	@RequestMapping(value="pname", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getPName(String p_code) {
		
		ProductDTO proDTO = pService.selectByPCode(p_code);
		
		//return proDTO.getP_name();
		return "<h1>" + proDTO.getP_name() + "</h1>";
	}
	
	@ResponseBody
	@RequestMapping(value="poprice", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getOPrice(String p_code) {
		
		ProductDTO proDTO = pService.selectByPCode(p_code);
		
		return proDTO.getP_oprice() + "";
	}
	
	@ResponseBody
	@RequestMapping(value="piprice", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getIPrice(String p_code) {
		
		ProductDTO proDTO = pService.selectByPCode(p_code);
		
		return proDTO.getP_iprice() + "";
	}
	
}
