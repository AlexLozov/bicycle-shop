FROM openjdk:21

WORKDIR /srv

COPY target/*.jar .
COPY docker/BicycleShop.sh .

RUN chmod +x BicycleShop.sh

EXPOSE 8080

ENTRYPOINT ["./BicycleShop.sh"]
