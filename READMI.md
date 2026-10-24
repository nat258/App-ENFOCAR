# 🇨🇱 ESFOCAR APP

## 1. Descripción del proyecto

**ESFOCAR APP** es una aplicación móvil académica desarrollada en Android Studio utilizando Kotlin y Jetpack Compose. Su propósito es proporcionar un espacio formativo para acceder a documentos y recursos relacionados con la Escuela de Formación de Carabineros de Chile.

El proyecto busca facilitar el acceso a materiales de estudio mediante una interfaz organizada, navegación entre pantallas y funcionalidades orientadas a la gestión de usuarios y documentos.

La aplicación corresponde a un MVP académico y no está conectada a los sistemas institucionales reales.

## 2. Objetivos del proyecto

- Diseñar una interfaz móvil clara, intuitiva y organizada.
- Incorporar pantallas de bienvenida, registro e inicio de sesión.
- Implementar navegación entre las distintas secciones de la aplicación.
- Organizar el código mediante paquetes y componentes reutilizables.
- Preparar una estructura para la gestión de usuarios y documentos.
- Incorporar almacenamiento local mediante Room.
- Aplicar validaciones, gestión de estado y retroalimentación visual.
- Integrar recursos nativos del dispositivo.
- Utilizar Git, GitHub y Trello para colaborar y organizar el desarrollo.

## 3. Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje de programación |
| Android Studio | Entorno de desarrollo |
| Jetpack Compose | Construcción de interfaces |
| Material 3 | Diseño visual de la aplicación |
| Navigation Compose | Navegación entre pantallas |
| Room | Persistencia de datos local prevista |
| KSP | Procesamiento de código para Room |
| Git | Control de versiones |
| GitHub | Repositorio del proyecto |
| Trello | Organización y seguimiento de tareas |

## 4. Estructura del proyecto

El proyecto se organiza en paquetes para separar la interfaz de usuario, los modelos, el acceso a datos y la lógica de presentación.

```text
Proyecto_ESFOCAR/
│
├── app/
│   ├── build.gradle.kts
│   │
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           │
│           ├── java/
│           │   └── com/example/proyecto_esfocar/
│           │       │
│           │       ├── MainActivity.kt
│           │       │
│           │       ├── data/
│           │       │   ├── document/
│           │       │   │
│           │       │   ├── local/
│           │       │   │   ├── DocumentoDao.kt
│           │       │   │   ├── EsfocarDatabase.kt
│           │       │   │   └── UsuarioDao.kt
│           │       │   │
│           │       │   └── repository/
│           │       │       ├── DocumentoRepository.kt
│           │       │       └── UsuarioRepository.kt
│           │       │
│           │       ├── model/
│           │       │   ├── Documento.kt
│           │       │   └── Usuario.kt
│           │       │
│           │       ├── ui/
│           │       │   ├── components/
│           │       │   │   └── EsfocarComponents.kt
│           │       │   │
│           │       │   ├── navigation/
│           │       │   │   └── AppNavigation.kt
│           │       │   │
│           │       │   ├── screens/
│           │       │   │   ├── BienvenidaScreen.kt
│           │       │   │   ├── DocumentosScreen.kt
│           │       │   │   ├── HomeScreen.kt
│           │       │   │   ├── LoginScreen.kt
│           │       │   │   └── RegistroScreen.kt
│           │       │   │
│           │       │   └── theme/
│           │       │       ├── Color.kt
│           │       │       ├── Theme.kt
│           │       │       └── Type.kt
│           │       │
│           │       ├── util/
│           │       │
│           │       └── viewmodel/
│           │           ├── DocumentosViewModel.kt
│           │           ├── LoginViewModel.kt
│           │           └── RegistroViewModel.kt
│           │
│           └── res/
│               └── drawable/
│                   └── escudo_esfocar_app.png
│
├── gradle/
│   └── libs.versions.toml
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
└── README.md
```

**Nota:** esta estructura representa los archivos identificados hasta el momento. Las carpetas `document` y `util` aparecen sin archivos en la captura revisada. La ubicación de la imagen del escudo debe comprobarse en `res/drawable`.

## 5. Avances realizados

### 5.1. Organización del código

Se ha organizado el proyecto mediante paquetes con responsabilidades diferenciadas:

- `data`: destinado a la gestión y acceso a los datos.
- `model`: contiene los modelos `Usuario` y `Documento`.
- `ui/components`: destinado a componentes visuales reutilizables.
- `ui/navigation`: contiene la configuración de navegación.
- `ui/screens`: reúne las pantallas de la aplicación.
- `ui/theme`: contiene los archivos del tema y los colores.
- `viewmodel`: destinado a la lógica de presentación y gestión del estado.
- `util`: reservado para funciones auxiliares.

