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
3. create `docker-compose-pgvector.yml`:
```yaml

```
