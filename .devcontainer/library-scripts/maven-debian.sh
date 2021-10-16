#!/bin/bash
###########################################################
RED='\033[0;31m' # Red
NC='\033[0m' # No Color CAP
###########################################################
apt-get update -y \
&& apt-get install -y curl
# Download Maven
curl -fsSL https://dlcdn.apache.org/maven/maven-3/3.8.3/binaries/apache-maven-3.8.3-bin.tar.gz \
-o /tmp/apache-maven-3.8.3-bin.tar.gz
wait $!
# Locate file has here: https://maven.apache.org/download.cgi
SHA256SUM="1c12a5df43421795054874fd54bb8b37d242949133b5bf6052a063a13a93f13a20e6e9dae2b3d85b9c7034ec977bbc2b6e7f66832182b9c863711d78bfe60faa"
# Check file has after download and 
if [[ $(sha512sum /tmp/apache-maven-3.8.3-bin.tar.gz | awk '{print $1}') =~ ^(${SHA256SUM})$ ]]; 
then
    printf "\n${RED}File verfication PASSED...\n\tSetting up MAVEN...${NC}\n"
    sleep 3
    tar xzvf /tmp/apache-maven-3.8.3-bin.tar.gz  -C  /opt/
else
    printf "\n${RED}File verification failed...${NC}\n" && \
    ls -lia /tmp/**
    exit 1
fi
export M2_HOME=/opt/apache-maven-3.8.3
export M2=${M2_HOME}/bin
export PATH=${M2}:${PATH}
export MAVEN_OPTS='-Xms256m -Xmx512m'
ln -s  $M2/mvn /usr/local/bin
mvn -v 






