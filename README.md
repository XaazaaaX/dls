# Anleitung zur Ausführung der DLSV-Anwendung mit Docker

## Voraussetzungen
- Bitte stellen Sie sicher, dass Docker installiert und gestartet ist (empfohlen wird Docker Desktop mit **mindestens Version 4.17.0**).
- [https://www.docker.com/](https://www.docker.com/)
- [https://rancherdesktop.io/](https://rancherdesktop.io/)

---

## Einmalige Vorbereitung

Nach der Installation von Docker ist **keine Internetverbindung mehr erforderlich**, da alle benötigten Docker-Images bereits mitgeliefert wurden.

1. **Starten Sie Docker**

   Vergewissern Sie sich, dass Docker gestartet wurde, bevor Sie fortfahren.

2. **Laden Sie die Docker-Images**

   Führen Sie die Datei `load_images.bat` aus, um die mitgelieferten Docker-Images zu importieren.

   **Hinweis:** Dieser Schritt ist **nur einmalig erforderlich**.

3. **Starten Sie die Anwendung**

   Durch Ausführen der Datei `startup.bat` wird die DLSV-Anwendung gestartet. Beim Schließen des Fensters wird die Anwendung gestoppt.

---

# Lokale Diensteübersicht (docker-compose)

## 1. DLSV Web-App

- **Dienstname:** `dlsa-app`
- **Port:** 80
- **Link:** [http://localhost:80](http://localhost:80)
- **Beschreibung:** Angular/Web-Frontend der Anwendung
- **Benutzer:**
  - Administrator: `admin` / `admin`
  - Sachbearbeiter: `bearbeiter` / `bearbeiter`
  - Gastzugang: `gast` / `gast`

---

## 2. API (Backend – Spring Boot mit Swagger UI)

- **Dienstname:** `dlsa-api`
- **Swagger UI:** [http://localhost:5005/swagger-ui/index.html](http://localhost:5005/swagger-ui/index.html)
- **Beschreibung:** REST-API mit Swagger-Dokumentation

---

## 3. PostgreSQL-Datenbank

- **Dienstname:** `db`
- **Port:** 5432
- **Verbindung pgadmin:**
- **Datenbankname:** `dlsa`
- **Benutzer:** `user`
- **Passwort:** `pass`

---

## 4. pgAdmin (PostgreSQL Web GUI)

- **Dienstname:** `pgadmin`
- **Port:** 5050
- **URL:** [http://localhost:5050](http://localhost:5050)
- **Login:**
- **E-Mail:** `admin@admin.com`
- **Passwort:** `admin`
- **Verbindung zur Datenbank:**
- **Host:** `db`
- **Port:** 5432
- **Benutzer:** `user`
- **Passwort:** `pass`
- **Datenbank:** `dlsa`
