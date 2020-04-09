# This Makefile is a one-stop shop for the administration of this project rather than
# being a true dependency driven Makefile. 


RELEASE = 0.3

JAVA_SRC_FILES = $(shell cd java/src; find . -iname '*.java')

# When doing a github release, I want to add the assets for 
# 	* the Java source code and jarfile
#	* the Python library
# This command builds these in the _build folder, which is ignored by .gitignore.
.PHONEY: github-release
github-release:
	rm -rf _build/
	mkdir -p _build/
	$(MAKE) release-python3
	$(MAKE) release-java


.PHONEY: clean
clean:
	rm -rf _build/bin
	rm -f _build/*.jar
	rm -f _build/*.py

.PHONEY: release-python3
release-python3:
	mkdir -p _build
	cp python3/minxml.py _build/minxml-$(RELEASE).py


.PHONEY: release-java
release-java:
	$(MAKE) -C java autodocs
	$(MAKE) -C java release-java
	cp java/_build/*.jar _build
