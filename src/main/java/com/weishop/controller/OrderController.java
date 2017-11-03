package com.weishop.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import com.weishop.base.BaseController;
import com.weishop.pojo.Order;
import com.weishop.service.impl.OrderServiceImpl;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-17
 */
@Controller
@RequestMapping("//order")
public class OrderController extends BaseController<OrderServiceImpl, Order> {
	
}	
