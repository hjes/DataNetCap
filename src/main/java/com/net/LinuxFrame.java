package com.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.net.common.util.LogUtil;
import com.net.netcap.DataCache;
import com.net.netcap.captor.CaptureThread;

public class LinuxFrame {
	
	private static Class<?> cl = LinuxFrame.class;
	
	public LinuxFrame(){
		Map<Integer, String> deviceMap = new HashMap<Integer, String>();
		int i = 0;
		for (String key : DataCache.getDevicesMap().keySet()) {
			deviceMap.put(i, key);
			LogUtil.console(cl, i + ":" + key);
			i++;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		LogUtil.console(cl, "Please input the sequence to choose a network card:");
		try {
			str = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogUtil.console(cl, "Your choice is :" + deviceMap.get(Integer.valueOf(str)));
		DataCache.setNetDevicesName(deviceMap.get(Integer.valueOf(str)));

		if(null != DataCache.getProjectMap() && DataCache.getProjectMap().size() > 0){
			Map<Integer, String> projectMap = new HashMap<Integer, String>();
			int j = 0;
			for (String key : DataCache.getProjectMap().keySet()) {
				projectMap.put(j, DataCache.getProjectMap().get(key).toString());
				LogUtil.console(cl, j + ":" + key + "-" + DataCache.getProjectMap().get(key));
				j++;
			}
			
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			String str2 = null;
			LogUtil.console(cl, "Please input the sequence to choose a project:");
			try {
				str2 = br2.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			LogUtil.console(cl, "Your choice is :" + projectMap.get(Integer.valueOf(str2)));
			DataCache.setProjectName(projectMap.get(Integer.valueOf(str2)));
		}
		
		BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
		String urlFilter = null;
		LogUtil.console(cl, "Please input the url Filters(Multiple filtering conditions are separated by \",\"):");
		try {
			urlFilter = br3.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogUtil.console(cl, "Your url Filters is :" + urlFilter);
		DataCache.setCaptureUrl(urlFilter);

		CaptureThread.getInstance("CaptureThread").start();
	}
}