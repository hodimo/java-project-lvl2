setup:
		gradle wrapper --gradle-version 7.2

clean:
		./gradlew clean

install:
		./gradlew clean install

run-dist:
		./build/install/app/bin/app

check-updates:
		./gradlew dependencyUpdates
