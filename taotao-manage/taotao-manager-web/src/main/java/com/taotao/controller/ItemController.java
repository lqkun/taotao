package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemService;

/**
 * 商品管理Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月2日上午10:52:46
 * @version 1.0
 */

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService descService;
	/**
	 * @Description:(根据itemId查询商品)
	 * @param itemId
	 * @return     
	 * @author:Lqkun
	 * @date:2018年9月18日下午4:08:08
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	/**
	 * @Description:(根据itemId查询商品描述)
	 * @param itemId
	 * @return     
	 * @author:Lqkun
	 * @date:2018年9月18日下午4:08:08
	 */
	@RequestMapping("/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDescById(@PathVariable Long itemId) {
		TaotaoResult taotaoResult = descService.getItemDescByItemId(itemId);	
		return taotaoResult;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception {
		TaotaoResult result = itemService.createItem(item, desc, itemParams);
		return result;
	}
	@RequestMapping(value="/rest/item/update", method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult updateItem(TbItem item, String desc,long itemParamId, String itemParams) throws Exception {
		TaotaoResult result = itemService.updateItem(item, desc,itemParamId, itemParams);
		return result;
	}
	@RequestMapping(value="/rest/item/delete", method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult deleteItem(@RequestParam String ids)  {
		TaotaoResult result = itemService.deleteItem(ids);
		return result;
	}
	
}
