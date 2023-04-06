# ButterMove Desafio

Microservicio que regresa la estimación del costo total de un viaje

## Prerequisitos

* Java11

## Arquitectura
Service -> MongoDB

## Stack tecnologico
* Spring boot
* Mongo Reactive
* WebFlux
* Lombok
* Flapdoodle-Mongo
* ProjectReactor-Test
* Joda-Time
* Maven

## Patrones de diseño
* Mediator / Controller
* Strategy
* Data Transfer Object
* Layers (Business)
* Response Entity

## Para iniciar
Existe el archivo application.yml multiples perfiles tales como:
* test
* dev
* qa
* prod

> Si desea probar el proyecto sin ejecutar la aplicación, use el perfil de **test** que incluye mongo.
> Si desea ejecutar la aplicación y probar con la inicialización de datos (los mismos datos de la prueba del controlador de estimación), puede usar **perfil de dev** para incluir incrustar mongo.
>
> Si desea ejecutar la aplicación sin inicializar datos, puede usar **prod profile** para incluir mongo.
>
> Si desea ejecutar la aplicación con su propio mongo, puede usar **qa, prod** pero necesita cambiar la ip en application.yml
>
> Para cambiar el perfil vas a la aplicación.yml y el primero es perfiles:activo: actualizar con **test**
>
> Otra forma de cambiar el perfil después de compilar el jar: ``java -jar -Dspring.profiles.active=test estimate-0.0.1-SNAPSHOT.jar``
>
> Para cambiar la IP de mongo, vaya a application.yml, busque el perfil correcto para cambiar y existen tres atributos para cambiar: host, puerto y base de datos.
>
> Si desea utilizar el docker de Mongo, puede utilizar el siguiente:
> ``docker pull mongo:latest``
>
> ``docker run --name estimate-mongo -p 27017:27017 -d mongo:latest``
>
> si la app está corriendo, y desea ejecutar un request use:
>
>  ``curl --location 'http://localhost:8085/Estimate/v1?state=oh&type=premium&kilometers=10&amount=100.0' \
--header 'ip-client: 172.8.9.28' ``