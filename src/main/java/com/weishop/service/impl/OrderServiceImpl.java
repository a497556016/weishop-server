package com.weishop.service.impl;

import com.weishop.pojo.Order;
import com.weishop.pojo.OrderList;
import com.weishop.pojo.ProductItem;
import com.weishop.dto.OrderDTO;
import com.weishop.dto.OrderListDTO;
import com.weishop.mapper.OrderListMapper;
import com.weishop.mapper.OrderMapper;
import com.weishop.mapper.ProductItemMapper;
import com.weishop.mapper.ShopCartMapper;
import com.weishop.service.IOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
	@Autowired
	private OrderListMapper orderListMapper;
	@Autowired
	private ShopCartMapper shopCartMapper;
	@Autowired
	private ProductItemMapper productItemMapper;
	
	@Transactional
	@Override
	public void createOrder(OrderDTO orderDto) {
		Order order = orderDto.getOrder();
		order.setCreateTime(new Date());
		this.baseMapper.insert(order);
		List<OrderListDTO> orderList = orderDto.getOrderList();
		for (OrderListDTO ol : orderList) {
			ol.setOrderId(order.getId());
			orderListMapper.insert(ol);
			
			//计入商品的订单数量
			ProductItem pi = productItemMapper.selectById(ol.getProItemId());
			pi.setOrderCount(pi.getOrderCount()+ol.getCount());
			productItemMapper.updateById(pi);
		}
		
		//删除购物车数据
		int i = shopCartMapper.deleteBatchIds(orderDto.getDelShopCartIds());
		if(i!=orderDto.getDelShopCartIds().size()) {
			throw new RuntimeException("删除购物车数据异常，应删除"+orderDto.getDelShopCartIds().size()+"条，实际删除"+i+"条！s");
		}
	}
	
}
