package com.weishop.service.impl;

import com.weishop.pojo.Order;
import com.weishop.pojo.OrderList;
import com.weishop.dto.OrderDTO;
import com.weishop.mapper.OrderListMapper;
import com.weishop.mapper.OrderMapper;
import com.weishop.mapper.ShopCartMapper;
import com.weishop.service.IOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
	@Autowired
	private OrderListMapper orderListMapper;
	@Autowired
	private ShopCartMapper shopCartMapper;
	@Override
	public void createOrder(OrderDTO orderDto) {
		Order order = orderDto.getOrder();
		order.setCreateTime(new Date());
		this.baseMapper.insert(order);
		List<OrderList> orderList = orderDto.getOrderList();
		for (OrderList ol : orderList) {
			ol.setOrderId(order.getId());
			orderListMapper.insert(ol);
		}
		
		//删除购物车数据
		int i = shopCartMapper.deleteBatchIds(orderDto.getDelShopCartIds());
		if(i!=orderDto.getDelShopCartIds().size()) {
			throw new RuntimeException("删除购物车数据异常，应删除"+orderDto.getDelShopCartIds().size()+"条，实际删除"+i+"条！s");
		}
	}
	
}
