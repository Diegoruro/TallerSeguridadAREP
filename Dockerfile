FROM openjdk:8

WORKDIR /usrapp/bin

ENV PORT 5000
ENV KEYSTORE /usrapp/bin/ecikeystore.p12
ENV KEYTRUST /usrapp/bin/ecikeypair.p12
ENV MESSAGE "hola desde server 1"
ENV PASSWORD Abc123!
ENV PORTSERVICE 5001

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

COPY /llaves .
COPY /llavesService .

CMD ["java","-cp","./classes:./dependency/*","edu.eci.arep.SparkWeb"]