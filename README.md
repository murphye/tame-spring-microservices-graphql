# *"Pictograph"* GraphQL + Lilo Demo

*Pictograph* is a demo application with an Instagram-like API that is implemented with GraphQL. It's comprised of the following microservices:

1. **pic-service:** Returns JSON with Pic metadata
2. **user-service:** Returns JSON for User data
3. **like-service:** Returns JSON for Users who liked Pics
4. **pictograph-gateway** Returns JSON with data aggregated from Pics, Users, and Likes

**pictograph-gateway** uses GraphQL Stiching implemented with Lilo to combine the results from the other 3 services.
