PGVector store SET UP Instructions
==================================

### [PGVector open source project](https://github.com/pgvector/pgvector)


1. Add dependency (gradle kotlin dsl):
```kotlin dsl
implementation("org.springframework.ai:spring-ai-pgvector-store-spring-boot-starter")
```
2. docker pull image(specifically `ankane/pgvector` or `pgvector/pgvector` with more version)
```cli
docker pull ankane/pgvector
```
[reference:](https://docs.haystack.deepset.ai/docs/pgvectordocumentstore)
3. Create initial script to create required vector store extension under `src/main/resource/sql/`:
```sql
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store (
	id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
	content text,
	metadata json,
	embedding vector(1024)
);

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);
```
4. create `docker-compose-pgvector.yml`:
```yaml
services:
    pgvector:
        container_name: pgvector
        image: ankane/pgvector:latest
        ports:
            - 5432:5432
        expose:
            - 5432
        environment:
            - POSTGRES_USER=postgres
            - POSTGRES_PASSWORD=postgres
            - POSTGRES_DB=postgres
        networks:
            - default
        volumes:
            - ${DOCKER_VOLUME_DIRECTORY:-.}/volumes/pgvector/:/var/lib/postgresql/
            - ${DOCKER_VOLUME_DIRECTORY:-.}/src/main/resources/sql/pgvector-init.sql:/docker-entrypoint-initdb.d/init.sql
        healthcheck:
            test: ["CMD", "pg_isready"]
            interval: 30s
            start_period: 90s
            timeout: 20s
            retries: 3

networks:
    default:
        name: pgvector
```
5. configure the `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres
  ai:
	vectorstore:
	  pgvector:
		index-type: HNSW
		distance-type: COSINE_DISTANCE
		dimension: 1024
```
6. add spring boot jdbc and postgresql dependencies to build.gradle.kts
