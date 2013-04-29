run: src/control/gui/DebugInterface.class
	nxjpc -cp src control/gui/DebugInterface 

%.class: %.java
	nxjpcc -sourcepath src $<

clean:
	rm -rf src/*/*/*.class
