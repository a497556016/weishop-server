package com.weishop.service;

import com.weishop.pojo.CommonFile;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-27
 */
public interface ICommonFileService extends IService<CommonFile> {

	CommonFile selectImageByBus(String busType, Integer busId);
	
	List<CommonFile> selectImagesByBus(String busType, Integer busId);
	
	List<CommonFile> selectFilesByBus(String busType, Integer busId, String fileType);
	
}
