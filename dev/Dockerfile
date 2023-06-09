FROM node:16-bullseye-slim
RUN apt update && apt upgrade -y
RUN apt install openjdk-17-jdk openjdk-17-jre git python3 build-essential musl-dev -y
RUN ln -s /usr/lib/x86_64-linux-musl/libc.so /lib/libc.musl-x86_64.so.1
RUN npm config set -g strict-ssl false
RUN npm config set -g cache /var/cache 
RUN npm install -g @angular/cli
RUN npm install -g concurrently
RUN git config --global user.email "a@b.c"
RUN git config --global user.name "abc"
WORKDIR /src
