# Práctica A+B - Conexión BD + ORM (MOR)

Proyecto preparado para entregar y ejecutar sin fallos en un entorno Java/Maven.

## Requisitos

- Java 21
- Maven 3.9+
- MongoDB en `localhost:27017` con la BD `bibliotecaDB` y colección `biblioteca`

> BaseX no requiere servidor levantado para esta práctica, se usa la API Java embebida.

## Estructura

- `org.roque.ConexionBaseX` → Programa A.1 (BaseX, XPath/XQuery).
- `org.roque.ConexionMongo` → Programa A.2 (MongoDB, `find`).
- `org.roque.orm.OrmPracticaApp` → Parte B (herramienta ORM, HQL, caso de uso).
- `src/main/resources/biblioteca.xml` → XML base.

## A) Conexión a bases de datos

### A.1 BaseX

Ejecuta una consulta XQuery sobre `biblioteca.xml`.

```bash
mvn -q -DskipTests exec:java -Dexec.mainClass=org.roque.ConexionBaseX
```

También puedes pasar una consulta personalizada:

```bash
mvn -q -DskipTests exec:java \
  -Dexec.mainClass=org.roque.ConexionBaseX \
  -Dexec.args="for $l in //libro return $l/autor/text()"
```

### A.2 MongoDB (Find)

```bash
mvn -q -DskipTests exec:java -Dexec.mainClass=org.roque.ConexionMongo
```

Find con filtro por título:

```bash
mvn -q -DskipTests exec:java \
  -Dexec.mainClass=org.roque.ConexionMongo \
  -Dexec.args="Clean Code"
```

## B) Herramientas MOR

### 1) Introducción y herramienta elegida

Se usa **Hibernate ORM (JPA)** porque:

- Es la herramienta ORM más extendida en Java.
- Permite mapear entidades POJO a tablas sin SQL repetitivo.
- Soporta HQL, DML, transacciones y consultas nativas (procedimientos/funciones).
- Facilita escalabilidad y mantenimiento para proyectos académicos y reales.

Herramientas de mapeo conocidas: Hibernate, EclipseLink, MyBatis, jOOQ.

### 2) Lenguaje HQL - 3 ejemplos

Incluidos en `OrmPracticaApp`:

1. Join simple: `Libro` + `Autor`.
2. Join de 2+ tablas: `Prestamo` + `Libro` + `Autor`.
3. Subconsulta: libros por encima del precio medio.

### 3) Caso de uso

Incluido en `OrmPracticaApp`:

- Selección simple sobre `Libro` y joins con `Autor`.
- DML sobre una o varias tablas:
    - `INSERT` (nuevo autor y libro)
    - `UPDATE` (precio libro)
    - `DELETE` (préstamos por socio)
- Procedimiento y función con H2:
    - Función `CALCULAR_IVA`
    - Procedimiento `SUBIR_PRECIO`


## Componente de conexión usando Bean (reutilizable)

Se agregó `org.roque.orm.bean.DbConnectionBean` como componente JavaBean para centralizar la conexión a la BD ORM y reutilizarla desde cualquier programa.

- Bean: `src/main/java/org/roque/orm/bean/DbConnectionBean.java`
- Uso central: `HibernateUtil.getConnectionBean()`

Ejemplo de uso desde otra clase:

```java
DbConnectionBean bean = HibernateUtil.getConnectionBean();
System.out.println(bean.getJdbcUrl());
```

Demo ejecutable del bean:

```bash
mvn -q -DskipTests exec:java -Dexec.mainClass=org.roque.orm.bean.BeanConnectionDemo
```

## Ejecución de toda la práctica

```bash
mvn -q -DskipTests exec:java -Dexec.mainClass=org.roque.Main
```

## Carga inicial Mongo (si hace falta)

Si quieres recrear la colección:

```javascript
use bibliotecaDB

db.biblioteca.deleteMany({})
db.biblioteca.insertMany([
  { titulo: "Clean Code", autor: "Robert C. Martin", precio: 35 },
  { titulo: "Effective Java", autor: "Joshua Bloch", precio: 40 }
])
```

---

Con esto tu profesor puede clonar/abrir el proyecto, ejecutar Maven y revisar cada apartado A y B con evidencias en consola.
