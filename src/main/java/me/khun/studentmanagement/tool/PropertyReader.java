package me.khun.studentmanagement.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	private Properties properties;
	
	public PropertyReader(String location) {
		var resource = getClass().getResource(location);
		try {
			if (resource == null) {
				throw new FileNotFoundException("No such file or directory : " + location);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.properties = new Properties();
		try {
			properties.load(new FileInputStream(resource.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PropertyReader(File file) {
		this.properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getValue(String key) {
		return properties.getProperty(key);
	}
}