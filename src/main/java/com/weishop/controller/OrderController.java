package com.weishop.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.weishop.base.BaseController;
import com.weishop.base.BaseResponse;
import com.weishop.base.PageResponse;
import com.weishop.dto.OrderDTO;
import com.weishop.dto.OrderListDTO;
import com.weishop.global.ResponseCode;
import com.weishop.pojo.CommonFile;
import com.weishop.pojo.Order;
import com.weishop.pojo.OrderList;
import com.weishop.pojo.Product;
import com.weishop.pojo.enums.BusType;
import com.weishop.service.ICommonFileService;
import com.weishop.service.IOrderListService;
import com.weishop.service.IProductService;
import com.weishop.service.impl.OrderServiceImpl;
import com.weishop.utils.PropertyUtils;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-17
 */
@RestController
@RequestMapping("//order")
public class OrderController extends BaseController<OrderServiceImpl, Order> {
	@Autowired
	private IOrderListService orderListService;
	@Autowired
	private ICommonFileService commonFileService;
	@Autowired
	private IProductService productService;
	
	@RequestMapping("/createOrder")
	@ResponseBody
	public BaseResponse<OrderDTO> createOrder(@RequestBody OrderDTO orderDto){
		this.baseService.createOrder(orderDto);
		return BaseResponse.result(orderDto);
	}
	
	@RequestMapping("/selectUserOrders")
	@ResponseBody
	public PageResponse<OrderDTO> selectUserOrders(Page<Order> page){
		PageResponse<OrderDTO> pageResponse = new PageResponse<>();
		EntityWrapper<Order> orderWrapper = getRequestMapToWrapper();
		orderWrapper.orderBy(Order.CREATE_TIME, false);
		page = this.baseService.selectPage(page, orderWrapper);
		List<OrderDTO> orderDTOs = Lists.newArrayList();
		for(Order order : page.getRecords()) {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrder(order);
			EntityWrapper<OrderList> wrapper = new EntityWrapper<>();
			wrapper.eq(OrderList.ORDER_ID, order.getId());
			wrapper.orderBy(OrderList.CODE);
			List<OrderList> orderLists = orderListService.selectList(wrapper);
			List<OrderListDTO> orderListDtos = PropertyUtils.convertModelToDTO(orderLists, OrderListDTO.class);
			for (OrderListDTO orderListDTO : orderListDtos) {
				EntityWrapper<Product> entityWrapper = new EntityWrapper<>();
				entityWrapper.eq(Product.CODE, orderListDTO.getCode());
				Product p = productService.selectOne(entityWrapper);
				if(null!=p){
					CommonFile cf = commonFileService.selectImageByBus(BusType.PRODUCT.getType(), p.getId());
					orderListDTO.setProPicUrl(cf.getFilePath());
				}
			}
			orderDTO.setOrderList(orderListDtos);
			
			orderDTOs.add(orderDTO);
		}
		pageResponse.setRows(orderDTOs);
		pageResponse.setCode(ResponseCode.SUCCESS.getCode());
		pageResponse.setPageNo(page.getCurrent());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setTotal(page.getTotal());
		return pageResponse;
	}
}	
