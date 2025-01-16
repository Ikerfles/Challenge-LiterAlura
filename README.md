# Bienvenido a LiterALura
LiterAlura es una aplicación de catálogo de libros que permite a los usuarios registrar libros en una base de datos y recuperar información sobre ellos.
La aplicación utiliza las tecnologías Java, Spring y PostgreSQL.

## Caracteristicas
1. Buscar libros por titulo
  - Los usuarios pueden buscar libros ingresando su título.
  - La aplicación obtiene datos de la API de Gutendex y registra el libro en la base de datos.
  - Muestra detalles del libro, incluyendo título, autor, idioma y número de descargas.
  - Previene entradas duplicadas en la base de datos.
2. Listar libros registrados
  - Muestra todos los libros almacenados en la base de datos
3. Listar autores registrados
Lista todos los autores con al menos un libro registrado.
4. Listar autores vivos por año
Filtra y lista autores que estaban vivos durante un año especificado.
5. Listar libros por idioma
Filtra libros por su idioma. Los idiomas soportados incluyen:
  - ES (Español)
  - EN (Inglés)
  - FR (Francés)
  - PT (Portugués)
6. Manejar entradas inválidas
  - Proporciona retroalimentación si un título de libro no existe en la API.
  - Garantiza la integridad de los datos rechazando registros duplicados de libros.

## Instrucciones de Uso

1. Iniciar la Aplicación
  - Compile y ejecute el proyecto desde su IDE o terminal.
  - Asegúrese de tener PostgreSQL configurado y en ejecución.
  - Si esta usando variables de entorno asegurese de que esten correctas
2. Interacción con la Consola
Al iniciar, se mostrarán opciones numeradas para seleccionar las funcionalidades:
  - Ingrese el número correspondiente a la funcionalidad que desea usar.
  - Siga las instrucciones proporcionadas por la aplicación para cada funcionalidad.
3. Buscar Libros
  - Seleccione la opción para buscar libros por título.
  - Ingrese el título del libro.
  - La aplicación buscará en la API de Gutendex y registrará el libro si no está duplicado.
4. Listar Datos
Use las opciones correspondientes para listar libros, autores, autores vivos en un año específico o libros por idioma.
5. Validar Entradas
La aplicación notificará si un libro no se encuentra en la API o si ya está registrado en la base de datos.

## Tecnologías Utilizadas

- Java: Lenguaje de programación principal para la aplicación.
- Spring Boot: Framework para desarrollo rápido de aplicaciones.
- PostgreSQL: Base de datos relacional para almacenar datos de libros y autores.
- API de Gutendex: Fuente de información de libros, basada en la biblioteca del Proyecto Gutenberg.

## ¡Gracias por utilizar LiterAlura! 
Este proyecto fue creado para como parte de un challenge donde se unen los conocimientos adquiridos en Java y Spring Framework
