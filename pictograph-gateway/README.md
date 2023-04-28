# Pictograph Gateway

## Run the Demo

### 1. Run Zipkin for Distributed Tracing Visualization

```shell
docker run -d --name zipkin -p 9411:9411 openzipkin/zipkin
```

Navigate your browser to http://localhost:9411

### 2. Run GraphQL Services for Pictograph

For `pictograph-gateway`, `pic-service`, `like-service`, and `user-service`, you can simply run `./gradlew bootRun` 
separately in each project.

In a separate terminal, prepare to run `curl` commands to run the demo.

## GraphQL Queries with Lilo

### Query a User with `curl`

```shell
query='query($userId: ID) {
    user(userId: $userId) {
        userId
        userName
        userEmail
    }
    picsByUserId(userId: $userId) {
        picId
        picUrl
    }
    likesByUserId(userId: $userId) {
        picId
    }
}'
variables='{
  "userId": "murphye"
}'
curl -i -X POST http://localhost:8080/graphql \
  -H 'Content-Type: application/json' \
  -H 'tenant-id: acme' \
  -d @- <<EOF
      {"query": "$(echo $query)", "variables": $variables}
EOF
```

#### Sample Response

```json
{
  "data": {
    "user": {
      "userId": "murphye",
      "userName": "Eric",
      "userEmail": "eric@murphy.com"
    },
    "picsByUserId": [
      {
        "picId": "123",
        "picUrl": "https://cdn.pictograph.com/pics/123.jpg"
      }
    ],
    "likesByUserId": [
      {
        "picId": "234"
      },
      {
        "picId": "456"
      }
    ]
  },
  "extensions": {
    "tracing": {
      "version": 1,
      "startTime": "2023-04-28T03:24:40.763514Z",
      "endTime": "2023-04-28T03:24:40.798796Z",
      "duration": 35294500,
      "parsing": {
        "startOffset": 1042042,
        "duration": 974250
      },
      "validation": {
        "startOffset": 1552625,
        "duration": 497708
      },
      "execution": {
        "resolvers": [
          {
            "path": [
              "likesByUserId"
            ],
            "parentType": "Query",
            "returnType": "[Like]",
            "fieldName": "likesByUserId",
            "startOffset": 3121833,
            "duration": 27504375
          },
          {
            "path": [
              "likesByUserId",
              0,
              "picId"
            ],
            "parentType": "Like",
            "returnType": "String",
            "fieldName": "picId",
            "startOffset": 30817083,
            "duration": 10042
          },
          {
            "path": [
              "likesByUserId",
              1,
              "picId"
            ],
            "parentType": "Like",
            "returnType": "String",
            "fieldName": "picId",
            "startOffset": 30891375,
            "duration": 8292
          },
          {
            "path": [
              "picsByUserId"
            ],
            "parentType": "Query",
            "returnType": "[Pic]",
            "fieldName": "picsByUserId",
            "startOffset": 2742375,
            "duration": 28316292
          },
          {
            "path": [
              "picsByUserId",
              0,
              "picId"
            ],
            "parentType": "Pic",
            "returnType": "ID!",
            "fieldName": "picId",
            "startOffset": 31108208,
            "duration": 4375
          },
          {
            "path": [
              "picsByUserId",
              0,
              "picUrl"
            ],
            "parentType": "Pic",
            "returnType": "String",
            "fieldName": "picUrl",
            "startOffset": 31131500,
            "duration": 3042
          },
          {
            "path": [
              "user"
            ],
            "parentType": "Query",
            "returnType": "User",
            "fieldName": "user",
            "startOffset": 1792375,
            "duration": 33254250
          },
          {
            "path": [
              "user",
              "userId"
            ],
            "parentType": "User",
            "returnType": "ID!",
            "fieldName": "userId",
            "startOffset": 35145250,
            "duration": 8125
          },
          {
            "path": [
              "user",
              "userName"
            ],
            "parentType": "User",
            "returnType": "String",
            "fieldName": "userName",
            "startOffset": 35175125,
            "duration": 2792
          },
          {
            "path": [
              "user",
              "userEmail"
            ],
            "parentType": "User",
            "returnType": "String",
            "fieldName": "userEmail",
            "startOffset": 35197333,
            "duration": 2625
          }
        ]
      }
    }
  }
}
```

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
    ],
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
    ]
  }
}
```