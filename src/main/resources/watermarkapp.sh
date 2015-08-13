#!/bin/bash
user_dir="/Users/victor/workspace/watermark/src/main/resources"
log4j_dir="file:/Users/victor/workspace/watermark/target/log4j.properties"
export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home"
java -jar -Duser_dir=$user_dir -Dlog4j.configuration=$log4j_dir  watermark-watermarkapp.jar