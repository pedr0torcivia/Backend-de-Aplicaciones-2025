# Pre Enunciado

Se solicita desarrollar un programa de consola en Java con menú de opciones, ***ejecutable con Maven (mvn exec:java),*** que brinde soporte para la gestión de alquiler de películas y cumpla con las siguientes características:

## 1. Modelo de datos

La aplicación manejará las siguientes entidades:

```java
classDiagram
    class Pelicula {
        int id
        String titulo
        LocalDate fechaEstreno
        double precioBaseAlquiler
        Clasificacion clasificacion  %% <= Enum
        Genero genero
        Director director

        + calcularPrecioAlquiler() double
        %% Regla:
        %% - Reciente (<=365 días): +25%
        %% - Además, si clasificacion es ADULTOS_18 o ADOLESCENTES_13: +5% adicional
    }

    class Genero {
        int id
        String nombre
    }

    class Director {
        int id
        String nombre

    }

    class Clasificacion {
        ATP
        INFANTIL_7
        ADOLESCENTES_13
        ADOLESCENTES_16
        ADULTOS_18
    }

```

## 2. Entrada de datos

El programa debe leer un archivo CSV (el cual será provisto junto con el enunciado final), cuyo nombre puede pasarse como parámetro de entrada o estar definido por defecto.
El CSV contendrá información sobre productos digitales.

## 3. Base de datos

Se utilizará H2 en memoria como motor de base de datos. Se entregará un script SQL para crear las tablas necesarias junto con este enunciado. El programa deberá ejecutar este script al inicio para inicializar la base.

_Nota:_
Considere utilizar lo siguiente en la definición de la conexión a la DB, dentro del __persistence.xml__:
```
<property name="jakarta.persistence.schema-generation.database.action" value="create"/>
<property name="jakarta.persistence.schema-generation.create-source" value="script"/>
<property name="jakarta.persistence.schema-generation.create-script-source" value="META-INF/db-schema.sql"/>
```

## 4. Persistencia

- El programa debe leer el CSV y popular las tablas de la base de datos con sus registros.
- Manejar transacciones y validaciones básicas (ej.: precio > 0, fecha válida).

## 5. Consultas

El programa deberá obtener y mostrar en consola resúmenes de los datos cargados.
Las consignas específicas de consultas se entregarán junto con el enunciado final completo.

# Resultado esperado

Con la información provista, se espera que cada estudiante arme toda la estructura del proyecto, con las clases del modelo provistas utilizando JPA y permitiendo la conexión a la DB de la manera que crea más conveniente.

Se recomienda subir el proyecto inicial al GitLab propio para luego actualizar el repositorio con los cambios hechos durante el parcial.