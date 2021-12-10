setup:
		gradle wrapper --gradle-version 7.2

clean:
		./gradlew clean

install: clean
		./gradlew install

run-dist:
		./build/install/app/bin/app

check-updates:
		./gradlew dependencyUpdates

lint:
		./gradlew check

build: lint clean
		./gradlew build
