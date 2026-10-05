# Cotizadores (Spring Boot)

Proyecto con tres servicios REST: envíos, renta de vehículos y hospedaje.

## Requisitos
- Java 17+
- Maven 3.9+

## Ejecutar
```bash
mvn spring-boot:run
```
Pruebas: `mvn test`

## Endpoints
| Servicio  | Método | URL                     |
|-----------|--------|-------------------------|
| Envíos    | POST   | /api/envios/cotizar     |
| Rentas    | POST   | /api/rentas/cotizar     |
| Hospedaje | POST   | /api/hospedajes/cotizar |

## Ejemplos
```bash
curl -X POST localhost:8080/api/envios/cotizar -H "Content-Type: application/json" -d '{
  "codigoPostal":"62577","pesoKg":8.5,"largoCm":40,"anchoCm":30,"altoCm":25,
  "tipoEnvio":"EXPRESS","valorDeclarado":12000}'

curl -X POST localhost:8080/api/rentas/cotizar -H "Content-Type: application/json" -d '{
  "nombreCliente":"Ana Martínez","edadConductor":24,"tipoVehiculo":"SUV",
  "diasRenta":5,"kilometrosEstimados":650,"seguroCompleto":true}'

curl -X POST localhost:8080/api/hospedajes/cotizar -H "Content-Type: application/json" -d '{
  "nombreHuesped":"Carlos López","tipoHabitacion":"DOBLE","numeroNoches":4,
  "numeroHuespedes":2,"temporada":"ALTA","incluyeDesayuno":true,"incluyeEstacionamiento":true}'
```

## Códigos de respuesta
- 200: cotización calculada
- 400: datos inválidos (Bean Validation, JSON mal formado, enum inválido)
- 422: regla de negocio incumplida (`ReglaNegocioException`)
- 500: error interno

## Supuestos
- Envíos: el seguro se suma después del recargo por tipo de envío.
- Renta: el cargo por edad se calcula sobre renta + km adicionales, antes del descuento de 7+ días.
- Hospedaje: los ajustes de temporada y el descuento de 7+ noches se calculan sobre el costo base del hospedaje.
