#export PATH=$PATH:./jre1.8.0_211/bin
export JAVA_BIN=./jre1.8.0_211/bin
nohup $JAVA_BIN/java -jar jar-copy-1.0.jar server >> file-copy.log 2>&1&
