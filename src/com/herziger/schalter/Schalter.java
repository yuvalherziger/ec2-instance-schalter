package com.herziger.schalter;

import java.util.ArrayList;

// Apache commons:
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// AWS Java SDK imports:
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;

public class Schalter {
	// Instantiating the initial class logger:
	private static Log log = LogFactory.getLog(Schalter.class);
	
	public static void main(String[] args) {
		try {
			String configFile = args[0];
			ConfigFileLoader configFileLoader = new ConfigFileLoader();
			configFileLoader.with(configFile);
			configFileLoader.loadProperties();
			log.info("Reading configuration file from: " + configFile);
			// executing the task and logging results (TRUE = success, FALSE =
			// failure):
			log.info(performTask() ? "###### Operation completed successfully, exiting ######"
					: "###### Operation ended, errors occurred ######");
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.info("ERROR: The application did not read any configuration file path in its arguments");
		} catch (Exception ex) {
			log.info("ERROR: " + ex.toString());
		}
		finally { 
			log.info("Task ended.");
		}
	}

	private static AWSCredentialsProvider createAWSCredentials(
			String accessKey, String secretKey) {
		return new BasicAWSCredentialsProvider(new BasicAWSCredentials(
				accessKey, secretKey));
	}

	public static boolean performTask() {
		// Instantiating AWSCredentialsProvider, this object is necessary for
		// successful creation of AWS client objects (in this case it's EC2):
		AWSCredentialsProvider credentials = createAWSCredentials(
				SchalterConfiguration.accessKey,
				SchalterConfiguration.secretKey);

		// Iterating on the image ID's array that has been retrieved from the
		// configuration:
		for (int i = 0; i < SchalterConfiguration.instanceID.size(); i++) {
			log.info("Treating instance with instance ID : "
					+ SchalterConfiguration.instanceID.get(i));
			log.info(SchalterConfiguration.action[0]);
			try {
				AmazonEC2Client amazonEC2Client = new AmazonEC2Client(credentials);
				amazonEC2Client.setEndpoint(SchalterConfiguration.EC2Endpoint[i]);
				ArrayList<String> instanceIdContainer = new ArrayList<String>();
				instanceIdContainer.add(SchalterConfiguration.instanceID.get(i));
				if (SchalterConfiguration.action[i].equals("START")) {
					log.info("Beginning attempt to START the instance");
					StartInstancesRequest startInstancesRequest = new StartInstancesRequest();
					startInstancesRequest.setInstanceIds(instanceIdContainer);
					StartInstancesResult startInstancesResult = amazonEC2Client
							.startInstances(startInstancesRequest);
					log.info("Request result: " + startInstancesResult.getStartingInstances());
				}
				if (SchalterConfiguration.action[i].equals("STOP")) {
					log.info("Beginning attempt to STOP the instance");
					StopInstancesRequest stopInstancesRequest = new StopInstancesRequest();
					stopInstancesRequest.setInstanceIds(instanceIdContainer);
					StopInstancesResult stopInstancesResult = amazonEC2Client
							.stopInstances(stopInstancesRequest);
					log.info("Request result: "
							+ stopInstancesResult.getStoppingInstances());
				}
				// if not the last instance in the sequence,
				// allowing ec2 client 1.5 sec to settle between requests
				if (i < SchalterConfiguration.instanceID.size() - 1)
					Thread.sleep(1500);
			} catch (Exception ex) {
				log.info("ERROR: " + ex.toString());
				return false;
			}
		}
		return true;
	}
}
