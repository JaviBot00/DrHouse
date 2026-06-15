# DrHouse - Examen Junio 2026

Sistema de gestión de pacientes, tratamientos y medicamentos de una clínica médica. Tres módulos independientes comunicados mediante una API REST sobre JSON: un cliente de importación de datos, un backend de servlets Java y una app Android de consulta.

## Estructura del repositorio

```cmd
.
├── data/                       # CSV de datos de prueba (pacientes, tratamientos, medicamentos)
├── deployment/                  # Scripts de despliegue (docker-compose, bbdd.sql)
├── DrHouse-ImportDataClient/     # Cliente Java (Gradle) - lee el CSV y lo envía al backend
├── DrHouse-Servlets/             # Backend Java EE (Maven) - API REST sobre Tomcat 9
├── DrHouse-Android/               # App Android - consulta pacientes y medicamentos
├── Dockerfile                    # Imagen del backend (Tomcat 9 + JDK 21)
├── examen.txt                    # Enunciado del examen
└── README.md
```

## Arquitectura

```cmd
data.csv → [ImportDataClient] --OkHttp/JSON--> [DrHouse-Servlets/Tomcat] <--JSON-- [DrHouse-Android]
                                                          |
                                                      MySQL (drhouse)
```

### Modelo de datos (árbol)

```cmd
Paciente
 └── Tratamiento[]
      └── Medicamento[]   (referencia al catálogo, relación N:M)

Medicamento[]  (catálogo independiente)
```

## DrHouse-ImportDataClient

Lee `data/data.csv` (formato `;`, secciones `Paciente`, `Tratamiento`, `Medicamento`, `MedicamentoXTratamiento` separadas por líneas en blanco), construye el árbol `Paciente → Tratamiento → Medicamento` y extrae el catálogo de `Medicamento` en paralelo. Envía ambos al backend con un único POST en JSON usando OkHttp.

**Payload enviado a `/import-data`:**

```json
{
  "pacientes": [ { "dni": "...", "tratamientos": [ { "medicamentos": [...] } ] } ],
  "medicamentos": [ { "idMedicamento": "MED001", "nombre": "...", "posologia": "..." } ]
}
```

**Ejecutar:**

```bash
cd DrHouse-ImportDataClient
./gradlew run
```

## DrHouse-Servlets

Backend Java EE (Maven) desplegado en Tomcat 9. Arquitectura en capas: `view` (servlets) → `controller` → `dataservice` (acceso a MySQL vía JDBC).

### Endpoints

| Método | Endpoint | Parámetros | Descripción |
|---|---|---|---|
| GET | `/list-medicamentos` | — | Lista todos los medicamentos del catálogo |
| GET | `/find-medicamento?code={codigo}` | `code` | Busca medicamento por código |
| GET | `/find-paciente?dni={dni}` | `dni` | Busca paciente por DNI |
| GET | `/list-medicamento-tratamiento?dni={dni}&tratamiendo_id={id}` | `dni`, `tratamiendo_id` | Medicamentos de un tratamiento de un paciente |
| POST | `/import-data` | body JSON | Importa el JSON generado por ImportDataClient |

### Base de datos

Esquema definido en `deployment/bbdd.sql` (base de datos `drhouse`, MySQL 8.x):

- `pacientes` (PK `dni`)
- `medicamentos` (PK `codigo`)
- `tratamientos` (PK `id_tratamiento`, FK → `pacientes`)
- `medicamentos_tratamiento` (FK → `tratamientos`, FK → `medicamentos`)

> **Idempotencia**: según el enunciado, importar datos ya existentes no debe duplicarlos. Todos los INSERT del proceso de importación usan `INSERT IGNORE` apoyándose en las claves primarias correspondientes.

## DrHouse-Android

App de consulta con un campo de texto para introducir un DNI y dos botones:

- **Pacientes**: busca el paciente con ese DNI (`/find-paciente?dni=...`)
- **Medicamentos**: lista todo el catálogo (`/list-medicamentos`)

Configurar la IP del backend en `MainActivity.BASE_URL` antes de compilar.

## Configuración / Variables de entorno

Por simplicidad didáctica, las credenciales de la base de datos están hardcodeadas en `DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://bbdd:3306/drhouse";
private static final String USER = "drhouse_user";
private static final String PASSWORD = "onlyforyoureyes";
```

En un entorno real, estos valores deberían externalizarse mediante variables de entorno en lugar de quedar embebidos en el código fuente.

## Despliegue

```bash
cd deployment
docker-compose up -d
```

Esto levanta:

- `servidorweb`: backend Tomcat 9 (puerto `8888`), construido desde `Dockerfile`
- `bbdd`: MySQL 8.0 (puerto `3308`), inicializado con `./db_backup` (colocar `bbdd.sql` ahí)
- `phpmyadmin`: administración web de la BBDD (puerto `8081`)

## Requisitos

- Java 21
- Maven (servlets) / Gradle (import client)
- Docker y Docker Compose
- Android Studio (app Android)

## Autoría

Examen Junio 2026 V1 — DAW
