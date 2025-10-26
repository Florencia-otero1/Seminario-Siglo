# 🐾 Sistema de Gestión y Difusión de Mascotas Perdidas  

## 📘 Contexto del Proyecto  
Este proyecto forma parte del **Trabajo Práctico Integrador** de la materia **Seminario de Práctica de Informática**, correspondiente a la **Licenciatura en Informática** (Universidad Siglo 21).

El sistema permite **registrar casos de mascotas perdidas, encontradas o animales callejeros**, **difundirlos públicamente**, **gestionar reportes** (rol administrador) y **registrar donaciones**.  
Su propósito es **centralizar la información** y facilitar la **colaboración comunitaria** para ayudar en la recuperación y bienestar de los animales.

El desarrollo siguió el **Proceso Unificado de Desarrollo (PUD)**, aplicando análisis, diseño y construcción incremental.

---

## ⚙️ Tecnologías Utilizadas  

| Tecnología | Rol en el Sistema |
|---|---|
| **Java (POO)** | Lógica principal y estructura del sistema |
| **JavaFX + FXML** | Desarrollo de la interfaz gráfica (Vistas) |
| **MySQL** | Base de datos relacional para persistencia |
| **JDBC** | Conexión y consultas a la base de datos |
| **MVC + DAO** | Arquitectura del software |
| **Git y GitHub** | Control y gestión de versiones |

---

## 🏛️ Arquitectura del Sistema  

La aplicación se desarrolló bajo arquitectura **MVC + DAO**, asegurando separación clara de responsabilidades.

### **Modelo (Model)**  
Clases que representan entidades reales:
- `Usuario`
- `Administrador` (hereda de `Usuario`)
- `Reporte`
- `Donacion`

### **Vista (View)**  
Pantallas desarrolladas con JavaFX (`.fxml`):
- `admin-gestion-reportes.fxml`
- `difusion-view.fxml`
- `donacion-view.fxml`
- `login-view.fxml`
- `menu-principal.fxml`
- `registro-view.fxml`
- `reporte-view.fxml`

### **Controladores (Controller)**  
Manejan acciones del usuario, validaciones y comunicación entre vista y modelo:
- `LoginController`
- `MenuPrincipalController`
- `ReporteController`
- `AdminGestionReportesController`
- `DonacionController`
- `DifusionController`
- `RegistroController`

### **DAO (Data Access Object)**  
Clases encargadas de ejecutar consultas SQL utilizando **JDBC**:
- `UsuarioDAO`
- `ReporteDAO`
- `DonacionDAO`

La conexión se gestiona mediante `ConexionBD.java` y un archivo de propiedades configurable.

---

## 🗃️ Estructura del Proyecto
```text
Project Root
├─ OTERO-FLORENCIA-AP3.pdf             
├─ SQL/
│  ├─ CreacionTablas.sql
│  ├─ InsercionDatos.sql
│  └─ ConsultasPrueba.sql
├─ src/
│  └─ main/
│     ├─ java/
│     │  └─ com/example/app/
│     │     ├─ controllers/
│     │     ├─ dao/
│     │     ├─ database/
│     │     └─ models/
│     └─ resources/
│        ├─ config.properties.example (plantilla de ejemplo)
│        └─ com/example/app/   (vistas .fxml)
└─ README.md
```
## 🛠️ Configuración de la Base de Datos (MySQL) y Archivo de Conexión

Para ejecutar correctamente el sistema, es necesario configurar la conexión con la base de datos.

1. Crear la base de datos ejecutando los scripts ubicados en la carpeta **SQL/**:
   ```sql
   SOURCE SQL/CreacionTablas.sql;
   SOURCE SQL/InsercionDatos.sql;   -- opcional (carga datos de prueba)
2. Dentro de la carpeta: src/main/resources/
   se encuentra el archivo de plantilla: config.properties.example
   Este archivo debe copiarse y renombrarse como: config.properties
3. Completar los datos con las credenciales del entorno local:
   db.url=jdbc:mysql://localhost:3306/nombre_de_la_bd?useSSL=false&serverTimezone=UTC
   db.user=tu_usuario_mysql
   db.password=tu_contraseña_mysql
   
⚠️ Importante:
El archivo config.properties no se incluye en el repositorio por motivos de seguridad.
Se proporciona config.properties.example como plantilla para permitir que cualquier usuario configure sus credenciales locales antes de ejecutar el sistema.
  
## ✅ Estado del Proyecto  
✔ Base de datos configurada y operativa  
✔ Conexión mediante JDBC funcional  
✔ Aplicación completa y en ejecución  
✔ Código documentado y organizado  

---

## 👩‍💻 Autoría  

**Florencia Otero**  
Estudiante de **Licenciatura en Informática**  
Materia: Seminario de Práctica de Informática  
Universidad Siglo 21 — Año **2025**

