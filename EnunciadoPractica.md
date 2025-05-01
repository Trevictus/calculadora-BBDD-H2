# Prueba usando CalcBasica

---

## Práctica Parte 1: Gestión de Cálculos con Logs y Argumentos

**CORRIGE** el programa de la carpeta `CalcBasicaConErrorACorregir` para que funcione correctamente.

1. Crea tu propio proyecto ***(Gradle)*** y copia el código de la carpeta anterior.
2. **Crea también un repositorio **PÚBLICO** y agrega el enlace a continuación:**
   * `EDITA ESTE FICHERO Y PEGA A CONTINUACIÓN EL ENLACE A TU REPO` -> 
4. **EJECUTA** el programa y observa el error que se produce **SI INTRODUCES UN NÚMERO NO VÁLIDO**.
5. **DEPURA** el programa para encontrar el problema.
6. Dale una solución para que ejecute de forma correcta y comprueba que funciona como se pedía originalmente la práctica `CalcBasica`.
7. Explícame, a continuación, que ocurría y cómo lo has solucionado:

---

## Práctica Parte 2: Gestión de Cálculos con Logs y Argumentos

Puedes utilizar tu propio código de `CalcBasica` o el anterior *(una vez corregido)* que os he pasado.

- **Si usas el código corregido que os he pasado en la parte 1, cread una nueva RAMA con el nombre `parte2`**

- **Si usas vuestro propio código de `CalcBasica`, agrega el enlace del repo PÚBLICO aquí** ->

### Título:
**Desarrollo de una calculadora básica con soporte de argumentos y sistema de registro (log)**

### Objetivo:
Crear una aplicación Kotlin basada en consola que implemente operaciones aritméticas básicas utilizando una arquitectura modular y principios de diseño orientados a objetos (POO) y principios SOLID. Además, gestionará argumentos por línea de comandos y mantendrá un registro de las operaciones realizadas en un sistema de logs.

### Requisitos funcionales:

1. **Comportamiento según los argumentos de entrada:**
   - **Sin argumentos:**  
     - Buscar la carpeta `./log`.  
     - Si no existe, crearla y mostrar: `"Ruta ./log creada"`  
     - Si existe y hay archivos, abrir el más reciente (`logYYYYMMDDHHMMSS.txt`) y mostrar su contenido línea por línea.  
     - Si no existen logs, mostrar: `"No existen ficheros de Log"`  

   - **Un argumento:**  
     - Se interpreta como la ruta de los logs.  
     - Mismo comportamiento que el caso anterior pero con la ruta proporcionada.  

   - **Cuatro argumentos:**  
     - El primero: ruta del log.  
     - Segundo y cuarto: números.  
     - Tercero: operador.  
     - Ejecuta el cálculo indicado, muestra el resultado o el error, y lo almacena en un nuevo fichero de log creado con formato `logYYYYMMDDHHMMSS.txt`.  

   - **Cualquier otro número de argumentos:**  
     - Mostrar error y no iniciar el programa.

2. **Cálculos en bucle:**
   - Después del manejo de argumentos, mostrar una pausa.
   - Limpiar pantalla.
   - Lanzar el programa de calculadora en bucle hasta que el usuario indique que no desea seguir.
   - Cada operación realizada se registra en el log actual, incluyendo fecha y hora (con segundos) y el resultado o error.
