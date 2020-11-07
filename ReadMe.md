
# Phineloops

Dans le cadre de l’UE : JAVA avancé, dispensée pour les étudiants de M1 MIAGE. Nous avons travaillé en trinôme sur un projet en Orienté Objet : InfinityLoop.
Nous sommes l’équipe RAW. RAW est l’acronyme de Riad Fezzoua – Ahmed Haouili – Wahib (Abdelouheb) Mouhoubi.
L’élément le plus important dans l’architecture est l’utilisation du motif d’architecture MVC.

## Compilation
```bash
mvn compile
mvn package 
```

## Exécution
- Générateur 
```bash
java -jar target/phineloops-1.0-jar-with-dependencies.jar --generate wxh --output file
```
- Solveur
```bash
java -jar target/phineloops-1.0-jar-with-dependencies.jar --solve file --output file
```
- Vérificateur
```bash
java -jar target/phineloops-1.0-jar-with-dependencies.jar --check file
```