### 5.2. Configuración del proyecto

Se han incorporado dependencias para trabajar con Room y KSP en el archivo `app/build.gradle.kts`.

La sincronización de Gradle se completó correctamente.

### 5.3. Diseño visual

Se definió una paleta de colores inspirada en los tonos verde, amarillo y blanco.

Los archivos `Color.kt` y `Theme.kt` se configuraron para aplicar estos colores a la interfaz mediante Material 3.

### 5.4. Pantalla de bienvenida

Se creó la pantalla `BienvenidaScreen.kt`, que incluye:

- Nombre de la Escuela de Formación de Carabineros de Chile.
- Imagen de identificación visual de ESFOCAR APP.
- Mensaje de bienvenida.
- Descripción de la aplicación.
- Botón «Iniciar sesión».
- Botón «Registrarse».

La pantalla se ejecutó correctamente en el emulador de Android Studio.

### 5.5. Navegación

En `AppNavigation.kt` se definieron rutas para las siguientes pantallas:

- Bienvenida.
- Inicio de sesión.
- Registro.
- Inicio.
- Documentos.

Las conexiones entre las pantallas deben probarse para comprobar el funcionamiento de cada ruta y sus acciones.

### 5.6. Organización de datos

Se cuenta con archivos destinados a organizar la persistencia y el acceso a los datos:

- `EsfocarDatabase.kt`
- `DocumentoDao.kt`
- `UsuarioDao.kt`
- `DocumentoRepository.kt`
- `UsuarioRepository.kt`

La implementación de estos componentes debe verificarse para determinar qué operaciones están terminadas y cuáles faltan.

## 6. Estado actual del proyecto

| Componente | Estado |
|---|---|
| Estructura de paquetes | Creada |
| Dependencias de Room y KSP | Configuradas |
| Paleta de colores | Definida |
| Tema visual | Configurado |
| Pantalla de bienvenida | Creada y ejecutada |
| Imagen de bienvenida | Incorporada al diseño |
| Rutas de navegación | Definidas inicialmente |
| Pantallas de registro e inicio de sesión | Archivos creados; funcionamiento pendiente de verificar |
| Pantalla de inicio | Archivo creado; funcionamiento pendiente de verificar |
| Pantalla de documentos | Archivo creado; funcionamiento pendiente de verificar |
| Modelos de usuario y documento | Archivos creados; implementación pendiente de verificar |
| Base de datos Room | Archivos creados; implementación pendiente de verificar |
| DAO y repositorios | Archivos creados; implementación pendiente de verificar |
| Validaciones de formularios | Pendientes de verificar |
| Persistencia local funcional | Pendiente de verificar |
| Gestión de documentos | Pendiente de verificar |
| Animaciones | Pendientes de implementar y probar |
| Recursos nativos | Pendientes de integrar y probar |
| Pruebas funcionales | Pendientes de realizar |

## 7. Próximas etapas de desarrollo

1. Revisar los modelos `Usuario.kt` y `Documento.kt`.
2. Verificar la configuración de `EsfocarDatabase.kt`.
3. Revisar las operaciones definidas en los DAO.
4. Conectar los repositorios con los ViewModels.
5. Completar y validar el registro de usuarios.
6. Implementar y verificar el inicio de sesión.
7. Comprobar la navegación entre todas las pantallas.
8. Implementar la gestión y visualización de documentos.
9. Incorporar almacenamiento local funcional.
10. Agregar validaciones y mensajes de retroalimentación.
11. Integrar recursos nativos y animaciones.
12. Ejecutar pruebas y corregir errores.
13. Actualizar la documentación conforme se completen las funcionalidades.

## 8. Control de versiones y trabajo colaborativo

El proyecto utiliza Git y GitHub para gestionar los cambios y mantener un historial del desarrollo.

Se recomienda registrar cada avance mediante commits con mensajes descriptivos y mantener las tareas organizadas en Trello.

El repositorio deberá permitir el acceso al docente y a las integrantes del equipo, según las instrucciones de la evaluación.

## 9. Instrucciones de ejecución

1. Abrir Android Studio.
2. Abrir la carpeta del proyecto.
3. Esperar a que finalice la sincronización de Gradle.
4. Iniciar un emulador compatible o conectar un dispositivo Android.
5. Ejecutar la aplicación desde Android Studio.

## 10. Observaciones

Este documento refleja los archivos y avances identificados hasta el momento.

