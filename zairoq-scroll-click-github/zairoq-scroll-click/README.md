# Zairoq Scroll Click

Mod independiente para Minecraft Java 1.21.11 + Fabric.

## Comportamiento

- Cada evento vertical de la rueda del ratón llama a la rutina de ataque vanilla de Minecraft.
- Funciona al girar hacia arriba o hacia abajo.
- Mientras esté activado y estés jugando (sin un menú abierto), la rueda no cambia la ranura seleccionada de la hotbar.
- La función empieza activada.
- Puedes asignar una tecla para activarla/desactivarla en `Opciones > Controles > Asignación de teclas`.
- Cuando está desactivada, la rueda vuelve a comportarse como en vanilla.

## Compilar en Windows

1. Instala un JDK 21 si todavía no lo tienes.
2. Extrae el ZIP a una carpeta.
3. Abre PowerShell dentro de esa carpeta y ejecuta:

   ```powershell
   Set-ExecutionPolicy -Scope Process Bypass
   .\build.ps1
   ```

4. El primer uso descarga Gradle 9.6.0 y las dependencias del proyecto. Se necesita conexión a internet.
5. Si compila correctamente, el JAR queda en `build/libs/`.
6. Copia ese JAR a la carpeta `mods` de tu instancia Fabric 1.21.11 en Prism Launcher. Necesitarás Fabric API.

También puedes abrir el proyecto desde IntelliJ IDEA como un proyecto Gradle.

## Notas

- El proyecto está escrito usando Yarn mappings para 1.21.11.
- El entorno donde se generó este código no dispone de Gradle ni acceso de red, así que no fue posible ejecutar una compilación de prueba. El primer build es la comprobación definitiva; si Loom o las mappings señalan algún error, habrá que corregirlo.
- El mod llama a la rutina de ataque vanilla; la cadencia real contra entidades puede seguir limitada por el sistema de combate y por las reglas del servidor. Comprueba las reglas del servidor antes de usarlo en PvP.

## Compilar automáticamente en GitHub

El repositorio incluye un workflow en `.github/workflows/build.yml`. Una vez que subas estos archivos a GitHub, se ejecutará en cada `push` o `pull request`; también puedes iniciarlo manualmente desde la pestaña **Actions** con **Run workflow**.

- Si compila, abre la ejecución y descarga el artefacto `zairoq-scroll-click-1.21.11`.
- Si falla, entra en la ejecución de **Build mod**, abre el paso **Compile mod** y copia aquí el error desde la primera línea `* What went wrong:` o `error:` hasta el final del stack trace relevante. También puedes descargar el artefacto de informes si aparece.
- No pegues tokens, contraseñas ni secretos de GitHub en el chat.
