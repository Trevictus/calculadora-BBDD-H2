# Práctica: Calculadora Básica con Logs *(POO + SOLID)*

## [Enunciado de la práctica](EnunciadoPractica.md)

## Explicación detallada de la solución y diseño del programa

El programa está estructurado siguiendo **principios de la Programación Orientada a Objetos (POO)** y los **principios SOLID**, aplicando además una organización en paquetes clara para mejorar la escalabilidad, reutilización y mantenibilidad del código.

### 1. Arquitectura general

La aplicación sigue una arquitectura modular dividida en paquetes:

| Paquete       | Contenido principal                                                                 |
|---------------|--------------------------------------------------------------------------------------|
| `app`         | Lógica de control de flujo. Gestiona los argumentos y el ciclo principal del programa. |
| `model`       | Contiene las **entidades de dominio** como `Calculo` y el enum `Operador`.         |
| `service`     | Encapsula la **lógica de negocio** (operaciones aritméticas y gestión de logs).     |
| `data`        | Implementaciones de persistencia (en este caso, almacenamiento en ficheros).        |
| `utils`       | Utilidades genéricas relacionadas con acceso a archivos.                            |
| `ui`          | Entrada y salida a través de consola.                                               |
| *Fuera de paquetes* | Punto de entrada `main()`                                                      |

### 2. Aplicación de POO

Se utilizan principios clásicos de POO:
- **Encapsulamiento**: Cada clase gestiona su propia responsabilidad *(por ejemplo, `ServicioLog` gestiona los logs, `ServicioCalc` las operaciones)*.
- **Abstracción**: Uso de interfaces como `IEntradaSalida`, `IRepoLog`, `IUtilFiles` permite desacoplar la lógica de implementación.
- **Modularidad**: Cada componente se encuentra en su propio paquete y realiza una tarea específica.
- **Composición**: Las clases se relacionan entre sí por composición y no por herencia innecesaria.

### 3. Cumplimiento de SOLID

#### S - Principio de Responsabilidad Única (SRP)
Cada clase tiene una única responsabilidad:
- `Controlador`: coordina el flujo del programa.
- `ServicioCalc`: calcula operaciones.
- `ServicioLog`: gestiona el log (con ayuda del repositorio).
- `RepoLogTxt`: almacena datos en disco.
- `Consola`: gestiona la entrada/salida.
- `GestorFichText`: accede a archivos físicos.

#### O - Principio Abierto/Cerrado (OCP)
Puedes extender funcionalidades (por ejemplo, nuevos tipos de log) sin modificar las clases existentes gracias al uso de interfaces (`IRepoLog`, `IUtilFiles`, etc.).

#### L - Principio de Sustitución de Liskov (LSP)
Las implementaciones concretas (`RepoLogTxt`, `GestorFichText`, etc.) pueden ser sustituidas por otras que implementen la misma interfaz sin romper el sistema.

#### I - Principio de Segregación de Interfaces (ISP)
Cada interfaz define únicamente los métodos necesarios para su responsabilidad concreta (`IUtilFiles`, `IEntradaSalida`, etc.), evitando métodos innecesarios en clases que no los necesitan.

#### D - Principio de Inversión de Dependencias (DIP)
En general, **las clases dependen de abstracciones y no de implementaciones concretas**:

- `Controlador` depende de las interfaces `IEntradaSalida` y `IServicioLog`.
- `ServicioLog` depende de la interface `IRepoLog`.
- `RepoLogTxt` depende de la interface `IUtilFiles`.

> **Excepción de incluir IServicioCalc:**
> `ServicioCalc` **no depende de una interfaz** porque su comportamiento es simple y no se espera que tenga múltiples implementaciones. En este contexto, prescindir de una interfaz **reduce la complejidad** sin romper ningún principio, ya que no se viola la inversión de dependencias si no hay nada que abstraer.

### 4. Descripción de clases clave

#### `Controlador (app)`
- Coordina el flujo de la aplicación.
- Procesa argumentos, controla la ejecución del programa y gestiona los errores.
- Se comunica con los servicios de cálculo y de logs, y con la consola.

#### `Calculo (model)`
- Representa una operación matemática con dos números, un operador y su resultado.
- Sobrescribe `toString()` para generar fácilmente una línea de log o mensaje para mostrar.

#### `Operador (model)`
- Enumera los operadores disponibles y su representación simbólica.
- Proporciona un método para convertir un carácter a un operador válido.

#### `ServicioCalc (service)`
- Encapsula la **lógica de negocio** (realizar una operación entre dos números).
- No tiene interfaz porque **no se prevén múltiples implementaciones**, y eso **reduce complejidad innecesaria** *(solo realiza operaciones aritméticas básicas)*.
- Se puede extender fácilmente si en el futuro se necesitase una variante más compleja.

#### `IServicioLog / ServicioLog (service)`
- Proveen una abstracción sobre cómo gestionar y registrar logs.
- Permiten cambiar el backend de persistencia sin alterar el controlador o la lógica del cálculo.

#### `IRepoLog / RepoLogTxt (data)`
- Permiten gestionar el almacenamiento de logs.
- Su implementación actual usa ficheros físicos de texto, pero podrían migrarse fácilmente a una base de datos, sin modificar el resto de la aplicación.

#### `IUtilFiles / GestorFichText (utils)`
- Encapsulan operaciones de bajo nivel con archivos (crear carpetas, escribir líneas, leer ficheros...).

#### `IEntradaSalida / Consola (ui)`
- Aíslan la entrada/salida de la lógica del negocio.
- Permiten en el futuro reemplazar `Consola` por una interfaz gráfica o incluso por entrada/salida desde ficheros o red.

### 5. Organización modular por paquetes

Esto ayuda a cumplir el principio de separación de responsabilidades y a organizar el código de forma que sea fácilmente escalable:

```
📦 app
 ├── Controlador
 └── InfoCalcException
📦 data
 ├── IRepoLog
 └── RepoLogTxt
📦 model
 ├── Calculo
 └── Operador
📦 service
 ├── IServicioLog
 ├── ServicioLog
 └── ServicioCalc
📦 ui
 ├── IEntradaSalida
 └── Consola
📦 utils
 ├── IUtilFiles
 └── GestorFichText
```

### 6. Entrada del programa

```kotlin
fun main(args: Array<String>) {
    val repoLog = RepoLogTxt(GestorFichText())
    Controlador(Consola(), ServicioCalc(), ServicioLog(repoLog)).iniciar(args)
}
```
