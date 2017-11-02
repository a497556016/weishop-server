package com.weishop.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.weishop.base.BaseController;
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
public class CommonFileController extends BaseController<CommonFileServiceImpl,CommonFile>  {
	
}
