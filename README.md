# API Restaurant Sales

![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)

Este documento describe los siguientes temas concernientes a la ejecución y modificación del proyecto "Restaurant Sales".

  - Prerrequisitos
  - Ejecución sin compilación local
  - Ejecución con compilación local
  - Ejecución de pruebas unitarias
  - Comportamiento general del flujo

## Prerrequisitos

  - JDK 1.8
  - Docker
  - Maven
  - [Lombok](https://projectlombok.org/)
  
Se debe considerar que el único elemento necesario para ejecutarlo sin compilación local es Docker. Con respecto a los demás elementos, estos son necesarios en la ejecución del proyecto con compilación local.

## Ejecución sin compilación local

Con esta modalidad, no es necesario contar con el código fuente de la aplicación. Solo basta con tener el archivo [docker-compose.yml](https://github.com/luisorellana777/spring_boot_restaurant-sales-v2/blob/master/docker-compose.yml) y ejecutarlo en la misma ruta donde se encuentre:

```sh
$ docker-compose up
```

Esto dará paso a un pull y run de tres imágenes:
  - [luisorellanaa/restaurant-sales:1.0.0-SNAPSHOT](https://hub.docker.com/repository/docker/luisorellanaa/restaurant-sales)
  - rabbitmq:management
  - mysql:5.7

## Ejecución con compilación local

Para este paso, es necesario contar con el código fuente del servicio.
Como primer paso, se debe ejecutar el siguiente comando en la ruta donde se encuentre el archivo [docker-compose.yml](https://github.com/luisorellana777/spring_boot_restaurant-sales-v2/blob/master/docker-compose.yml):

```sh
$ docker-compose up mysql rabbit
```
Esto dejara en ejecución un contenedor Mysql.
Luego, en la ruta donde se encuentre el archivo [pom.xml](https://github.com/luisorellana777/spring_boot_restaurant-sales-v2/blob/master/pom.xml), ejecutar:
```sh
$ ./mvnw spring-boot:run
```
De esta manera, se podrá modificar sin problemas el código base del servicio.

## Ejecución de pruebas unitarias
Por otro lado, si se desea ejecutar las pruebas unitarias, es necesario ejecutar el siguiente comando en la ruta donde se encuentre el archivo [docker-compose.yml](https://github.com/luisorellana777/spring_boot_restaurant-sales-v2/blob/master/docker-compose.yml):

```sh
$ docker-compose up rabbit
```
Y luego ejecutar el siguiente comando donde se encuentre el archivo [pom.xml](https://github.com/luisorellana777/spring_boot_restaurant-sales-v2/blob/master/pom.xml):
```sh
$ mvn clean package
```
Esto realizara dos pasos:
  - Ejecutara los test unitarios, y luego la creación del jar.
  - Creara una imagen Docker local, utilizando el archivo [Dockerfile](https://github.com/luisorellana777/spring_boot_restaurant-sales-v2/blob/master/Dockerfile). Esto se realiza por medio de un plugin inserto en el archivo [pom.xml](https://github.com/luisorellana777/spring_boot_restaurant-sales/blob/master/restaurant-sales/pom.xml). Este plugin es [dockerfile-maven-plugin](https://mvnrepository.com/artifact/com.spotify/dockerfile-maven-plugin).

Para realizar las pruebas unitarias, es necesario contar con RabbitMQ, ya que en este paso se crea una cola de mensajería especial para dicho test. Luego de que finalizan estas pruebas, la cola de test es eliminada de manera automática. Por otra parte, no es necesario utilizar la imagen Docker MySQL, ya que en la fase de pruebas, se crea una base de datos en memoria. Esta base de datos es [H2](https://www.h2database.com/html/main.html).

## Comportamiento general del flujo
### Endpoints

Los endpoints disponibles son:
  - GET: http://localhost:8091/actuator
  - GET: http://localhost:8091/swagger-ui.html
  - PUT: http://localhost:8091/login
  
  - GET: http://localhost:8091/sales
  - POST: http://localhost:8091/sales
  - PUT: http://localhost:8091/sales
  - GET: http://localhost:8091/sales/{idSale}
  - DELETE: http://localhost:8091/sales/{idSale}
  - GET: http://localhost:8091/sales/today
  - GET: http://localhost:8091/dishes
  - GET: http://localhost:8091/tables
  - GET: http://localhost:8091/waiters
  

Todos los endpoint se encuentran bloqueados por medio de autenticación, por medio de [JWT](https://jwt.io/), con excepción de http://localhost:8091/actuator, http://localhost:8091/swagger-ui.html y http://localhost:8091/login.

Con el objetivo de simplificar la utilización del servicio, se detallarán las consultas de los endpoints por medio de [Swagger](http://localhost:8091/swagger-ui.html):
Como primer paso, se debe consultar el endpoint http://localhost:8091/login.
Las credenciales son:

```diff
email:    arroba@punto.ceele
password: restorrtt
```

En el cuerpo del mensaje se debe incluir:

```diff
{
  "email": "arroba@punto.ceele",
  "password": "restorrtt"
}
```

Con esto se obtendrá un mensaje, el cual contendrá un token como:

```diff
{"message":"Credencial Correcto.",
"token":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJrZXlJZEpXVCIsInN1YiI6ImFycm9iYUBwdW50by5jZWVsZSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2MDE5MTk4MDMsImV4cCI6MTYwMTkyMDQwM30.-aioy6JbQzWdA9AGNRcxjIDNkNJDs-_HzlEBaI8sejbIzgk6ecvMZzyr7mLWL-0bGEk0qIoP6caVUv7TO7P8Xg"}
```
Se debe ingresar el token en conjunto con la palabra "Bearer" en el pop up desplegado al presionar el botón "Autorize", en el costado superior derecho de [Swagger](http://localhost:8091/swagger-ui.html).

Luego de esto, es posible consultar todos los endpoints anteriormente descritos.
El token tiene una duración de 10 minutos, luego de esto es necesario renovarlo realizando un cierre de sesión, consultando el endpoint [/login]( http://localhost:8091/login), y luego ingresándolo nuevamente como autorización en [Swagger]( http://localhost:8091/swagger-ui.html).

#### Descripción de Endpoints

Los siguientes endpoints realizan CRUD de la entidad "Sale":
  - GET: http://localhost:8091/sales
  - POST: http://localhost:8091/sales
  - PUT: http://localhost:8091/sales
  - GET: http://localhost:8091/sales/{idSale}
  - DELETE: http://localhost:8091/sales/{idSale}
  - GET: http://localhost:8091/sales/today

Por otra parte, los siguientes métodos describen las entidades relacionadas con la venta. Estas son:
  - GET: http://localhost:8091/dishes
  - GET: http://localhost:8091/tables
  - GET: http://localhost:8091/waiters

La estructura json de la entidad "Sale" que se debe enviar como POST es:

```
{
  "dishes": [
    {
      "id": 1000,
      "quantity": 1
    },
    {
      "id": 1001,
      "quantity": 2
    }
  ],
  "table": {
    "id": 1000
  },
  "waiter": {
    "id": 1000
  },
  "tip": 500
}
```

Las entidades agregadas a "Sale" solo deben contener sus identificadores, con excepcion a los platos, los cuales deben incluir cantidad de unidades del plato descritas.

Se deben considerar las siguientes validaciones:
  - dishes[...]: Debe tener al menos un elemento.
  - dishes[...].quantity: Cantidad minima de platos de este tipo es 1.
  - waiter: Se debe ingresar un mesero.
  - table: Se debe ingresar una mesa.
  - neto: Valor neto es auto calculado.
  - tax: Valor iva es auto calculado.
  - total: Valor total es auto calculado.
  - tip: El valor de la propina debe ser valido.

Para el caso de modificar la entidad "Sale" por medio de PUT, se debe ingresar el identificador de la venta, quedando de esta manera:

```
{
  "id": 1,
  "dishes": [
    {
      "id": 1002,
      "quantity": 3
    }
  ],
  "table": {
    "id": 1001
  },
  "waiter": {
    "id": 1001
  },
  "tip": 2500
}
```

En el caso de utilizar el metodo DELETE, la entidad "Sale" no es eliminada de la base de dato, sino que su estado es modificado por medio de [hibernate soft delete](https://thorben-janssen.com/implement-soft-delete-hibernate/).

### Proceso Asíncrono

Se utiliza un proceso en paralelo para depositar elementos en una cola RabbitMQ. La venta generada por medio de un Mock es de manera aleatorio, generando valores al azar y cantidad de ventas entre 1 y 10, una vez por minuto. Luego un escuchador recoge dicho elemento (Sale) para luego depositarlo en la base de datos. De esta manera se simula el flujo constante de ventas a la base de datos.
