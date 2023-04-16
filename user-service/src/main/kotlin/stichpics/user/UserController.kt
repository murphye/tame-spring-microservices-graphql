package stichpics.user

import jakarta.annotation.PostConstruct
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserController {

    private val users = mutableListOf<User>()

    @PostConstruct
    fun load() {
        users.add(User("eric"))
    }

    @QueryMapping
    fun users(): List<User> {
        return users
    }

    @QueryMapping
    fun userById(@Argument id: String): User {
        return users[0]
    }

}