# BASE IMAGE
FROM ubuntu:18.04

# GENERAL LIBRARIES
RUN apt-get update \
    && apt-get install -y \
    curl \
    vim \
    nodejs \
    npm

RUN /bin/bash -c "npm install -g yarn"