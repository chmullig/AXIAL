
JARS = /Applications/Processing.app/Contents/Resources/Java/core.jar:/Applications/Processing.app/Contents/Resources/Java/modes/java/libraries/opengl/library/opengl.jar:/Applications/Processing.app/Contents/Resources/Java/modes/java/libraries/opengl/library/gluegen-rt.jar:/Applications/Processing.app/Contents/Resources/Java/modes/java/libraries/opengl/library/jogl.jar:/Users/chmullig/Documents/Processing/libraries/GLGraphics/library/GLGraphics.jar:/Users/chmullig/Documents/unfolding/dist/unfolding.0.9.3.jar:/Users/chmullig/Documents/unfolding/dist/Unfolding/library/log4j-1.2.15.jar:/Users/chmullig/Documents/unfolding/dist/Unfolding/library/json4processing.jar:.

NATIVE_OPENGL = /Applications/Processing.app/Contents/Resources/Java/modes/java/libraries/opengl/library/macosx

SRC_FILES = \
	src/Axial.java \
	src/AxialMarkerManager.java \
	src/FoursquareMarker.java


build/Axial.jar: $(SRC_FILES) Makefile
	javac -cp $(JARS) $(SRC_FILES) 
	jar -Mcvf library/Axial.jar src/*.class

test: build/Axial.jar
	java -cp $(JARS):src:library -Djava.library.path=$(NATIVE_OPENGL) Axial	

data/foursquare.geojson:
	curl http://project-axial.herokuapp.com/api/pull_4sq_all
	curl http://project-axial.herokuapp.com/api/geojson_all > data/foursquare.geojson

frames/frame-000000.tif: build/Axial.jar
	java -cp $(JARS):src:library -Djava.library.path=$(NATIVE_OPENGL) Axial

video.mp4: frames/frame-000000.tif
	ffmpeg -f image2 -r 25 -i shitty_demo/frames/frame-%06d.tif -r 25 -i ~/Dropbox/05\ -\ Empire\ State\ Of\ Mind\ \[Jay-Z\ +\ Alicia\ Keys\]\ \
(Explicit\).mp3  -c:v libx264 -pix_fmt yuv420p -threads 4 --shortest video.mp4

clean:
	rm -f src/*.class
	rm -f build/*
	rm -f frames/*
	rm video.mp4
