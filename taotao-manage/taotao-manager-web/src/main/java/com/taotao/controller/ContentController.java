package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
@RequestMapping(value="/content")
@Controller
public class ContentController {
	
	@Autowired	
	private ContentService contentService;
	@RequestMapping(value="/query/list")
	@ResponseBody
	public EasyUIResult getContentList(long categoryId,Integer page, Integer rows){
		
		try {
			EasyUIResult result = contentService.getContentList(categoryId, page, rows);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value="/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent content){
		
		TaotaoResult result = contentService.saveContent(content);
		
		return result;
	}
	
	@RequestMapping(value="/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content){
		
		TaotaoResult result = contentService.updateContent(content);
		
		return result;
	}
	@RequestMapping(value="/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids){
		
		TaotaoResult result = contentService.deleteContent(ids);
		
		return result;
	}
	
	
}
