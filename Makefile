setup:
		gradle wrapper --gradle-version 7.2

clean:
		./gradlew clean

install: clean
		./gradlew install

run-dist:
		./build/install/app/bin/app $(args)

check-updates:
		./gradlew dependencyUpdates

lint:
		./gradlew check

.PHONY: test
test:
		./gradlew test

report:
		./gradlew jacocoTestReport

.PHONY: build
build: clean lint test
		./gradlew build
