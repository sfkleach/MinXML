# This Makefile is a one-stop shop for the administration of java implementation rather than
# being a true dependency driven Makefile. 

# This is a shortcut for building the docs. I took the decision that I wanted the
# docs to go in the repository so that people downloading it could see it. This 
# predated the rise in popularity of maven/gradle that makes this irrelevant.
.PHONEY: autodocs
autodocs:
	javadoc -quiet -d doc -sourcepath src -subpackages com.steelypip.powerups -cp org.eclipse.jdt.annotation_2.0.0.v20140415-1436.jar

.PHONEY: release-java
release-java:
	mkdir -p _build/bin
	(cd src; javac -cp ../org.eclipse.jdt.annotation_2.0.0.v20140415-1436.jar -d ../_build/bin $(JAVA_SRC_FILES))
	(cd _build; jar cf minxml.jar-$(RELEASE) -C bin .)

.PHONEY: clean
clean:
	rm -rf _build
	mkdir -p _build
