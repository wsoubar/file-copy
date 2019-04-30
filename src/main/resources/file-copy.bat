rem SET JAVA_BIN=.\jre\bin
rem %JAVA_BIN%\java -jar ssv-file-copy-1.0.jar -c -porta 17017 -servidores ./servidores.txt > ssv-file-copy-client.log
java -jar ssv-file-copy-1.0.jar -c -porta 17017 -servidores ./servidores.txt > ssv-file-copy-client.log