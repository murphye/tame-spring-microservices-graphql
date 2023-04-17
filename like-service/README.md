# Like Service

## GraphQL Queries

[Access GraphiQL Console](http://localhost:8081/graphiql)

### Query `likeById` with `curl`

```shell
query='query($picId: ID) {
    likesForPic(picId: $picId) {
        picId
        likes {
            userId
            likeComment
            likeCount
        }
    }
}'
variables='{
  "picId": "123"
}'
curl -i -X POST http://localhost:8083/graphql \
  -H 'Content-Type: application/json' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```