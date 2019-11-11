# BASE IMAGE
FROM ubuntu:18.04

# GENERAL LIBRARIES
RUN apt-get update \
    && apt-get install -y \
    openjdk-8-jdk \
    curl \
    unzip \
    zip \
    vim

# SDKMAN & GRADLE
RUN curl -s "https://get.sdkman.io" | bash
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install gradle"