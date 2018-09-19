package com.taotao.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;

@RequestMapping(value="/item/cat/")
@Controller
public class ItemCatController {
  
	@Autowired
	private ItemCatService itemCatService;		
	@RequestMapping("/list")
	@ResponseBody
	//如果id为null是使用默认值，也就是parentid为0的分类列表
	public List<EUTreeNode> categoryList(@RequestParam(value="id", defaultValue="0") Long parentId) throws Exception {
		List<EUTreeNode> catList= itemCatService.getItemCatList(parentId);
		return catList;
	}
	
}
