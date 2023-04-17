# Pic Service

## GraphQL Queries

[Access GraphiQL Console](http://localhost:8081/graphiql)

### Query `pic` with `curl`

```shell
query='query($picId: ID) {
    pic(picId: $picId) {
        picId
        userId
        picUrl
        picComment
    }
}'
variables='{
  "picId": "123"
}'
curl -i -X POST http://localhost:8082/graphql \
  -H 'Content-Type: application/json' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```

### Query `pics` with `curl`
```shell
query='query {
    pics {
        picId
        userId
        picUrl
        picComment
    }
}'
variables='{}'
curl -i -X POST http://localhost:8082/graphql \
  -H 'Content-Type: application/json' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```