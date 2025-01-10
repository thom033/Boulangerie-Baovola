#!/bin/bash

set -e  # Arrêter le script en cas d'erreur

# Déclaration des variables
work_dir="."
temp="$work_dir/temp"
web="$work_dir/web"
web_xml="$work_dir/web.xml"
lib="$work_dir/lib"
src="$work_dir/src"
war_name="gotta-taste"
docker_image_name="gotta-taste-tomcat"
docker_container_name="gotta-taste-container"
port=8080  # Changer ici le port local

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


# Configuration JDK pour Java 11
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Compilation des fichiers .java dans [src]
find "$src" -name "*.java" > sources.txt
javac -parameters -d "$temp/WEB-INF/classes" -cp "$lib/*" @sources.txt
rm sources.txt  # Supprimer la liste des fichiers après compilation

# Créer un fichier .war nommé [war_name].war à partir de [temp]
cd "$temp"
jar cf "$work_dir/$war_name.war" *
cd "$work_dir"

# Créer un Dockerfile pour Tomcat
cat > Dockerfile <<EOF
# Utiliser une image Tomcat officielle compatible avec Jakarta EE et Java 11
FROM tomcat:11.0

# Copier le fichier WAR dans le dossier webapps
COPY $war_name.war /usr/local/tomcat/webapps/

# Exposer le port 8080 (interne au conteneur)
EXPOSE 8080

# Lancer Tomcat
CMD ["catalina.sh", "run"]
EOF


# Construire l'image Docker
docker build -t $docker_image_name .

# Arrêter et supprimer le conteneur existant, s'il y en a un
if [ "$(docker ps -aq -f name=$docker_container_name)" ]; then
    docker stop $docker_container_name
    docker rm $docker_container_name
fi

# Lancer un nouveau conteneur sur le port 8087
docker run -d -p $port:8080 --name $docker_container_name $docker_image_name

# Nettoyer les fichiers temporaires
rm -rf "$temp"
rm -f "$work_dir/$war_name.war"
rm Dockerfile

echo "Déploiement terminé. L'application est accessible sur http://localhost:$port/$war_name"