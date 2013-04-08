run: OnboardSystem.nxj
	#nxjupload -r -b -d ${NXT} Controller.nxj
	nxjupload -r -u Controller.nxj

%.class: %.java
	nxjc $<

%.nxj: %.class
	nxjlink -o $@ $(patsubst %.class,%,$<)

clean:
	rm -rf *.nxj *.class

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
