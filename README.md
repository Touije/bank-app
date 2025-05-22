# Application Bancaire Microservices

## 📝 Description
Cette application bancaire est construite selon une architecture microservices, composée de deux services principaux :
- `client-service` : Gestion des clients
- `compte-service` : Gestion des comptes bancaires

L'architecture utilise une communication asynchrone via Kafka pour la création automatique de comptes lors de l'inscription d'un nouveau client.

## 🏗 Architecture

### Services

#### 1. Service Client (Port 8081)
- **Rôle** : Gestion des informations clients
- **Fonctionnalités** :
  - CRUD des clients
  - Publication d'événements Kafka lors de la création d'un client
- **Base de données** : PostgreSQL (clientdb)
- **Points d'entrée** : `/api/clients`

#### 2. Service Compte (Port 8082)
- **Rôle** : Gestion des comptes bancaires
- **Fonctionnalités** :
  - CRUD des comptes
  - Opérations de crédit/débit
  - Création automatique de comptes (courant et épargne) pour les nouveaux clients
- **Base de données** : PostgreSQL (comptedb)
- **Points d'entrée** : `/api/comptes`

### Communication
- **Kafka** : Utilisé pour la communication asynchrone entre les services
- **Topic** : `client-created` (création de client)
- **Groupe de consommateurs** : `compte-group`

## 🛠 Technologies Utilisées

### Dépendances Principales
- **Spring Boot 3.4.6** : Framework principal
- **Spring Data JPA** : Persistance des données
- **PostgreSQL** : Base de données
- **Spring Kafka** : Communication asynchrone
- **Lombok** : Réduction du code boilerplate
- **Maven** : Gestion des dépendances

### Versions des Composants
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>3.4.6</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
        <version>3.4.6</version>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.5</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
        <version>3.3.6</version>
    </dependency>
</dependencies>
```

## ⚙️ Configuration

### 1. Bases de Données
```properties
# client-service
spring.datasource.url=jdbc:postgresql://localhost:5432/clientdb
spring.datasource.username=postgres
spring.datasource.password=postgres

# compte-service
spring.datasource.url=jdbc:postgresql://localhost:5432/comptedb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 2. Kafka
```properties
# Configuration commune
spring.kafka.bootstrap-servers=localhost:9092

# client-service (Producteur)
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

# compte-service (Consommateur)
spring.kafka.consumer.group-id=compte-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
```

## 🚀 Démarrage

### Prérequis
1. Java 23
2. PostgreSQL 17
3. Kafka 3.8.1
4. Maven

### Étapes de Démarrage
1. **Démarrer PostgreSQL**
   ```powershell
   # Vérifier que le service est en cours d'exécution
   Get-Service postgresql-x64-17
   ```

2. **Démarrer Kafka**
   ```powershell
   # Démarrer ZooKeeper
   .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

   # Démarrer Kafka
   .\bin\windows\kafka-server-start.bat .\config\server.properties
   ```

3. **Lancer les Services**
   ```powershell
   # Service Client
   cd client-service
   mvn spring-boot:run

   # Service Compte
   cd compte-service
   mvn spring-boot:run
   ```

4. **Vérifier les Services**
   ```powershell
   .\check-services.ps1
   ```

5. **Exécuter les Tests**
   ```powershell
   .\test-api.ps1
   ```

## 💡 Astuces et Bonnes Pratiques

### 1. Architecture
- **Séparation des DTOs** : Utilisation de DTOs distincts pour les entrées/sorties API
- **Mapping** : Utilisation de mappers dédiés pour la conversion DTO/Entity
- **Validation** : Validation des données d'entrée avec des exceptions métier

### 2. Kafka
- **Idempotence** : Les consommateurs sont conçus pour être idempotents
- **Gestion des Erreurs** : Logging des erreurs avec possibilité d'implémenter une Dead Letter Queue
- **Sérialisation** : Utilisation de JsonSerializer/JsonDeserializer pour la sérialisation des événements

### 3. Base de Données
- **Transactions** : Utilisation de `@Transactional` pour garantir l'intégrité des données
- **Indexation** : Index sur les champs fréquemment recherchés (email, numeroCompte)
- **Contraintes** : Contraintes d'unicité sur les champs critiques

## 🔍 Points d'Attention

1. **Sécurité**
   - Ajouter une authentification (Spring Security)
   - Chiffrer les données sensibles
   - Implémenter HTTPS

2. **Performance**
   - Ajouter du caching (Redis)
   - Optimiser les requêtes JPA
   - Implémenter la pagination

3. **Monitoring**
   - Ajouter Spring Actuator
   - Implémenter des métriques Prometheus
   - Configurer des alertes

## 🛠 Améliorations Futures

1. **Fonctionnalités**
   - Ajout d'un service de transaction
   - Implémentation d'un service de notification
   - Ajout d'un service de reporting

2. **Architecture**
   - Ajout d'une API Gateway
   - Implémentation d'un service de découverte (Eureka)
   - Configuration d'un load balancer

3. **DevOps**
   - Configuration de CI/CD
   - Containerisation avec Docker
   - Orchestration avec Kubernetes

## 📚 Documentation Supplémentaire

- [Documentation Spring Boot](https://spring.io/projects/spring-boot)
- [Documentation Spring Kafka](https://spring.io/projects/spring-kafka)
- [Documentation PostgreSQL](https://www.postgresql.org/docs/)
- [Documentation Apache Kafka](https://kafka.apache.org/documentation/) 