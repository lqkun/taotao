package com.taotao.service.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.fabric.xmlrpc.base.Data;
import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public EasyUIResult getContentList(long catId, Integer page, Integer rows) {
		//根据category_id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(catId);
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		//取分页信息
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIResult result = new EasyUIResult(pageInfo.getTotal(), list);
		return result;
	}

	@Override
	public TaotaoResult saveContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		
		int ok = contentMapper.insert(content);
		
		if(ok>0){
			return TaotaoResult.ok(content);
		}
		return null;
	}

	@Override
	public TaotaoResult updateContent(TbContent content) {			
		content.setUpdated(new Date());
		int ok = contentMapper.updateByPrimaryKeyWithBLOBs(content);
		if(ok>0){
			return TaotaoResult.ok(content);
		}
		return null;
	}

	@Override
	public TaotaoResult deleteContent(String ids) {
		List<Long> idlist = new ArrayList<>();
		String []str=ids.split(",");
		for (String string : str) {
			idlist.add(Long.parseLong(string));
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(idlist);
		int ok = contentMapper.deleteByExample(example);
		if(ok>0){
			
			return TaotaoResult.ok(null);
		}
		return null;
	}

	
}
