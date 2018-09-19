package com.taotao.service;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {
	
	TaotaoResult getItemParamByCid(long cid);

	public TaotaoResult saveItemParam(long cid, String itemParam);
	
	public EasyUIResult getItemParamList(Integer page, Integer rows) throws Exception ;
	
	TaotaoResult deleteItemParamByIds(String ids);

}
