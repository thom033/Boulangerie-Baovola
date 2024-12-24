#!/bin/bash

# Déclaration des variables
work_dir="."
temp="$work_dir/temp"
web="$work_dir/web"
web_xml="$work_dir/web.xml"
lib="$work_dir/lib"
web_apps="/opt/tomcat/webapps"
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
cp -r "$web/." "$temp"

# Copier le fichier [web_xml] vers [temp] + "/WEB-INF"
cp "$web_xml" "$temp/WEB-INF"

# Copier les fichiers .jar dans [lib] vers [temp] + "/WEB-INF/lib"
cp "$lib"/*.jar "$temp/WEB-INF/lib"

# Copier la structure de dossier de src dans WEB-INF/classes
find "$src" -type d -exec mkdir -p "$temp/WEB-INF/classes/{}" \;

# Copier les fichiers .class dans src vers WEB-INF/classes
find "$src" -name "*.class" -exec cp {} "$temp/WEB-INF/classes/{}" \;

# Créer le fichier WAR
cd "$temp"
jar -cvf "$war_name.war" *

# Déployer le fichier WAR dans le dossier webapps de Tomcat
cp "$war_name.war" "$web_apps"