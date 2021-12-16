setup:
		gradle wrapper --gradle-version 7.2

clean:
		./gradlew clean

install: clean
		./gradlew install

run-dist:
		./build/install/app/bin/app $(args)

run:
		./gradlew run

lint:
		./gradlew checkstyleMain checkstyleTest

.PHONY: test
test:
		./gradlew test

report:
		./gradlew jacocoTestReport

check-updates:
		./gradlew dependencyUpdates

.PHONY: build
build: clean lint test
		./gradlew build
