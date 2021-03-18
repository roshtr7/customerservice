# customerservice


| Method    |   Path    			| 		 Description 				|
| :---      |     :---:     		|          ---: 					|
|POST		| /customer				|	Create Customer         	    |
|GET		| /customer/sim			|	Retrieve Customer Sims			| 
|POST		| /customer/link-sim	|	Link sim to Customer 			|
|POST		| /sim 					|	Create Sim 						|
|GET		| /sim/all				|	Retrieve all Sims               |


<br /> 
<br /> 
<br /> 
How to run:- <br /> 
1. checkout project using command - git clone -b master https://github.com/roshtr7/customerservice.git <br /> 
2. Change database config in application.yml <br /> 
3. Change csv location in applicaton.yml <br /> 
4. Deploy using a stadalone tomcat server or you can run war using internal tomcat with command - java -jar <Path>/customerservice-0.0.1.war.war <br /> 

