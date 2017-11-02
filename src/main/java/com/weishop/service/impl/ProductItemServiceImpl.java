package com.weishop.service.impl;

import com.weishop.pojo.CommonFile;
import com.weishop.pojo.Product;
import com.weishop.pojo.ProductItem;
import com.weishop.pojo.ShopCart;
import com.weishop.dto.ModelSizeDTO;
import com.weishop.dto.ShopCartDTO;
import com.weishop.global.BusType;
import com.weishop.mapper.ProductItemMapper;
import com.weishop.mapper.ProductMapper;
import com.weishop.mapper.ShopCartMapper;
import com.weishop.service.ICommonFileService;
import com.weishop.service.IProductItemService;
import com.weishop.utils.PropertyUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-27
 */
@Service
public class ProductItemServiceImpl extends ServiceImpl<ProductItemMapper, ProductItem> implements IProductItemService {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ShopCartMapper shopCartMapper;
	@Autowired
	private ICommonFileService commonFileService;
	
	@Override
	public ModelSizeDTO selectModelSizeByProduct(String code) {
		ModelSizeDTO modelSizeDTO = new ModelSizeDTO();
		List<String> models = this.baseMapper.selectModels(code);
		List<String> sizes = this.baseMapper.selectSizes(code);
		modelSizeDTO.setModel(models);
		modelSizeDTO.setSize(sizes);
		return modelSizeDTO;
	}

	@Override
	public ShopCartDTO putInCart(String code, String model, String size,int count, Integer userId) {
		ShopCartDTO shopCartDTO = new ShopCartDTO();
		shopCartDTO.setCount(count);
		
		EntityWrapper<ProductItem> wrapper = new EntityWrapper<>();
		wrapper.eq(ProductItem.P_CODE, code);
		wrapper.eq(ProductItem.MODEL, model);
		wrapper.eq(ProductItem.SIZE, size);
		List<ProductItem> productItems = this.baseMapper.selectList(wrapper);
		if(CollectionUtils.isNotEmpty(productItems)) {
			ProductItem productItem = productItems.get(0);
			if(productItem.getStoreTotal()-count<=0) {
				throw new RuntimeException("抱歉，库存已经不足！");
			}else {
				//判断该商品以及规格型号是否已经存在于购物车，如果存在就更新数量，不存在则插入
				ShopCart shopCart = new ShopCart();
				shopCart.setUserId(userId);
				shopCart.setProItemId(productItem.getId());
				ShopCart sc = shopCartMapper.selectOne(shopCart);
				if(null!=sc) {
					sc.setCount(sc.getCount()+count);
					shopCartMapper.updateById(sc);
					shopCartDTO.setId(sc.getId());
				}else {
					Product product = new Product();
					product.setCode(code);
					product = productMapper.selectOne(product);
					PropertyUtils.convertModelToDTO(product, shopCartDTO, new String[] {"id"});
					
					shopCart.setCount(count);
					shopCartMapper.insert(shopCart);
					shopCartDTO.setId(shopCart.getId());
					
					shopCartDTO.setProItemId(productItem.getId());
					shopCartDTO.setUserId(userId);
					shopCartDTO.setModel(model);
					shopCartDTO.setSize(size);
					
					CommonFile cf = commonFileService.selectImageByBus(BusType.PRODUCT.getType(),product.getId());
					if(null!=cf) {
						shopCartDTO.setPicUrl(cf.getFilePath());
					}
				}
				
			}
		}else {
			throw new RuntimeException("抱歉，该商品已经下架！");
		}
		return shopCartDTO;
	}

	@Override
	public List<String> getSizeByModel(String code, String model) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("code", code);
		param.put("model", model);
		return this.baseMapper.getSizeByModel(param);
	}

	@Override
	public List<String> getModelBySize(String code, String size) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("code", code);
		param.put("size", size);
		return this.baseMapper.getModelBySize(param);
	}
	
}
