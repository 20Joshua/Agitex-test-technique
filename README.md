#### Project Documenation ####

# cd src/main/resources et docker-compose up -d pour démarrer les conteneurs docker
NB : Toutes les requêtes doivent commencer par "/api" et le port local pour la connexion à la base de donnée est le 3308 

1. Importation de fichiers clients
URL: /clients/upload
Méthode HTTP: POST
Paramètres:
file: Fichier à importer (multipart)
fileType: Type de fichier à importer ("csv" => pour les fichiers csv, "pdf" => pour les fichiers pdf,"xml" => pour les fichiers xml,"txt" => pour les fichiers texte,"xlsx" => pour les fichiers excel,"docx" => pour les fichiers document word,"json" => pour les fichiers json)
Description: Permet d'importer des données clients à partir d'un fichier. Le type de fichier est spécifié pour utiliser la stratégie appropriée pour la lecture du fichier.
2. Exportation de la liste des clients au format PDF
URL: /clients/export-pdf
Méthode HTTP: GET
Description: Génère et télécharge la liste des clients au format PDF.
3. Exportation de la liste des clients au format Excel
URL: /clients/export-excel
Méthode HTTP: GET
Description: Génère et télécharge la liste des clients au format Excel.
4. Récupération de tous les clients
URL: /clients/getAll
Méthode HTTP: GET
Description: Récupère tous les clients enregistrés dans la base de données.
5. Mise à jour d'un client
URL: /clients/{id}
Méthode HTTP: PUT
Paramètres:
id: Identifiant du client à mettre à jour
client: Données du client à mettre à jour
Description: Met à jour les informations d'un client spécifique.
6. Suppression d'un client
URL: /clients/{id}
Méthode HTTP: DELETE
Paramètres:
id: Identifiant du client à supprimer
Description: Supprime un client spécifique de la base de données.
7. Calcul de la moyenne des salaires par profession
URL: /clients/moyenne-salaires
Méthode HTTP: GET
Paramètres:
profession: Profession pour laquelle calculer la moyenne des salaires
Description: Calcule et renvoie la moyenne des salaires pour une profession donnée.
