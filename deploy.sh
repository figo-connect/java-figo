#!/bin/bash

openssl aes-256-cbc -pass pass:$ENCRYPTION_PASSWORD -in pubring.gpg.enc -out pubring.gpg -d
openssl aes-256-cbc -pass pass:$ENCRYPTION_PASSWORD -in secring.gpg.enc -out secring.gpg -d

if [[ $TRAVIS_PULL_REQUEST == "false" ]]; then
    mvn deploy -B -DskipTests=true --settings .travis.settings.xml
    exit $?
fi
