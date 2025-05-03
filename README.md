# Aplicación Android Vinilos

Esta es una aplicación Android desarrollada para gestionar una colección de vinilos.

## Requisitos Previos
-   Android Studio actualizado
-   Seleccionar Gradle JDK 17 (Oracle OpenJDK 17.0.12)
-   Versión de Android : SDK API 21 (Lollipop Android 5.0)
-   API: https://backvynils-q6yc.onrender.com 

## Configuración del Entorno de Desarrollo

1. Clona este repositorio:
```bash
git clone https://github.com/gorozcoua/team18.git
```

2. Abre el proyecto en Android Studio:
   - Abre Android Studio
   - Selecciona "Open an existing Android Studio project"
   - Navega hasta la carpeta del proyecto y selecciónala

3. Sincroniza el proyecto con Gradle:
   - Espera a que Android Studio sincronice el proyecto automáticamente
   - Si la sincronización automática falla, haz clic en "Sync Project with Gradle Files"

## Construcción y Ejecución

1. Asegúrate de tener un dispositivo Android conectado o un emulador configurado

2. Para ejecutar la aplicación:
   - Selecciona tu dispositivo/emulador en el menú desplegable de dispositivos
   - Haz clic en el botón "Run" (ícono de play verde) o presiona Shift+F10

## Dependencias Principales

La aplicación utiliza las siguientes dependencias principales:
- AndroidX Core KTX
- Material Design Components
- Retrofit2 para llamadas de red
- Gson para parsing JSON
- Glide para carga de imágenes
- Volley para operaciones de red

## Estructura del Proyecto

```
app/
├── src/
│   ├── main/
│   │   ├── java/        # Código fuente Kotlin/Java
│   │   ├── res/         # Recursos (layouts, strings, etc.)
│   │   └── AndroidManifest.xml
│   └── test/            # Tests unitarios
└── build.gradle         # Configuración de build
```

## Solución de Problemas Comunes

1. Error de Gradle Sync:
   - Verifica que tienes la versión correcta de JDK instalada
   - Asegúrate de que las variables de entorno JAVA_HOME y ANDROID_HOME están configuradas correctamente

2. Error de compilación:
   - Limpia el proyecto (Build > Clean Project)
   - Invalida los caches (File > Invalidate Caches / Restart)


