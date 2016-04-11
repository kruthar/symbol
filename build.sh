#!/usr/bin/env bash

VERSION=0.1-beta
PROJECT_DIR=symbol-${VERSION}
JAR=symbol-${VERSION}.jar
TAR=symbol-${VERSION}.tar.gz

# Build the jar
mvn package

# Create a project dir
rm -rf ${PROJECT_DIR}
mkdir ${PROJECT_DIR}
cp -r bin ${PROJECT_DIR}/
cp target/${JAR} ${PROJECT_DIR}/
cp README.md ${PROJECT_DIR}/

# tar it up
tar -cvzf ${TAR} ${PROJECT_DIR}

# remove the project dir
rm -rf ${PROJECT_DIR}