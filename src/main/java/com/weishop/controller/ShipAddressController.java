package com.weishop.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.weishop.base.BaseController;
import com.weishop.base.BaseResponse;
import com.weishop.global.ResponseCode;
import com.weishop.pojo.ShipAddress;
import com.weishop.service.IShipAddressService;
import com.weishop.service.impl.ShipAddressServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-17
 */
@RestController
@RequestMapping("//shipAddress")
public class ShipAddressController extends BaseController<ShipAddressServiceImpl,ShipAddress> {
	
	@RequestMapping("/selectUserShipAddress")
	@ResponseBody
	public BaseResponse<List<ShipAddress>> selectUserShipAddress(Integer userId) {
		if(null==userId) {
			return BaseResponse.error(String.format("用户Id不能为空！"));
		}
		EntityWrapper<ShipAddress> wrapper = new EntityWrapper<>();
		wrapper.eq(ShipAddress.USER_ID, userId);
		List<ShipAddress> addresses = this.baseService.selectList(wrapper);
		return BaseResponse.result(addresses);
	}
	
	@RequestMapping("/setDefaultShipAddress")
	@ResponseBody
	public BaseResponse<?> setDefaultShipAddress(Integer id,Integer userId){
		this.baseService.setDefaultShipAddress(id,userId);
		return BaseResponse.success();
	}
	
}
