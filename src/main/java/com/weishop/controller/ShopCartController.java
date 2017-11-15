package com.weishop.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.weishop.base.BaseController;
import com.weishop.base.BaseResponse;
import com.weishop.dto.ShopCartDTO;
import com.weishop.pojo.CommonFile;
import com.weishop.pojo.Product;
import com.weishop.pojo.ProductItem;
import com.weishop.pojo.ShopCart;
import com.weishop.pojo.enums.BusType;
import com.weishop.properties.FileServerProperties;
import com.weishop.service.ICommonFileService;
import com.weishop.service.IProductItemService;
import com.weishop.service.IProductService;
import com.weishop.service.IShopCartService;
import com.weishop.service.impl.ShopCartServiceImpl;
import com.weishop.utils.PropertyUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-17
 */
@Controller
@RequestMapping("//shopCart")
public class ShopCartController extends BaseController<ShopCartServiceImpl, ShopCart> {
	@Autowired
	private IProductItemService productItemService;
	@Autowired
	private IProductService productService;
	@Autowired
	private ICommonFileService commonFileService;
	
	
	@RequestMapping("/queryUserShopCart")
	@ResponseBody
	public BaseResponse<List<ShopCartDTO>> queryUserShopCart(Integer userId){
		EntityWrapper<ShopCart> wrapper = new EntityWrapper<>();
		wrapper.eq(ShopCart.USER_ID, userId);
		List<ShopCart> shopCarts = this.baseService.selectList(wrapper);
		List<ShopCartDTO> shopCartDTOs = PropertyUtils.convertModelToDTO(shopCarts, ShopCartDTO.class);
		for (ShopCartDTO shopCartDTO : shopCartDTOs) {
			ProductItem productItem = productItemService.selectById(shopCartDTO.getProItemId());
			shopCartDTO.setModel(productItem.getModel());
			shopCartDTO.setSize(productItem.getSize());
			
			EntityWrapper<Product> productWrapper = new EntityWrapper<>();
			productWrapper.eq(Product.CODE, productItem.getpCode());
			Product product = productService.selectOne(productWrapper);
			PropertyUtils.convertModelToDTO(product, shopCartDTO, new String[] {"id"});
			
			CommonFile cf = commonFileService.selectImageByBus(BusType.PRODUCT.getType(),product.getId());
			if(null!=cf) {
				shopCartDTO.setPicUrl(cf.getFilePath());
			}
		}
		return BaseResponse.result(shopCartDTOs);
	}
}
