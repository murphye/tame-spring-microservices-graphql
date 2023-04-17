# StitchPics GraphQL + Lilo Demo Application

This is an Instagram-like application that has 4 microservices with a GraphQL endpoint:

1. **Pics:** Returns JSON with pictures metadata
2. **Users:** Returns JSON
3. **Likes:** Returns JSON for Users who liked Pics
4. **Gateway** Returns JSON with an aggregation of Pics, Users, and Likes

## Sample Results

### Pics

A single pic result:

```json
{
  "picId": "1234",
  "userId": "murphye",
  "picUrl": "https://stichpics.cdn.com/murphye-1234.jpg",
  "picComment": "My cool pic of Mt. Rainier!"
}
```
### Users

A single user result (authenticated):

```json
{
  "userId": "eric",
  "userName": "Eric Murphy",
  "email": "eric@murphy.com", # Protected value
  "age": 43  # Protected value
}
```

### Likes

All likes for a specific pic:

```json
{
  "picId": "1234",
  "likes": [
    {
      "userId": "bob",
      "comment": "Rad pic!",
      "count": 2
    },
    {
      "userId": "samantha",
      "comment": "Cool!",
      "count": 1
    }
  ]
}
```

### Gateway

GraphQL query:

```
query {
  allPics {
    picId
    userId
    picUrl
    picComment
    likes
  }
}  
```

Result:

```json
[
  {
    "picId": "1234",
    "userId": "eric",
    "picUrl": "https://stichpics.cdn.com/murphye-1234.jpg",
    "picComment": "My cool pic of Mt. Rainier!",
    "likes": [
      {
        "userId": "bob",
        "comment": "Rad pic!",
        "count": 2
      },
      {
        "userId": "samantha",
        "comment": "Nice!",
        "count": 1
      }
    ]
  },
  ...
]
```
