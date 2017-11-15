package com.weishop.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.weishop.base.BaseController;
import com.weishop.pojo.Comment;
import com.weishop.service.impl.CommentServiceImpl;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HeShaowei
 * @since 2017-11-10
 */
@RestController
@RequestMapping("//comment")
public class CommentController extends BaseController<CommentServiceImpl, Comment> {
	
}
