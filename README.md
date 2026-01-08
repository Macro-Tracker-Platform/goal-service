# Goal Service

[**← Back to Main Architecture**](https://github.com/Macro-Tracker-Platform)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

---

[![License](https://img.shields.io/badge/license-Apache%202.0-blue?style=for-the-badge)](LICENSE)
[![Swagger](https://img.shields.io/badge/Swagger-API_Docs-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://macrotracker.uk/webjars/swagger-ui/index.html?urls.primaryName=goal-service)
[![Docker Hub](https://img.shields.io/badge/Docker%20Hub-Image-blue?style=for-the-badge&logo=docker)](https://hub.docker.com/repository/docker/olehprukhnytskyi/macro-tracker-goal-service/general)

**Nutrition Calculation Engine.**

A stateless microservice dedicated to calculating daily caloric needs and macronutrient splits based on user biometrics.

## :zap: Service Specifics

* **Stateless Architecture**: Designed as a pure computational unit with no database dependency, ensuring millisecond response times and effortless horizontal scaling.
* **Scientific Accuracy**: Implements the **Mifflin-St Jeor** equation (standard for clinical settings) with activity level multipliers to determine Basal Metabolic Rate (BMR) and Total Daily Energy Expenditure (TDEE).
* **Goal Adaptation**: Automatically adjusts calculations based on the user's specific objective (Weight Loss, Maintenance, or Muscle Gain).
* **Observability**: Built-in **JSON Structured Logging** (Logstash) and OpenTelemetry instrumentation for seamless integration with the ELK Stack.

---

## :electric_plug: API & Communication

* **Internal Communication**:
    * Primarily consumed by the **User Service** (via Feign Client) during profile creation or updates.
    * Exposes a `POST /api/goals` endpoint accepting user details (Age, Weight, Height, Gender, Activity Level).
* **Public Documentation**:
    * Fully documented via **OpenAPI 3.0** (SpringDoc).

---

## :hammer_and_wrench: Tech Details

| Component | Implementation |
| :--- | :--- |
| **Framework** | Java 21, Spring Boot 3 |
| **Validation** | Jakarta Bean Validation (Constraints on inputs) |
| **Logging** | Logstash Logback Encoder (JSON format) |
| **Build Tool** | Maven, GitHub Actions |

---

## :gear: Environment Variables

Required variables for `local` or `k8s` deployment:

| Variable | Purpose |
| :--- | :--- |
| **Application Config** | |
| `MACRO_TRACKER_URL` | Public URL of the application (e.g., `https://macrotracker.uk`). Used to configure Swagger UI redirect URLs behind a proxy. |
| **Observability** | |
| `LOGGING_LEVEL_ROOT` | *(Optional)* Root logging level (default: `INFO`). |
| **Server** | |
| `SERVER_PORT` | *(Optional)* Port to run on (default: `8080`). |

---

## :whale: Quick Start

```bash
# Pull from Docker Hub
docker pull olehprukhnytskyi/macro-tracker-goal-service:latest

# Run
docker run -p 8080:8080 -e MACRO_TRACKER_URL=http://localhost:8080 olehprukhnytskyi/macro-tracker-goal-service:latest
```

---

## :balance_scale: License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.