package com.weishop.service;

import com.weishop.pojo.ShipAddress;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-27
 */
public interface IShipAddressService extends IService<ShipAddress> {
	void setDefaultShipAddress(Integer id,Integer  userId);
}
