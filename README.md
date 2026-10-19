# Meteoritos (Forge 1.20.1)

Lluvia de meteoritos estilo Dedsafio. **Solo servidor**: los jugadores no necesitan instalar el mod.

- Meteoritos enormes con estela larga de fuego y humo oscuro (particulas, no bolas de fuego de Minecraft).
- Explosion real al impactar, magma, fuego y crater ardiendo.
- Hongo de humo estilo bomba atomica.
- Temblor de camara segun la distancia (sin ceguera ni efectos de pocion).
- Pensado para hardcore: los meteoritos nunca apuntan a los jugadores.
- Barra roja "LLUVIA DE METEORITOS 01:46" y aviso "METEORITO 88 m ↑".

## Comandos (operador)

- `/meteoritos iniciar <minutos>`
- `/meteoritos detener`
- `/meteoritos intensidad <1-5>`
- `/meteoritos uno` (lanza uno delante de ti, para probar)
- `/meteoritos destruir <true/false>`
- `/meteoritos estado`

## Compilar

Sube el proyecto a GitHub; la accion de `.github/workflows/build.yml` compila sola.
Luego entra a **Actions**, abre la ultima ejecucion y descarga el artefacto `meteoritos-jar`.

## Instalar

Forge 1.20.1 (47.x). Pon `meteoritos-1.0.0.jar` en la carpeta `mods`.
