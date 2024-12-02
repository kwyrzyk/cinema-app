#!/bin/bash

# Instalacja zależności
echo "Sprawdzanie zależności..."
if ! dpkg -s openjdk-21-jdk maven unzip wget > /dev/null 2>&1; then
    echo "Instalowanie zależności..."
    sudo apt update
    sudo apt install -y openjdk-21-jdk maven unzip wget
else
    echo "Zależności już zainstalowane."
fi

# Pobranie i rozpakowanie JavaFX
echo "Sprawdzanie JavaFX..."
if [ ! -d "javafx-sdk-23.0.1" ]; then
    if [ ! -f "javafx-sdk-23.0.1-linux-x64.zip" ]; then
        echo "Pobieranie JavaFX..."
        wget -q https://download2.gluonhq.com/openjfx/23.0.1/javafx-sdk-23.0.1-linux-x64.zip
    fi
    echo "Rozpakowywanie JavaFX..."
    unzip -o javafx-sdk-23.0.1-linux-x64.zip
else
    echo "JavaFX już rozpakowane."
fi
export JAVA_FX_HOME=$PWD/javafx-sdk-23.0.1

# Budowanie aplikacji
echo "Sprawdzanie kompilacji aplikacji..."
if [ ! -f "target/moja-aplikacja-javafx-1.0-SNAPSHOT.jar" ]; then
    echo "Budowanie aplikacji..."
    mvn clean install -DskipTests
else
    echo "Aplikacja już zbudowana."
fi

# Uruchomienie aplikacji
echo "Uruchamianie aplikacji..."
mvn javafx:run -f pom.xml
