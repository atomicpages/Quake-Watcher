#!/usr/bin/env bash

REPO="https://github.com/atomicpages/Quake-Watcher.git"
DIR="temp-clone"
TEMP="temp-docs"
ARCHIVE_NAME="javadoc.zip"

# Clone the repo
git clone $REPO ~/$DIR

cd ~/$DIR

# Build the docs
gradle clean javadoc

# Create an archive
cd build/docs/javadoc/
zip -r -X $ARCHIVE_NAME .

if [ ! -d ~/$TEMP ]; then
	mkdir ~/$TEMP
fi

# Move the archive
mv $ARCHIVE_NAME ~/$TEMP/$ARCHIVE_NAME

cd ~/$DIR

# Checkout gh-pages
git checkout -t origin/gh-pages

mv ~/$TEMP/$ARCHIVE_NAME .

unzip $ARCHIVE_NAME

rm $ARCHIVE_NAME

git add . && git add -u && git commit -m "Docs updated at $(date)"

git push origin gh-pages

# Clean up
rm -rf ~/$DIR ~/$TEMP