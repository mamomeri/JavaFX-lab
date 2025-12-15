

## Buenas prácticas para subir un commit (estilo empresa)

### 1) Antes de commitear

* **Actualiza tu rama**: `git pull --rebase` (o `git pull`) para evitar divergencias.
* Revisa qué vas a subir: `git status` y `git diff`.
* No subas basura: agrega/actualiza `.gitignore` (builds, logs, `*.class`, `target/`, `.idea/`, etc.).
* Evita secretos en el repo: contraseñas, tokens, archivos `.env` reales (usa `.env.example`).

### 2) Asegura calidad mínima

* Compila y corre lo básico (según tu stack).
* Corre tests si existen: unitarios / integración.
* Si cambiaste comportamiento, deja evidencia: actualización de README, notas o tests.

### 3) Commit pequeño y con intención

* Un commit = **una idea** (feature/bugfix/refactor). Evita “megacommits”.
* Si hay cambios no relacionados, sepáralos en commits distintos.

### 4) Mensaje de commit (formato recomendado)

Usa algo tipo **Conventional Commits**:

`<tipo>(<scope>): <resumen corto en imperativo>`

Tipos comunes:

* `feat`: nueva funcionalidad
* `fix`: corrección de bug
* `refactor`: reestructura sin cambiar comportamiento
* `test`: agrega/ajusta pruebas
* `docs`: documentación
* `chore`: tareas varias (config, deps, build)
* `perf`: mejora rendimiento

Ejemplos:

* `feat(import): add Excel simulation mode`
* `fix(db): prevent duplicate case insertion`
* `test(validation): add required-field rules`
* `docs(readme): add setup instructions`

### 5) Flujo típico de comandos

```bash
git status
git diff
git add -p              # recomendado: staging por partes
git commit -m "feat(import): add simulation and error report"
git push
```

### 6) Si trabajas con ramas (ideal)

* Crea rama por tarea: `feature/import-excel`, `fix/duplicate-cases`.
* Abre PR (Pull Request) y describe: qué cambia, por qué, cómo probarlo.
* Evita pushear directo a `main` si el proyecto es colaborativo.

Si quieres, te lo adapto a tu repo Java/JavaFX (Maven/Gradle) con un `.gitignore` recomendado y una plantilla corta de PR.

**Ejecuta con: (desde el terminal, no el botón de run)**
mvn javafx:run