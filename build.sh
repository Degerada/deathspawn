#!/bin/sh

## Configs
serverLoc=/home/oliver/minecraft-server/paper1.14
pluginDest=$serverLoc/plugins/

# Build jar file
./gradlew fatJar

# Delete if already exists
if [ -e "pluginDest" ]; then
    rm -rf pluginDest
fi

# Copy created jar file
cp build/deathspawn* $pluginDest