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

#### Sample Response

```json
{
  "data": {
    "users": [
      {
        "userId": "murphye",
        "userName": "Eric",
        "userAge": 43,
        "userEmail": "eric@murphy.com"
      },
      {
        "userId": "bob123",
        "userName": "Bob",
        "userAge": 60,
        "userEmail": "bob123@yahoo.com"
      },
      {
        "userId": "michelle12",
        "userName": "Michelle",
        "userAge": 24,
        "userEmail": "michelle12@gmail.com"
      },
      {
        "userId": "firatkucuk",
        "userName": "Fırat",
        "userAge": 30,
        "userEmail": "firat@kucuk.com"
      }
    ],
    "pics": [
      {
        "picId": "123",
        "userId": "murphye",
        "picUrl": "https://cdn.pictograph.com/pics/123.jpg",
        "picComment": "Check out my view of Mt. Rainier!"
      },
      {
        "picId": "234",
        "userId": "bob123",
        "picUrl": "https://cdn.pictograph.com/pics/234.jpg",
        "picComment": "My daughter Ellie turned 5 today! Happy Birthday!"
      },
      {
        "picId": "345",
        "userId": "michelle12",
        "picUrl": "https://cdn.pictograph.com/pics/345.jpg",
        "picComment": "My husband bought me these beautiful roses!"
      },
      {
        "picId": "456",
        "userId": "firatkucuk",
        "picUrl": "https://cdn.pictograph.com/pics/456.jpg",
        "picComment": "The landscape is so beautiful here!"
      }
    ],
    "likes": [
      {
        "picId": "123",
        "likes": [
          {
            "userId": "firatkucuk",
            "likeComment": "Beautiful mountain!",
            "likeCount": 1
          },
          {
            "userId": "michelle12",
            "likeComment": "Majestic!",
            "likeCount": 2
          }
        ]
      },
      {
        "picId": "234",
        "likes": [
          {
            "userId": "firatkucuk",
            "likeComment": "Happy Birthday!",
            "likeCount": 1
          },
          {
            "userId": "michelle12",
            "likeComment": "Happy Birthday!",
            "likeCount": 2
          },
          {
            "userId": "murphye",
            "likeComment": "Happy B-Day.",
            "likeCount": 3
          }
        ]
      },
      {
        "picId": "345",
        "likes": [
          {
            "userId": "michelle12",
            "likeComment": "Jealous!",
            "likeCount": 1
          }
        ]
      },
      {
        "picId": "456",
        "likes": [
          {
            "userId": "murphye",
            "likeComment": "Pretty!",
            "likeCount": 1
          },
          {
            "userId": "michelle12",
            "likeComment": "I want to go there!",
            "likeCount": 2
          }
        ]
      }
    ]
  }
}
```