Qdrant Vector Store Set Up
==========================

### [qdrant intallation instructions](https://qdrant.tech/documentation/guides/installation/)

1. docker pull the image `docker pull qdrant/qdrant`
2. In the project directory, not the root directory, create `docker-compose-qdrant.yml` as the following:
```yaml
services:
    qdrant:
        container_name: qdrant
        image: qdrant/qdrant:latest
        restart: always
        ports:
            - 6333:6333
            - 6334:6334
        expose:
            - 6333
            - 6334
            - 6335
        configs:
            - source: qdrant-config
              target: /qdrant/config/production.yaml
        volumes:
            - ${DOCKER_VOLUME_DIRECTORY:-.}/volumes/qdrant:/qdrant/storage
        healthcheck:
            test: ["CMD", "curl", "-f", "http://localhost:6333/health"]
            interval: 30s
            start_period: 90s
            timeout: 20s
            retries: 3

configs:
    qdrant-config:
        content: |
            log_level: INFO

networks:
    default:
        name: qdrant
```
3. add qdrant dependency(gradle Kotlin DSL):
```kotlin dsl
implementation("org.springframework.ai:spring-ai-qdrant-store-spring-boot-starter")
```

