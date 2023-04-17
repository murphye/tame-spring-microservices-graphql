package stitchpics.user

import jakarta.annotation.PostConstruct
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserController {

    private val users = mutableListOf<User>()

    @PostConstruct
    fun load() {
        users.add(User("murphye", "Eric", "eric@murphy.com", 43))
        users.add(User("bob123", "Bob", "bob123@yahoo.com", 60))
        users.add(User("michelle12", "Michelle", "michelle12@gmail.com", 24))
        users.add(User("firatkucuk", "Fırat", "firat@kucuk.com", 30))
    }

    @QueryMapping
    fun users(): List<User> {
        return users
    }

    @QueryMapping
    fun userById(@Argument userId: String): User {
        return users[0]
    }

}