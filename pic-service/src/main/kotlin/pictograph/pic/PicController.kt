package pictograph.pic

import jakarta.annotation.PostConstruct
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class PicController {

    private val pics = mutableListOf<Pic>()

    @PostConstruct
    fun load() {
        pics.add(Pic("123", "murphye", "https://cdn.pictograph.com/pics/123.jpg", "Check out my view of Mt. Rainier!"))
        pics.add(Pic("234", "bob123", "https://cdn.pictograph.com/pics/234.jpg", "My daughter Ellie turned 5 today! Happy Birthday!"))
        pics.add(Pic("345", "michelle12", "https://cdn.pictograph.com/pics/345.jpg", "My husband bought me these beautiful roses!"))
        pics.add(Pic("456", "firatkucuk", "https://cdn.pictograph.com/pics/456.jpg", "The landscape is so beautiful here!"))
    }

    @QueryMapping
    fun pics(): List<Pic> {
        throw Exception("ooops!")
        return pics
    }

    @QueryMapping
    fun pic(@Argument picId: String): Pic? {
        for(pic in pics) {
            if(pic.picId == picId) {
                return pic
            }
        }
        return null
    }

    @QueryMapping
    fun picsByUserId(@Argument userId: String): List<Pic> {
        val matchedPics = mutableListOf<Pic>()
        for(pic in pics) {
            if(pic.userId == userId) {
                matchedPics.add(pic)
            }
        }
        return matchedPics
    }
}