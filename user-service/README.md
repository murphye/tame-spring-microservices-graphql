
### userById

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

### users
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









query bookDetails {
userById(userId: "book-1") {
userId
}
}

query users {
    users {
        userId
        userName
        userAge
        userEmail
    }
}