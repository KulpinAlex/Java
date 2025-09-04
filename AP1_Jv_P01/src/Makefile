clean:
	./gradlew clean
	rm -rf .gradle bin

build: clean
	./gradlew shadowJar

run: build
	java -jar build/libs/RogueS21-1.0-SNAPSHOT-all.jar --scan


#install on OS Linux
#NCURSES_PATH := $(shell sudo find /usr/lib/x86_64-linux-gnu/ -name "*libncurses.so.*" | head -1)

#install:
#	 sudo ln -s $(NCURSES_PATH) /usr/lib/x86_64-linux-gnu/libncurses.so.5
#	 sudo cp libs/libjcurses.so /usr/lib/x86_64-linux-gnu/

