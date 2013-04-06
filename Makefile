RobotCommTest.class: RobotCommTest.java
	nxjc RobotCommTest.java

RobotCommTest.nxj: RobotCommTest.class
	nxjlink -o RobotCommTest.nxj RobotCommTest

RobotCommTest: RobotCommTest.nxj
	nxjupload -r -b -d ${NXT} RobotCommTest.nxj

BaseCommTest.class: BaseCommTest.java
	nxjpcc BaseCommTest.java

BaseCommTest: BaseCommTest.class
	nxjpc BaseCommTest
