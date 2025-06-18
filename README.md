# SIGI Backend

Servidor del **Sistema Integral de Gestión de Inventarios (SIGI)**, desarrollado con Java y Spring Boot para exponer una API RESTful que gestiona stock, pedidos, rutas y usuarios.

**Software libre**

## Características principales

- Arquitectura basada en **Domain-Driven Design (DDD)**, con paquetes por dominio:  
  - `Branch`: gestión de sucursales (controladores, servicios, repositorios, DTOs).  
  - `Order`: lógica de pedidos, con entidades `Order` y `OrderProduct`, controladores de comandos y consultas.  
  - `Product`: gestión de productos y stock (`ProductInventory`).  
  - `Route`: cálculo y consulta de rutas, con DTOs para paradas y almacenes.  
  - `Truck`: seguimiento de camiones, comandos y consultas.  
  - `User`: autenticación (`AuthRequest`) y controladores de usuario.  
  - `Simulation`: endpoints y servicio asíncrono para simulación en tiempo real.  
- **CQRS** en los dominios de Branch, Order, Product, Route, Truck y User: separación de comandos (modificar estado) y queries (recuperar datos).  
- **Seguridad**: cifrado de contraseñas con `BCryptPasswordEncoder` y autenticación básica (sin registro de usuarios desde la UI).  
- **Configuración**: CORS, seguridad y web config en `config/`.  
- Construcción y gestión de dependencias con **Maven**.

## Requisitos

- Java JDK ≥ 17  
- Maven ≥ 3.6  
- IDE recomendado: IntelliJ IDEA

## Instalación

```
git clone https://github.com/ShadowTroll113/PackageTrackerBackend.git sigi-backend
cd sigi-backend
mvn install
Ejecución
Modo desarrollo
```

```
mvn spring-boot:run
```


Generar JAR y ejecutar
```
mvn package
java -jar target/PackageTracker-0.0.1-SNAPSHOT.jar
```
Estructura del proyecto
swift
.
├─ .gitattributes
├─ .gitignore
├─ HELP.md
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
├─ .idea/
├─ .mvn/
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  ├─ CreateUsers.java
│  │  │  └─ com/Project/PackageTracker/
│  │  │      ├─ PackageTrackerApplication.java
│  │  │      ├─ Branch/
│  │  │      ├─ config/
│  │  │      ├─ Order/
│  │  │      ├─ Product/
│  │  │      │  └─ Inventory/
│  │  │      ├─ Route/
│  │  │      ├─ Simulation/
│  │  │      ├─ Truck/
│  │  │      └─ User/
│  │  └─ resources/
│  │      ├─ application.properties
│  │      ├─ static/
│  │      └─ templates/
│  └─ test/
│     └─ java/com/Project/PackageTracker/
│         └─ OrderTrackerApplicationTests.java
└─ target/
   ├─ PackageTracker-0.0.1-SNAPSHOT.jar
   ├─ classes/
   └─ ...

Enlaces de interés
Repositorio Backend: https://github.com/ShadowTroll113/PackageTrackerBackend
Repositorio Frontend: https://github.com/ShadowTroll113/Package-Tracer-Frontend

Demo en vídeo: https://drive.google.com/file/d/1p2tnTumy5I_rhqN_maW_lE1ZPPXW6_m_/view?usp=sharing



