package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	@ResponseBody
	@RequestMapping("/query/itemcatid/{cid}")
	public TaotaoResult queryCatalogById(@PathVariable long cid) throws Exception {
		//根据分类id查询列表
		TaotaoResult result = itemParamService.getItemParamByCid(cid);
		return result;
	}
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult saveItemParam(@PathVariable Long cid, String paramData) throws Exception {
		TaotaoResult result = itemParamService.saveItemParam(cid, paramData);
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult getItemParamList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "30") Integer rows) throws Exception {
		//查询列表
		EasyUIResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteItemParam(@RequestParam String ids) throws Exception {
		//删除列表
		TaotaoResult result = itemParamService.deleteItemParamByIds(ids);
		return result;
	}
}
