# Like Service

## GraphQL Queries

[Access GraphiQL Console](http://localhost:8081/graphiql)

### Query `likeById` with `curl`

```shell
query='query($userId: ID) {
    userById(userId: $userId) {
        userId
        userName
        userAge
        userEmail
    }
}'
variables='{
  "userId": "murphye"
}'
curl -i -X POST http://localhost:8083/graphql \
  -H 'Content-Type: application/json' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```

### Query `likes` with `curl`
```shell
query='query {
    users {
        userId
        userName
        userAge
        userEmail
    }
}'
variables='{}'
curl -i -X POST http://localhost:8083/graphql \
  -H 'Content-Type: application/json' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```