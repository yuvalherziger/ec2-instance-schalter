# EC2-instance-schalter
Mini-project for shutting down and booting existing instances on Amazon Web Services EC2

<h3>What is EC2 Instance Schalter?</h3>
EC2 Instance Schalter helps you save money on running EC2 instances on Amazon Web Service.
This mini-project was written in order to schedule shut-downs and starts of EC2 instances during development and testing phases.

<h3>Installation</h3>
<ul>
  <li>Make a clone of the project</li>
  <li>Build it with <code>Schalter.Java</code> as the main class</li>
</ul>

<h3>Configuration</h3>
Make a copy of ```{path-to-project}/resources/configuration/config.properties```.
You should posses credentials to you AWS account (access/secret key), which will allow for the application to authenticate your request. The next parameters, ```aws.instance-id```, ```aws.endpoints``` and ```aws.action``` should contain equally-sized, comma separated lists of the relevant EC2 instances, their regions and the action you would like to perform on them.

```properties
aws.accesskey 		= <access key>
aws.secretkey 		= <secret key>
aws.instance-id		= i-xxxxxx01,i-xxxxxx02,i-xxxxxx03
aws.endpoints 		= ec2.eu-central-1.amazonaws.com,ec2.eu-west-1.amazonaws.com,ec2.eu-west-1.amazonaws.com
aws.action			= START,STOP,START
```
The above example would start the first instance, stop the second one and start the last one. 

<b>Q:</b> What happens if an instance is already in the state to which I'm trying to bring it?

<b>A:</b> Nothing :-)

<h3>Running</h3>
Simply run the following command:
```bash
java -jar {path-and-name-of-jar-file}.jar {path-and-name-of-config-file}.properties 
```
