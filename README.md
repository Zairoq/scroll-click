# Zairoq Scroll Click

Mod cliente independiente para **Minecraft Java 1.21.11**, usando Fabric.

## Versiones objetivo

- Minecraft: `1.21.11`
- Fabric Loader: `0.20.1`
- Fabric API: `0.141.6+1.21.11`
- Yarn mappings: `1.21.11+build.6`
- Java: `21`

## Comportamiento

- Cada evento vertical de la rueda del ratón genera una acción de clic izquierdo mediante la rutina de ataque de Minecraft.
- Funciona tanto al girar la rueda hacia arriba como hacia abajo.
- Mientras está activado y se encuentra dentro del juego (sin un menú abierto), la rueda no cambia la ranura seleccionada de la hotbar.
- La función empieza activada.
- Se puede asignar una tecla para activarla/desactivarla en `Opciones > Controles > Asignación de teclas`.
- Cuando está desactivado, la rueda vuelve a comportarse normalmente.

Este mod reproduce la acción de ataque a través del cliente de Minecraft; no garantiza una frecuencia de ataque superior a la permitida por el juego o el servidor.

## Compilar con GitHub Actions

Cada push, pull request o ejecución manual compila el proyecto con Java 21 y sube el JAR como artefacto.

1. Abre la pestaña **Actions** del repositorio.
2. Selecciona la ejecución **Build mod**.
3. Cuando termine correctamente, descarga el artefacto `zairoq-scroll-click-1.21.11`.

Para compilar localmente en Windows con JDK 21 y Gradle 9.6.0:

```powershell
Set-ExecutionPolicy -Scope Process Bypass
.\build.ps1
```

El JAR compilado se genera en `build/libs/`. Colócalo en la carpeta `mods` de tu instancia Fabric de Prism Launcher, junto con Fabric API.

## Depuración

Si falla GitHub Actions, abre el paso **Compile mod** y comparte el primer error de Gradle/Java y el bloque `What went wrong`. Si compila pero Minecraft falla al iniciar, comparte `logs/latest.log` o el crash report.
