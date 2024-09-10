MVN = mvn

# Por defecto, construye el proyecto
all: build

# Regla para construir el proyecto
build:
	$(MVN) clean package

# Regla para ejecutar los tests
test:
	$(MVN) test

# Regla para ejecutar la aplicación principal
run: build
	$(MVN) exec:java -Dexec.mainClass=App

# Regla para limpiar los artefactos de construcción
clean:
	$(MVN) clean
