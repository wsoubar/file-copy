export JAVA_BIN=./jre1.8.0_211/bin
nohup $JAVA_BIN/java -jar ssv-file-copy.jar -s -porta 17017 -caminho C:/dev/java/jar-copy/sharedlib/ >> ssv-file-copy.log 2>&1&
