package com.biz.product.persistence;

import java.util.List;

import com.biz.product.domain.ProductDTO;

public interface ProductDao {
	
	public List<ProductDTO> selectAll();
	public ProductDTO selectByPCode(String p_code);
	public List<ProductDTO> selectByPName(String p_name);
	
	public int insert(ProductDTO productDTO);
	public int update(ProductDTO productDTO);
	public int delete(String p_code);

}
