# MeetHub :busts_in_silhouette:

## Gestor de Reuniones - Proyecto Fin de Grado Superior

![Status](https://img.shields.io/badge/status-active-success.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/rubendesrod/MeetHub)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

MeetHub es una aplicación diseñada para gestionar eficientemente las reuniones, permitiendo a los usuarios crear, modificar y visualizar próximas reuniones de manera sencilla y efectiva. Este proyecto ha sido desarrollado como parte del trabajo de fin de grado superior en informática.

### Contenidos
- [Instalación](#instalación)
- [Uso](#uso)
- [Contacto](#contacto)

### Instalación

```bash
git clone https://github.com/rubendesrod/MeetHub.git
cd MeetHub
```


### Uso

 * Primero
 Deberemos crear en nuestro entorno local unas variables de entorno
 las cuales conlleven el nombre de (`GOOGLE_CLIENT_ID`) y (`GOOGLE_CLIENT_SECRET`)
 Ambas dos tendrán que llevar las credenciales sacadas de google cloud para poder hacer uso del OAuth2.0
 las cuales deben de llevar permisos a los siguientes endPoints

* Segundo 
 corre un jar generado en la carpeta target
```bash
cd project
cd target
java -jar MeetHub-2.5.0.jar
```
localhost:/9000
 Ponemos esa ruta en el navegador y nos llevará a la página principal de la aplicación


### Contacto
https://www.linkedin.com/in/rubendescalzodr/