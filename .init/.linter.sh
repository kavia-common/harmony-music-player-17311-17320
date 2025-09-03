#!/bin/bash
cd /home/kavia/workspace/code-generation/harmony-music-player-17311-17320/music_player_mobile_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

