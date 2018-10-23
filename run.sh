#!/bin/bash

cd server
mvn package -q
mvn exec:java -q
cd ..

