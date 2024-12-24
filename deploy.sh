#!/bin/bash

set -e  # Arrêter le script en cas d'erreur

# Déclaration des variables
work_dir="."
temp="$work_dir/temp"
web="$work_dir/web"
web_xml="$work_dir/web.xml"
lib="$work_dir/lib"
web_apps="/opt/tomcat/webapps"  # Chemin par défaut pour Tomcat sur Ubuntu
war_name="gotta-taste"
src="$work_dir/src"

# Effacer le dossier [temp]
if [ -d "$temp" ]; then
    rm -rf "$temp"
fi

# Créer la structure de dossier
mkdir -p "$temp/WEB-INF/lib"
mkdir -p "$temp/WEB-INF/classes"

# Copier le contenu de [web] dans [temp]
cp -r "$web/"* "$temp/"

# Copier le fichier [web_xml] vers [temp]/WEB-INF
cp "$web_xml" "$temp/WEB-INF"

# Copier les fichiers .jar dans [lib] vers [temp]/WEB-INF/lib
cp "$lib"/*.jar "$temp/WEB-INF/lib"

# Copier la structure de dossier de [src] dans WEB-INF/classes
rsync -av --include '*/' --exclude '*' "$src/" "$temp/WEB-INF/classes"

# Assurez-vous que le chemin vers le JDK 8 est correctement configuré
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Compilation des fichiers .java dans [src]
# Note : Assurez-vous que javac est installé et configuré dans PATH
find "$src" -name "*.java" > sources.txt
javac -parameters -d "$temp/WEB-INF/classes" -cp "$lib/*" @sources.txt
rm sources.txt  # Supprimer la liste des fichiers après compilation

# Créer un fichier .war nommé [war_name].war à partir de [temp]
cd "$temp"
jar cf "$work_dir/$war_name.war" *

# Effacer le fichier .war dans [web_apps] s'il existe
if [ -f "$web_apps/$war_name.war" ]; then
    rm -f "$web_apps/$war_name.war"
fi

# Copier le fichier .war vers [web_apps]
cp "$work_dir/$war_name.war" "$web_apps"

# Supprimer le fichier .war temporaire
rm "$work_dir/$war_name.war"

echo "Déploiement terminé."
