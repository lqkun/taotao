package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService categoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCategoryList(@RequestParam(value="id", defaultValue="0")long parentid) throws Exception {
		List<EUTreeNode> list = categoryService.getContentCategoryList(parentid);
		
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(long parentId,String name) throws Exception {
		
		TaotaoResult result = categoryService.createContentCategory(parentId,name);
		
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody                                  
	public TaotaoResult deleteContentCategory(long id) throws Exception {
		

		TaotaoResult result = categoryService.deleteContentCategory(id);
		
		return result;
	}
	@RequestMapping("/update")
	@ResponseBody                                  
	public TaotaoResult updateContentCategory(long id,String name) throws Exception {
		

		TaotaoResult result = categoryService.updateContentCategory(id, name);
		
		return result;
	}
}
