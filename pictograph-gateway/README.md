# Pictograph Gateway

## GraphQL Queries with Lilo

### Query All with `curl`

```shell
query='query {
    pics {
        picId
        userId
        picUrl
        picComment
    }
    likes {
        picId
        likes {
            userId
            likeComment
            likeCount
        }
    }
    users {
        userId
        userName
        userAge
        userEmail
    }
}'
variables='{}'
curl -i -X POST http://localhost:8080/graphql \
  -H 'Content-Type: application/json' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```