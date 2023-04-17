# User Service

## GraphQL Queries

[Access GraphiQL Console](http://localhost:8081/graphiql)

### Query `userById` with `curl`

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
curl -i -X POST http://localhost:8081/graphql \
  -H 'Content-Type: application/json' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```

### Query `users` with `curl`
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
curl -i -X POST http://localhost:8081/graphql \
  -H 'Content-Type: application/json' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```