# proyecto-gestionTareas
# Ejecución
Para la correcta ejecucion del codigo es necesario tener maven instalado y Java 17
*Instalación de Maven en linux*
sudo apt update
sudo apt install maven

El código posee un makefile y ejecutando el siguiente formato es posible correr  el codigo\n
**make run**

De igual forma para la ejecución de las pruebas utilizar el siguiente comando
**make test**

# ¿Cómo especificarías mejor el requerimiento? (Validación)

**Requerimiento:** Asegurar que la aplicación para la gestión de tareas sea intuitiva de usar y fácilmente comprensible para el usuario final, verificando que la interfaz y las funcionalidades respondan adecuadamente a las expectativas del cliente en términos de 
facilidad de aprendizaje, navegación clara y experiencia de usuario positiva.

# ¿Cómo asegurarías que el programa cumpla el requerimiento? (Verificación)

**Cumplir Requerimiento:** Esto puede ser un reto ya que ni el cliente mismo sabe explicar bien su requerimiento y lo que considera como un gestionador de tareas sencillo. A pesar de esa complicación, considero que una buena forma de abordarlo sería poniendo 
especial enfásis en que cada una de las acciones sea entendible con solo leerla. Y ya que nos limitamos a solo trabajar con la consola, la mejor forma sería generando instrucciones autoexplicativas y procurar dar algo de estética al sistema de tal forma que se 
parezca a uno hecho en papel.

# Organización, explicar cómo se organizó el proyecto y el flujo de trabajo de éste.

**Organización:** Aspectos relacionados con el desarrollo propio del código fue manejado por Diego Acevedo en su totalidad, mientras que el manejo con github, el proceso de registro de pruebas y otros apartados fueron abordados por Sebastián Arrieta.

# Incluir evidencia de flujo de trabajo y configuraciones realizadas (Imágenes de pantalla)

## GitFlow
![image](https://github.com/user-attachments/assets/8c8c9823-2852-47f9-af34-31cc15053529)

## rulesets para las ramas principales
![image](https://github.com/user-attachments/assets/cc556909-c7e3-47d4-8460-6c2afff026d3)

## Integración de github con Slack
![image](https://github.com/user-attachments/assets/799a6c52-8fab-41ef-9069-fc1e5bf3d470)


# Problemas encontrados y como se solucionaron.

**Problemas encontrados:** Los principales problemas encontrados en el código fueron el como gestionar el traspaso desde una lista semanal a una lista archivada de tal forma que el id se mantuviera y no se generaran duplicados, 
nos interesaba que el cliente pudiese conseguir lo que pidió sin que las copias pudiesen confundirle.

Otro problema relacionado iba en torno a como mostrar la imagen de forma clara con las limitaciones de la consola. Pues en otro contexto hubieramos usado una api de calendarios para hacerlo más visible. 
El problema es que si mostrabamos un calendario por consola, se volvería tedioso de ver tras cada pequeña acción. Fue por esto que para poder cumplir con el requerimiento de la forma más eficiente que se nos ocurrió, 
es que decidimos simplificar la lógica a un tablero por día de la semana, y agregar la fecha en el detalle de cada lunes. De este modo, todas las tareas asignadas a un día lunes (ya sea lunes 8 o lunes 15), se visualizan en el mismo lugar, 
pudiendo ser diferenciadas en detalle gracias a las fechas especificas.

## Contribuir

Para contribuir con este proyecto se podran realizar Pull Request a la rama develop para que estos queden registrados

## License

[MIT](https://choosealicense.com/licenses/mit/)
