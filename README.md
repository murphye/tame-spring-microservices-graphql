# *"Pictograph"* GraphQL + Lilo Demo

*Pictograph* is a demo application with an API that is implemented with GraphQL. It's meant to mimic a photo sharing website API where users can "like" pictures.

*Pictograph* is comprised of the following microservices:

1. **pic-service:** Returns JSON with Picture metadata
2. **user-service:** Returns JSON for User data
3. **like-service:** Returns JSON for Users who liked Pictures
4. **pictograph-gateway** Returns JSON with data aggregated from Pics, Users, and Likes

**pictograph-gateway** uses GraphQL Stiching implemented with Lilo to combine the results from the other 3 services.
