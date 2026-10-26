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
- Implementar la gestión de usuarios y documentos.
- Incorporar almacenamiento local mediante Room.
- Aplicar validaciones, gestión de estado y retroalimentación visual.
- Crear una cuenta de administrador de prueba.
- Permitir al administrador consultar, editar y eliminar cuentas de estudiantes y profesores.
- Permitir la modificación del nombre, correo electrónico y rol de las cuentas gestionadas.
- Incorporar confirmación antes de eliminar una cuenta.
- Proteger la cuenta de administrador frente a las operaciones de edición y eliminación desde la pantalla de gestión.
- Permitir al administrador incorporar documentos PDF.
- Facilitar la selección y apertura de archivos PDF asociados a los documentos.
- Controlar la visibilidad de las opciones administrativas según el rol del usuario.
- Utilizar Git, GitHub y Trello para colaborar y organizar el desarrollo.

## 3. Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje de programación |
| Android Studio | Entorno de desarrollo |
| Jetpack Compose | Construcción de interfaces |
| Material Design 3 | Diseño visual de la aplicación |
| Navigation Compose | Navegación entre pantallas |
| Room | Persistencia local de datos |
| SQLite | Base de datos local utilizada por Room |
| KSP | Procesamiento de código para Room |
| Coroutines | Ejecución de operaciones asíncronas |
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
│           │       │   │   ├── GestionarUsuariosScreen.kt
│           │       │   │   ├── HomeScreen.kt
│           │       │   │   ├── LoginScreen.kt
│           │       │   │   ├── PerfilScreen.kt
│           │       │   │   └── RegistroScreen.kt
│           │       │   │
│           │       │   └── theme/
│           │       │       ├── Color.kt
│           │       │       ├── Theme.kt
│           │       │       └── Type.kt
│           │       │
│           │       └── viewmodel/
│           │           ├── DocumentosViewModel.kt
│           │           ├── LoginViewModel.kt
│           │           ├── PerfilViewModel.kt
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

**Nota:** el árbol representa la estructura identificada durante el desarrollo. Puede variar según los archivos existentes en el proyecto.

## 5. Funcionalidades implementadas

### 5.1. Pantalla de bienvenida

Presenta la aplicación y permite acceder a