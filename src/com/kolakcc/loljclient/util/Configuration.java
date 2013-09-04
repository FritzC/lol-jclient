package com.kolakcc.loljclient.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	public static String PVPVersion = "3.11.13_08_30_19_21";
	private static Properties config = null;
	private static File configFile = null;
	
	private static void initializeProperties() {
		config = new Properties();
		configFile = FileSystem.getFile("app://data/configuration.properties");
		try {
			if (!configFile.exists()) configFile.createNewFile();
			config.load(new FileInputStream(configFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getDefaultRegion() {
		if (config == null) initializeProperties();
		return config.getProperty("region", "");
	}
	
	public static String getDefaultUsername() {
		if (config == null) initializeProperties();
		return config.getProperty("username", "");
	}
	
	public static String getDefaultPassword() {
		if (config == null) initializeProperties();
		return config.getProperty("password", "");
	}
	
	public static String getLeagueDirectory() {
		//TODO: remove useless code FileSystem checks for already
		if (config == null) initializeProperties();
		String uncheckedDirectory = config.getProperty("leaguedir", "C:\\Riot Games\\League of Legends\\");
		if (!uncheckedDirectory.endsWith(File.separator)) uncheckedDirectory = uncheckedDirectory + File.separator;
		config.put("leaguedir",uncheckedDirectory);
		Configuration.flushConfig();
		return uncheckedDirectory;
	}
	
	public static String getXMPPStatus() {
		if (config == null) initializeProperties();
		return config.getProperty("status", "");
	}
	
	public static boolean checkLeagueDirectory() {
		File f = new File(Configuration.getLeagueDirectory());
		return (f.isDirectory() && f.canRead());
	}
	
	public static void setStatus(String status) {
		if (config == null) initializeProperties();
		config.put("status", status);
		Configuration.flushConfig();
	}
	
	public static void setLeagueDirectory(File dir) {
		if (config == null) initializeProperties();
		config.put("leaguedir", dir.getAbsolutePath());
		Configuration.flushConfig();
	}
	
	public static void flushConfig() {
		if (config == null) initializeProperties();
		try {
			config.store(new FileOutputStream(configFile), "");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void set(String key, String value) {
		if (config == null) initializeProperties();
		config.put(key, value);
		Configuration.flushConfig();
	}
}
