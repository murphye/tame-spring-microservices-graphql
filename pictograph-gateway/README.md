# Pictograph Gateway

## GraphQL Queries with Lilo

### Query All with `curl`

```shell
query='query {
    users
    pics
    likes
}'
variables='{}'
curl -i -X POST http://localhost:8080/graphql \
  -H 'Content-Type: application/json' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```