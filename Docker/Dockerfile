#spring-boot 使用embeded tomcat 
FROM java:8
VOLUME /tmp
ADD war/short-url.war short-url.war

RUN bash -c 'touch /short-url.war'

RUN echo '127.0.0.1 zoo1 zoo2 zoo3 0.0.0.0'>> /etc/hosts

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \

    && echo 'Asia/Shanghai' > /etc/timezone
    
EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/short-url.war"]