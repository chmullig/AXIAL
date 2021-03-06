
JARS = lib

NATIVE_OPENGL = libNative/macosx

SRC_FILES = \
	src/Axial.java \
	src/AxialMarkerManager.java \
	src/FoursquareUserMarker.java \
	src/TextFeature.java \
	src/FeatureManager.java \
	src/Position.java \
	src/Featurable.java \
	src/ImageFeature.java

build/Axial.jar: $(SRC_FILES) Makefile
	javac -cp $(JARS)/*:. $(SRC_FILES) 
	jar -Mcvf build/Axial.jar -C src .

test: build/Axial.jar
	java -Xms2048m -Xmx2048m -cp $(JARS)/*:build/*:. -Djava.library.path=$(NATIVE_OPENGL) Axial --test

data/foursquare.geojson:
	curl http://project-axial.herokuapp.com/api/pull_4sq_all
	curl http://project-axial.herokuapp.com/api/geojson_all > data/foursquare.geojson

frames/frame-000010.tif: build/Axial.jar
	java -Xms2048m -Xmx2048m -cp $(JARS)/*:build/*:. -Djava.library.path=$(NATIVE_OPENGL) Axial

video.mp4: frames/frame-000010.tif
	ffmpeg -f image2 -r 30 -i frames/frame-%06d.tif -r 30 -i ~/Dropbox/05\ -\ Empire\ State\ Of\ Mind\ \[Jay-Z\ +\ Alicia\ Keys\]\ \(Explicit\).mp3  -c:v libx264 -pix_fmt yuv420p -threads 4 -shortest -y video.mp4

gitshots.mp4:
	ffmpeg -f image2 -r 5 -i '~/.gitshots/%*.jpg' -r 30 -q:v 2 -y gitshots.mp4


remove_frames:
	rm -f frames/*

clean:
	rm -f src/*.class
	rm -f build/*
	rm -f frames/*
	rm video.mp4
