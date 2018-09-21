package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EUTreeNode> getContentCategoryList(long parentid) {
		
		//根据parentid查询内容分类列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			
			//判断是否是父节点
			if (tbContentCategory.getIsParent()) {
				node.setState("closed");
			} else {
				node.setState("open");
			}
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult createContentCategory(long parentId,String name) {
		
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setParentId(parentId);
		contentCategory.setIsParent(false);
		//'状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		int ok = contentCategoryMapper.insert(contentCategory);
		if(ok<=0){
			return TaotaoResult.build(500, "添加节点失败！");
		}
		//查看父节点的isParent列是否为true，如果不是true 改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult deleteContentCategory( long id) {
		
		
		//判断此节点是不是父节点
		TbContentCategory ContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		
		if(ContentCategory!=null&&ContentCategory.getIsParent()){
			
			//删除其所有子节点
			TbContentCategoryExample example = new TbContentCategoryExample();
			example.createCriteria().andParentIdEqualTo(id);
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
			for (TbContentCategory tbContentCategory : list) {
				if(tbContentCategory.getIsParent()){
					deleteContentCategory(tbContentCategory.getId());				
				}				
				//删除id对应的记录		
				contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
			}	
			//删除id对应的记录	
			contentCategoryMapper.deleteByPrimaryKey(id);
			TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(ContentCategory.getParentId());
			parentCat.setIsParent(false);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}else{
			//删除id对应的记录		
			contentCategoryMapper.deleteByPrimaryKey(id);
			//parentid对应的记录下是否有子节点。如果没有子节点。需要把parentid对应的记录的isparent改成false。
			TbContentCategoryExample example = new TbContentCategoryExample();
			example.createCriteria().andParentIdEqualTo(ContentCategory.getParentId());
			List<TbContentCategory> list =contentCategoryMapper.selectByExample(example);
			
			if(list!=null && list.size()>0){			
				
			}else{			
				TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(ContentCategory.getParentId());
				parentCat.setIsParent(false);
				//更新父节点
				contentCategoryMapper.updateByPrimaryKey(parentCat);
			}
			
		}
		return TaotaoResult.ok(null);
	}

	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		
		TbContentCategory  category = contentCategoryMapper.selectByPrimaryKey(id);
		
		category.setName(name);
		
		int ok = contentCategoryMapper.updateByPrimaryKey(category);
		if(ok>0){
			return TaotaoResult.ok(null);
		}
		return null;
	}
}