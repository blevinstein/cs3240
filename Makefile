run: OnboardSystem.nxj
	#nxjupload -r -b -d ${NXT} $<
	nxjupload -r -u $<

%.class: %.java
	nxjc $<

%.nxj: %.class
	nxjlink -o $@ -od $(patsubst %.class,%.nxd,$<) $(patsubst %.class,%,$<)

clean:
	rm -rf *.nxj *.class
