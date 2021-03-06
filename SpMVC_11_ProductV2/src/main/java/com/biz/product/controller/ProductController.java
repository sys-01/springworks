package com.biz.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.biz.product.domain.ProductDTO;
import com.biz.product.service.FileService;
import com.biz.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class ProductController {

	@Autowired
	ProductService pSvc;
	
	@Autowired
	FileService fSvc;
	
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
		
		List<ProductDTO> proList = pSvc.selectAll();
		model.addAttribute("PLIST", proList);
		
		return "home";
	}
	
	
	
	@RequestMapping(value="input", method=RequestMethod.POST)
	public String input(@ModelAttribute ProductDTO proDTO, @RequestParam("u_file") MultipartFile u_file) {
		
		log.debug("상품정보입력 : " + proDTO.toString());
		log.debug("업로드한 파일 : " + u_file.getOriginalFilename());
		
		// 파일을 선택하지 않았으면
		// 파일 업로드에 관련된 코드를 실행하지 않음
		// 업로드할 파일을 선택했을 때만 파일에 관련된 코드 실행
		if(!u_file.isEmpty()) {
			
			/*
			 * update를 수행할 때 이미 업로드된 파일이 있으면 기존의 파일을 삭제하고
			 * 새로운 파일을 업로드 해야하므로 p_file 변수를 검사하여
			 * 값이 있으면 파일을 삭제
			 */
			if(proDTO.getP_file() != null) {
				fSvc.fileDelete(proDTO.getP_file());
			}
			
			/*
			 * web에서 파일이 전송되어 오면 fSvc.fileUp() 메소드에게 파일을 어딘가에 저장해달라고 요청
			 * 정상적으로 파일저장이 완료되면 파일명을 DTO의 p_file 변수에 저장한다 
			 */
			String upFileName = fSvc.fileUp(u_file);
			if(upFileName != null) {
				proDTO.setP_file(upFileName);
			}
		}
		
		int ret = 0;
		if(proDTO.getP_code().isEmpty()) {
			log.debug("새로운 상품등록");
			// insert 실행
			ret = pSvc.insert(proDTO);
		} else {
			log.debug("기존 상품변경");
			// update 실행
			ret = pSvc.update(proDTO);
		}
		
		return "redirect:/plist";
		
	}
	
	@RequestMapping(value="imgDelete", method=RequestMethod.GET)
	public String imgDelete(@RequestParam("p_code") String p_code) {
		
		ProductDTO proDTO = pSvc.selectByPCode(p_code);
		if(proDTO != null && proDTO.getP_file() != null && !proDTO.getP_file().isEmpty()) {
			fSvc.fileDelete(proDTO.getP_file());
			proDTO.setP_file(null);
			pSvc.update(proDTO);
		}
		return "redirect:/plist";
	}
	
	
	/*
	 * @ResponseBody
	 * 결과물을 view(*.jsp)로 변환하지 않고
	 * 문자열 그대로 또는 객체(vo,dto)를 json 형태로 변환하여
	 * 클라이언트에게 response를 수행하는 기능
	 */
	
	/*
	@ResponseBody
	@RequestMapping(value="getProduct", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public ProductDTO getProduct(String p_code) {
		
		ProductDTO proDTO = pService.selectByPCode(p_code);
		
		return proDTO;
	}
	*/
	
	@ResponseBody
	@RequestMapping(value="getString", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
	// @ResponseBody를 사용할 때는 produces를 설정하는 것이 좋다
	// 특히 한글 데이터를 response 할 때는 반드시 charset=UTF-8로 설정해주자
	public String getString(@RequestParam(value="str", // query 문자열
										required=false, // 혹시 값을 보내지 않더라도 오류가 나지 않도록 함
										defaultValue="없음") // 값을 보내지 않으면 없음 이라는 문자열로 세팅
															// required=false와 defaultValue가 없으면 서버는 client에게 400오류를 보내고 처리를 거부한다
															// 또한 VO나 DTO 등 객체에는 적용하면 안된다
											String myStr) {
		
		if(myStr.equals("없음")) {
			return "http://localhost:8080/product/getString?str=문자열 형식으로 보내세요";
		} else {
			return "보낸 문자열 : " + myStr;
		}
	}
	
	@ResponseBody
	@RequestMapping(value="plist/name", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public List<ProductDTO> getPNames(String p_name) {
		
		List<ProductDTO> proList = pSvc.selectByPName(p_name);
		
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
		
		ProductDTO proDTO = pSvc.selectByPCode(p_code);
		
		//return proDTO.getP_name();
		return "<h1>" + proDTO.getP_name() + "</h1>";
	}
	
	@ResponseBody
	@RequestMapping(value="poprice", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getOPrice(String p_code) {
		
		ProductDTO proDTO = pSvc.selectByPCode(p_code);
		
		return proDTO.getP_oprice() + "";
	}
	
	@ResponseBody
	@RequestMapping(value="piprice", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getIPrice(String p_code) {
		
		ProductDTO proDTO = pSvc.selectByPCode(p_code);
		
		return proDTO.getP_iprice() + "";
	}
	
}
