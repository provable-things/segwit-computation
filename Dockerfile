FROM openjdk:alpine
MAINTAINER Oraclize "info@oraclize.it"

COPY ./* /tmp/
CMD cd /tmp && javac -cp ./bitcoinj.jar ./SegwitStats.java && java -cp .:bitcoinj.jar:slf4j-nop-1.6.4.jar SegwitStats $ARG0
