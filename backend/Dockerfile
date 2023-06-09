FROM openjdk:17-alpine
RUN apk update \
  && apk upgrade \
  && apk add ca-certificates \
  && update-ca-certificates \
  && apk add --update inotify-tools && rm -rf /var/cache/apk/*
COPY watcher.sh /watcher.sh
RUN chmod +x /watcher.sh
CMD ["/watcher.sh"]