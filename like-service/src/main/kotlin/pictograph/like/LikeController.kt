package pictograph.like

import jakarta.annotation.PostConstruct
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class LikeController {
    // picId is the key
    private val likesMap = mutableMapOf<String,List<Like>>()

    @PostConstruct
    fun load() {

        var likes = mutableListOf<Like>()
        likes.add(Like("123-1","firatkucuk","123","Beautiful mountain!", 1))
        likes.add(Like("123-2","michelle12", "123","Majestic!", 2))
        likesMap["123"] = likes

        likes = mutableListOf()
        likes.add(Like("234-1","firatkucuk", "234", "Happy Birthday!", 1))
        likes.add(Like("234-2","michelle12", "234", "Happy Birthday!", 2))
        likes.add(Like("234-3","murphye", "234", "Happy B-Day.", 3))
        likesMap["234"] = likes

        likes = mutableListOf()
        likes.add(Like("345-1","michelle12", "345","Jealous!", 1))
        likesMap["345"] = likes

        likes = mutableListOf()
        likes.add(Like("456-1","murphye", "456", "Pretty!", 1))
        likes.add(Like("456-2","michelle12", "456", "I want to go there!", 2))
        likesMap["456"] = likes
    }

    @QueryMapping
    fun likes(): List<LikesForPic> {
        val likesForPics = mutableListOf<LikesForPic>()

        for(picId in likesMap.keys) {
            likesForPics.add(LikesForPic(picId, likesMap.get(picId)))
        }

        return likesForPics
    }

    @QueryMapping
    fun likesForPic(@Argument picId: String): LikesForPic {
        return LikesForPic(picId, likesMap.get(picId))
    }

    @QueryMapping
    fun likesByUserId(@Argument userId: String): List<Like> {
        val likeMatches = mutableListOf<Like>()
        for(likeValue in likesMap.values) {
            for(like in likeValue) {
                if(like.userId == userId) {
                    likeMatches.add(like)
                }
            }
        }
        return likeMatches
    }
}