# ESFOCAR APP

## 1. Descripción del proyecto

ESFOCAR APP es una aplicación móvil desarrollada en Kotlin mediante Android Studio, como parte de un proyecto académico.

El objetivo es organizar y facilitar el acceso a documentos y recursos de apoyo formativo de ESFOCAR.

Actualmente, el proyecto se encuentra en la etapa de configuración inicial y organización de su estructura.

## 2. Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3
- Room
- KSP
- Git y GitHub

## 3. Estructura del proyecto

```text
com.example.proyecto_esfocar/
|
|-- data/
|   |-- document/
|   |-- local/
|   |   |-- EsfocarDatabase.kt
|   |-- repository/
|
|-- model/
|   |-- Documento.kt
|   |-- Usuario.kt
|
|-- ui/
|   |-- components/
|   |   |-- EsfocarComponents.kt
|   |-- navigation/
|   |   |-- AppNavigation.kt
|   |-- screens/
|   |   |-- DocumentosScreen.kt
|   |   |-- HomeScreen.kt
|   |   |-- LoginScreen.kt
|   |   |-- RegistroScreen.kt
|   |-- theme/
|
|-- util/
|
|-- viewmodel/
|   |-- DocumentosViewModel.kt
|   |-- LoginViewModel.kt
|   |-- RegistroViewModel.kt
|
|-- MainActivity.kt
```

## 4. Organización de paquetes

- `data`: organización y acceso a los datos.
- `data/document`: organización de los recursos relacionados con documentos.
- `data/local`: configuración prevista para el almacenamiento local.
- `data/repository`: gestión y acceso a los datos.
- `model`: modelos de datos de usuarios y documentos.
- `ui/screens`: pantallas de la aplicación.
- `ui/components`: componentes visuales reutilizables.
- `ui/navigation`: navegación entre pantallas.
- `ui/theme`: configuración del tema visual.
- `viewmodel`: gestión del estado y la lógica de presentación.
- `util`: funciones auxiliares.
- `MainActivity.kt`: actividad principal de la aplicación.

## 5. Configuración realizada

// Se creó la estructura inicial de paquetes y archivos Kotlin.
// Se organizaron las pantallas, los modelos y los ViewModels.
// Se conservaron los archivos de tema generados por Android Studio.
// Se añadieron las dependencias de Room.
// Se configuró el complemento KSP.
// Se sincronizó Gradle correctamente.

## 6. Estado actual del proyecto

// Estructura de paquetes y archivos: creada.
// Dependencias de Room y KSP: configuradas.
// Modelos de usuario y documento: archivos creados, pendientes de implementación.
// Pantallas: archivos creados, pendientes de implementación.
// Navegación: pendiente de implementación.
// Registro e inicio de sesión: pendientes de implementación.
// Base de datos local: pendiente de implementación.
// Gestión de documentos: pendiente de implementación.
// Validaciones, animaciones y pruebas: pendientes de implementación.

## 7. Próximas tareas

1. Implementar los modelos de datos.
2. Configurar las entidades, los DAO y la base de datos con Room.
3. Implementar el registro de usuarios y el inicio de sesión.
4. Desarrollar la navegación entre pantallas.
5. Implementar la pantalla principal.
6. Desarrollar la gestión de documentos.
7. Incorporar las validaciones, las animaciones y los recursos requeridos.
8. Realizar pruebas para verificar el funcionamiento de la aplicación.

## 8. Control de versiones y trabajo colaborativo

// El proyecto utiliza Git y GitHub para gestionar los cambios.
// Se trabajará mediante ramas para organizar el desarrollo.
// Los cambios se documentarán mediante mensajes de commit descriptivos.
// La integración de los cambios se realizará de forma controlada.

## 9. Ejecución del proyecto

1. Clonar el repositorio de GitHub.
2. Abrir el proyecto en Android Studio.
3. Esperar a que Gradle sincronice las dependencias.
4. Ejecutar la aplicación en un emulador o dispositivo Android compatible.

## 10. Observaciones

// Este README describe la estructura y la configuración inicial del proyecto.
// La creación de archivos no implica que sus funcionalidades estén implementadas.
// La documentación se actualizará a medida que avance el desarrollo de ESFOCAR APP.
