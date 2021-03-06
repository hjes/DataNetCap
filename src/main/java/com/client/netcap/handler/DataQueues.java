package com.client.netcap.handler;

import java.util.LinkedList;

import com.client.common.util.LogUtil;
import com.client.common.util.PropertiesUtil;
import com.client.common.util.StringUtil;
import com.client.netcap.DataCache;
import com.client.protocol.http.HTTPDataBean;
import com.client.protocol.http.HttptHelper;
import com.client.service.ConnectToPlatform;

public class DataQueues {
	private static Class<?> cl = DataQueues.class;

	public static LinkedList<Task> queue = new LinkedList<Task>();
	
    /**
     * 假如 参数t 为任务
     * @param t
     */
    public static void add (Task t){
        synchronized (DataQueues.queue) {
        	DataQueues.queue.addFirst(t); //添加任务
        	DataQueues.queue.notifyAll();//激活该队列对应的执行线程，全部Run起来
        }
    }
    
    public static class Task{
    	private static Class<?> cl = Task.class;
    	
    	private String reqData;
    	private String rspData;
    	
    	public Task(String reqData, String rspData){
    		this.reqData = reqData;
    		this.rspData = rspData;
    	}
    	
    	/**
    	 * 
    	 */
        public void dataProcess(){
        	HTTPDataBean bean = HttptHelper.getDataBean(reqData, rspData);
        	bean.setProjectId(ConnectToPlatform.getProjectId());
        	bean.setModuleId(ConnectToPlatform.getModuleId());
        	if (bean != null && isValidReq(bean.getUrl())) {
            	LogUtil.debug(cl, bean.toString());
            	ConnectToPlatform.uploadData(bean);
            }
        }
    }
    
    /**
     * 
     * @param url
     * @return
     */
    public static boolean isValidReq(String url){
    	String captureUrl = DataCache.CAPTURE_URL;
    	String excludeUrlSuffix = PropertiesUtil.getProperty("excludeUrlSuffix");
    	if(null == url){
    		return false;
    	}else {
    		if(!StringUtil.isEmpty(excludeUrlSuffix)){
    			for(String suffix : excludeUrlSuffix.split(",")){
    				if(url.endsWith(suffix))
    					return false;
    			}
    		}
    		if(StringUtil.isEmpty(captureUrl) || StringUtil.isBlank(captureUrl)){
    			return true;
    		} else {
    			for(String address : captureUrl.split(",")){
    				LogUtil.debug(cl, "URL--------->" + url);
    				if(url.contains(address.trim())){
    					return true;
    				}
    			} 
    			return false;
    		}
    			
    	}
    }

}
