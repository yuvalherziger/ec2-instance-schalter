package com.herziger.schalter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileLoader {
	private String filePath;
	
	public ConfigFileLoader() {
		
	}
	public void with (String _filePath) { 
		filePath = _filePath;
	}
	public void loadProperties() throws IOException { 
		Properties mainProperties = new Properties();
		FileInputStream file;
		file = new FileInputStream(filePath);
		mainProperties.load(file);
		SchalterConfiguration.appendConfigurations(mainProperties);
		file.close();
	}
}
