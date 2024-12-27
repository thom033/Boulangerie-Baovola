@echo off
setlocal enabledelayedexpansion

:: Déclaration des variables
set "work_dir=E:\S5\Boulangerie-Baovola"
set "tomcat_dir=C:\apache-tomcat-10.1.34\bin"

:: Lancer Tomcat
cd /d "%tomcat_dir%"
call catalina.bat start

:: Ouvrir Microsoft Edge à l'adresse localhost:8080
start microsoft-edge:http://localhost:8080

:: Revenir au dossier work_dir
cd /d "%work_dir%"

echo Tomcat lancé et Microsoft Edge ouvert à l'adresse localhost:8080.
pause