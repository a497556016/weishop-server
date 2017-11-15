package com.weishop.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.weishop.base.BaseController;
import com.weishop.base.BaseResponse;
import com.weishop.base.PageResponse;
import com.weishop.dto.ProductDTO;
import com.weishop.global.ResponseCode;
import com.weishop.pojo.CommonFile;
import com.weishop.pojo.Product;
import com.weishop.pojo.ProductItem;
import com.weishop.pojo.enums.BusType;
import com.weishop.properties.FileServerProperties;
import com.weishop.service.ICommonFileService;
import com.weishop.service.IProductItemService;
import com.weishop.service.IProductService;
import com.weishop.service.impl.ProductServiceImpl;
import com.weishop.utils.PropertyUtils;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-17
 */
@RestController
@RequestMapping("//product")
public class ProductController extends BaseController<ProductServiceImpl,Product> {
	@Autowired
	private ICommonFileService commonFileService;
	
	@RequestMapping("/listProduct")
	@ResponseBody
	public BaseResponse<Map<String, List<ProductDTO>>> listProduct(){
	
		Map<String, List<ProductDTO>> result = Maps.newHashMap();
		result.put("p1", selectProductList(1,10));
		result.put("p2", selectProductList(2,10));
		return BaseResponse.result(result);
	}
	
	private List<ProductDTO> selectProductList(int proType,int size){
		EntityWrapper<Product> entityWrapper = new EntityWrapper<>();
		entityWrapper.eq(Product.PRO_TYPE, proType);
		Page<Product> page1 = this.baseService.selectPage(new Page<>(1, size),entityWrapper);
		List<Product> p1 = page1.getRecords();
		List<ProductDTO> pDto1 = PropertyUtils.convertModelToDTO(p1, ProductDTO.class);
		for (ProductDTO product : pDto1) {
			product.setImages(commonFileService.selectImagesByBus(BusType.PRODUCT.getType(),product.getId()));
			product.setPicUrl(product.getImages().size()>0?product.getImages().get(0).getFilePath():null);
		}
		return pDto1;
	}
	
}
