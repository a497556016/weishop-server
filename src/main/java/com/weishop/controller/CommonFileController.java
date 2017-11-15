package com.weishop.controller;


import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.weishop.base.BaseController;
import com.weishop.base.BaseResponse;
import com.weishop.pojo.CommonFile;
import com.weishop.service.impl.CommonFileServiceImpl;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HeShaowei
 * @since 2017-10-27
 */
@RestController
@RequestMapping("//commonFile")
public class CommonFileController extends BaseController<CommonFileServiceImpl,CommonFile> {
	
	@RequestMapping("/selectImageByBus")
	@ResponseBody
	public BaseResponse<CommonFile> selectImageByBus(String busType,Integer busId){
		CommonFile commonFile = this.baseService.selectImageByBus(busType, busId);
		return BaseResponse.result(commonFile);
	}
	
	@RequestMapping("/selectImagesByBus")
	@ResponseBody
	public BaseResponse<List<CommonFile>> selectImagesByBus(String busType,Integer busId){
		List<CommonFile> commonFiles = this.baseService.selectImagesByBus(busType, busId);
		return BaseResponse.result(commonFiles);
	}
}
