# ChatbotTurismo
## dUAck

dUAck es un proyecto que realicé como mi trabajo de fin de grado en la Universidad de Alicante.

Es una aplicación móvil Android con dos funciones principales:
* Si estás haciendo turismo en un pueblo o ciudad y quieres saber qué lugares puedes visitar, dUAck te permite saber qué lugares de interés turístico hay cerca de tu ubicación actual en un rango modificable por el usuario.
* Si quieres viajar a un pueblo o ciudad, te permite saber qué lugares de interés turístico hay allí para planificar mejor tu viaje.

## Descarga

Actualmente dUAck está disponible en Google Play en el siguiente enlace: [https://play.google.com/store/apps/details?id=es.ua.eduardo.duack](https://play.google.com/store/apps/details?id=es.ua.eduardo.duack)

## Funcionamiento

Su funcionamiento es muy simple: al ser un chatbot, las preguntas deben realizarse como si estuvieras hablando con una persona. Por poner algunos ejemplos:
* ¿Qué lugares de interés turístico hay por aquí?
* ¿Hay algún museo en esta ciudad?
* Dime algo interesante para hacer turismo cerca.

Una vez hecha la pregunta, dUAck comprobará si la pregunta puede ser más concreta (tener más datos por los que filtrar) y, en caso de que pueda serlo, le preguntará al usuario si quiere que le haga más preguntas en pos de una búsqueda más precisa diciéndole:
* Muy bien, aunque podría reducir la búsqueda si me contestas las siguiente preguntas. En caso de que quieras los resultados, contesta en alguna de mis preguntas FIN.

Por tanto, el usuario puede terminar ya la consulta pulsando FIN o bien ir respondiendo las preguntas de dUAck para filtrar más su búsqueda. Las preguntas que le realizará son:
* ¿Buscas que sea gratis o cuál es el coste máximo de entrada que quieres pagar?
* ¿Quieres que, a ser posible, el sitio que buscas tenga guía o no?
* ¿Quieres que se hable en algún idioma en particular?
* El lugar turístico que buscas, ¿es de algún tipo en concreto?
* El lugar turístico que buscas, ¿es de algún subtipo en concreto?

Los tipos y subtipos pueden verse en la aplicación. Por poner un ejemplo:
* Tipo: Museo.
* Subtipo: Arqueológico.
* Esta búsqueda filtrará solo los lugares de interés turísitico que sean museos arqueológicos.

Tras finalizar la búsqueda, nos mostrará el o los resultados.

## Datos del resultado

Los datos que actualmente tienen los resultados son:
* Nombre.
* Dirección con enlace a Google Maps.
* Localidad, Provincia, País.
* Página WEB con enlace directo a la aplicación navegador del móvil.
* Coste de la entrada.
* Número de teléfono con enlace directo a la aplicación llamada del móvil.
* Si tiene guía.
* Idioma que se habla.
