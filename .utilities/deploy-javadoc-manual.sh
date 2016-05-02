#!/usr/bin/env bash

REPO="https://github.com/atomicpages/Quake-Watcher.git"
DIR="temp-clone"
TEMP="temp-docs"
DOCS_PATH="build/docs/javadoc/"
ARCHIVE_NAME="javadoc.zip"

# Clone the repo
git clone $REPO $DIR

# Build the docs
gradle clean javadoc

# Create an archive
zip -r -X $ARCHIVE_NAME $DOCS_PATH

# Copy the archive
cp -R javadoc.zip ~/$TEMP/

# Checkout gh-pages
git checkout -t origin/gh-pages

mv ~/$TEMP/$ARCHIVE_NAME .

unzip $ARCHIVE_NAME

git add . && git add -u && git commit -m "Website at $(date)"

git push origin gh-pages

# Clean up
cd ~
rm -rf $DIR $TEMP