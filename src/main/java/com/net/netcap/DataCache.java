package com.net.netcap;

import java.util.Map;
import java.util.Map.Entry;

import com.net.netcap.handler.ConnectToPlatform;

import jpcap.NetworkInterface;

public class DataCache {

	private static Map<String, Object> projectMap;
	
	private static Map<String, NetworkInterface> devicesMap;
	
	private static String projectName;
	
	private static String netDevicesName;

	private static String captureUrl;
	
	public static Map<String, Object> getProjectMap(){
		if(null == DataCache.projectMap || DataCache.projectMap.isEmpty()){
			DataCache.projectMap = ConnectToPlatform.getProjectMap();
		}
		return DataCache.projectMap;
	}
	
	public static Map<String, NetworkInterface> getDevicesMap(){
		if(null == DataCache.devicesMap || DataCache.devicesMap.isEmpty()){
			DataCache.devicesMap = NetDevice.getNetDeviceMap();
		}
		return DataCache.devicesMap;
	}
	
	public static String getProjectName() {
		if(null != projectName){
			int indexSep = projectName.lastIndexOf("_");
			if(indexSep >= 0){
				String projectId = projectName.substring(indexSep + 1, projectName.length()).trim();
				if(projectId.matches("^\\d+$"))
					return projectName;
			}
		}
		if(null != DataCache.getProjectMap() && DataCache.getProjectMap().size() > 0) {
			Entry<String, Object> firstProject = DataCache.getProjectMap().entrySet().iterator().next();
			projectName = firstProject.getValue().toString() + "_" + firstProject.getKey();
			return projectName;
		} else {
			return projectName;
		}
	}
	
	public static void setProjectName(String projectName) {
		DataCache.projectName = projectName;
	}
	
	public static String getNetDevicesName() {
		if(null != netDevicesName){
			return netDevicesName;
		} else if(null != DataCache.devicesMap && DataCache.devicesMap.size() > 0) {
			netDevicesName = DataCache.devicesMap.entrySet().iterator().next().getKey().toString();
			return netDevicesName;
		} else {
			return netDevicesName;
		}
	}

	public static void setNetDevicesName(String netDevicesName) {
		DataCache.netDevicesName = netDevicesName;
	}

	public static String getCaptureUrl() {
		return captureUrl;
	}

	public static void setCaptureUrl(String captureUrl) {
		DataCache.captureUrl = captureUrl;
	}
}