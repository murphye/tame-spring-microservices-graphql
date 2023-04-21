package pictograph.user

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.ContextValue
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestHeader

@Controller
class UserController {

    var logger = LoggerFactory.getLogger(UserController::class.java)

    private val users = mutableListOf<User>()

    @PostConstruct
    fun load() {
        users.add(User("murphye", "Eric", "eric@murphy.com", 43))
        users.add(User("bob123", "Bob", "bob123@yahoo.com", 60))
        users.add(User("michelle12", "Michelle", "michelle12@gmail.com", 24))
        users.add(User("firatkucuk", "Fırat", "firat@kucuk.com", 30))
    }

    @QueryMapping
    fun users(@RequestHeader headers: MultiValueMap<String, String>): List<User> {
        return users
    }

    @QueryMapping
    fun user(@Argument userId: String, @ContextValue tenantId: String): User? {

        logger.info("Tenant ID is: $tenantId")

        for(user in users) {
            if(user.userId == userId) {
                return user
            }
        }
        return null
    }

}