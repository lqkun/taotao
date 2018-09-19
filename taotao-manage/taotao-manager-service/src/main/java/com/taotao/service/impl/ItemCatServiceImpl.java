package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService{
   
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public List<EUTreeNode> getItemCatList(Long parentId) {
				
		List<EUTreeNode> ls = new ArrayList<>();
		TbItemCatExample example = new TbItemCatExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		//根据parentid查询子节点
		criteria.andParentIdEqualTo(parentId);
		//返回子节点列表
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		for (TbItemCat tbItemCat : list) {
			EUTreeNode eUTreeNode = new EUTreeNode();
			eUTreeNode.setId(tbItemCat.getId());
			eUTreeNode.setText(tbItemCat.getName());			
			//如果是父节点的话就设置成关闭状态，如果是叶子节点就是open状态
			eUTreeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			
			ls.add(eUTreeNode);
		}
		return ls;
	}

}
