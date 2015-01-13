package com.herziger.schalter;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

public class BasicAWSCredentialsProvider implements AWSCredentialsProvider {
	private BasicAWSCredentials gBAWSc;

	public BasicAWSCredentialsProvider(BasicAWSCredentials bwC) {
		gBAWSc = bwC;
		// getCredentials(bwC);
	}

	public AWSCredentials getCredentials(BasicAWSCredentials bwC) {

		return bwC;
		// TODO Auto-generated method stub

		// return BasicAWSCredentialsProvider;
	}

	@Override
	public void refresh() {
	}

	@Override
	public AWSCredentials getCredentials() {
		return gBAWSc;
	}
}
