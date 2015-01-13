package com.herziger.schalter;

import java.util.ArrayList;
import java.util.Properties;

public class SchalterConfiguration {
	public static String accessKey;
	public static String secretKey;
	public static String[] action;
	public static ArrayList<String> instanceID;
	public static String[] EC2Endpoint;

	// [Yuval H] Single static method, receiving data from the properties file
	public static void appendConfigurations(Properties mainProperties) {
		
		instanceID = new ArrayList<String>();
		accessKey = mainProperties.getProperty("aws.accesskey");
		secretKey = mainProperties.getProperty("aws.secretkey");
		EC2Endpoint = mainProperties.getProperty("aws.endpoints").split(",");
		action = mainProperties.getProperty("aws.action").split(",");
		for (String _instanceId : mainProperties.getProperty("aws.instance-id").split(",")) { instanceID.add(_instanceId.trim()); }
	}
}
