# WeatherSensorRestAPI

## Project Overview
WeatherSensorRestAPI is a RESTful Spring Boot application designed to manage sensors and their measurements. The application allows registering sensors, adding measurements, and analyzing data such as rainy days count.

---

## Technology Stack
- **Java 21**
- **Spring Boot**
    - Spring Data JPA
    - Spring Validation
    - Spring Web
- **Hibernate Validator**
- **ModelMapper**
- **PostgreSQL**
- **H2 Database** (for testing)
- **JUnit 5** and **Mockito** (for tests)
- **Lombok**

---

## Setup and Run

### Step 1: Configure the Database
1. Install and start PostgreSQL.
2. Create a database and populate it using the `db_init.sql` and then `db_populate.sql` files.

### Step 2: Application Configuration
Update the `application.properties` file with your database settings:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<your_database>
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
spring.jpa.hibernate.ddl-auto=update
```

### Step 3: Run the Application
1. Build the project:
   ```bash
   ./mvnw clean install
   ```
2. Start the application using the main class:
   ```bash
   java -jar target/<your_jar_file>.jar
   ```

---

## API Documentation

### **Sensors**
#### **GET** `/sensors` — Get all sensors
**Response Example:**
```json
{
    "sensors": [
        { "name": "StPetersburg" },
        { "name": "Novgorod" },
        { "name": "Tver" },
        { "name": "Omsk" }
    ]
}
```

#### **POST** `/sensors/register` — Register a new sensor
**Request Body Example:**
```json
{
    "name": "Anapa"
}
```
**Response:**
- **201 Created** if successful.

---

### **Measurements**
#### **POST** `/measurements` — Get all measurements by sensor
**Request Body Example:**
```json
{
    "name": "StPetersburg"
}
```
**Response Example:**
```json
{
    "measurements": [
        { "value": 24.11, "sensor": { "name": "StPetersburg" }, "raining": false },
        { "value": 34.4, "sensor": { "name": "StPetersburg" }, "raining": true }
    ]
}
```

#### **GET** `/measurements` — Get all measurements
**Response Example:**
```json
{
    "measurements": [
        { "value": 22.93, "sensor": { "name": "Moscow" }, "raining": false },
        { "value": 19.66, "sensor": { "name": "Tver" }, "raining": false }
    ]
}
```

#### **GET** `/measurements/rainyDaysCount` — Get the count of rainy days
**Response Example:**
```json
60
```

#### **POST** `/measurements/add` — Add a new measurement
**Request Body Example:**
```json
{
    "value": 41.2,
    "isRaining": true,
    "sensor": {
        "name": "Anapa"
    }
}
```
**Response:**
- **201 Created** if successful.