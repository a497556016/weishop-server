package com.weishop.service.impl;

import com.weishop.pojo.CommonFile;
import com.weishop.properties.FileServerProperties;
import com.weishop.global.BusType;
import com.weishop.global.FileType;
import com.weishop.mapper.CommonFileMapper;
import com.weishop.service.ICommonFileService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

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
public class CommonFileServiceImpl extends ServiceImpl<CommonFileMapper, CommonFile> implements ICommonFileService {
	@Autowired
	private FileServerProperties fileServerProperties;

	@Override
	public CommonFile selectImageByBus(String busType, Integer busId) {
		EntityWrapper<CommonFile> wrapper = new EntityWrapper<>();
		wrapper.eq(CommonFile.BUS_TYPE, busType);
		wrapper.eq(CommonFile.BUS_ID, busId);
		wrapper.eq(CommonFile.FILE_TYPE, FileType.IMAGE.getType());
		CommonFile cf = this.selectOne(wrapper);
		if(null!=cf) {
			cf.setFilePath(fileServerProperties.getUrl()+cf.getFilePath());
		}
		return cf;
	}

	@Override
	public List<CommonFile> selectFilesByBus(String busType, Integer busId, String fileType) {
		EntityWrapper<CommonFile> wrapper = new EntityWrapper<>();
		wrapper.eq(CommonFile.BUS_TYPE, busType);
		wrapper.eq(CommonFile.BUS_ID, busId);
		wrapper.eq(CommonFile.FILE_TYPE, fileType);
		return this.selectList(wrapper);
	}

	@Override
	public List<CommonFile> selectImagesByBus(String busType, Integer busId) {
		return this.selectFilesByBus(busType, busId, FileType.IMAGE.getType());
	}
	
}
