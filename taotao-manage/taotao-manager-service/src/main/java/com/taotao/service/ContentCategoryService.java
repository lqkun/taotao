package com.taotao.service;
import java.util.List;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	
	public List<EUTreeNode> getContentCategoryList(long pid);
	
	TaotaoResult createContentCategory(long parentId,String name);
	
	TaotaoResult deleteContentCategory(long id);
	
	TaotaoResult updateContentCategory(long id,String name);
	
	

}
