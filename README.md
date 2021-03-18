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
4. Run initial_setup.sql <br /> 
5. Deploy using a stadalone tomcat server or you can run war using internal tomcat with command -> java -jar filePath/customerservice-0.0.1.war.war <br /> 



# scheduled-jobs

1. birthday-notification -> Send email to customer wishing advance happy bithday <br/>
2. Customer-export -> send csv file having data of all customers having their birthday on the day <br/> 
