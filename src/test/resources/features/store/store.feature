Feature: Store API - Crear y consultar orden

  Scenario Outline: Crear y consultar una orden en la tienda
    Given que envío una solicitud para crear una orden con:
    | id   | petId | quantity | status   |
    | <id> | <petId> | <quantity> | <status> |
    When consulto la orden con el ID <id>
    Then la respuesta debe tener status 200 y los datos deben coincidir

    Examples:
      | id | petId | quantity | status   |
      | 10 | 5     | 1        | placed   |
      | 11 | 7     | 2        | approved |
