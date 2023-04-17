package stitchpics.like

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

        val likes = mutableListOf<Like>()
        likes.add(Like("123-1","firatkucuk","Beautiful mountain!", 1))
        likes.add(Like("123-2","michelle12","Majestic!", 2))
        likesMap["123"] = likes

        likes.clear()
        likes.add(Like("234-1","firatkucuk","Happy Birthday!", 1))
        likes.add(Like("234-2","michelle12","Happy Birthday!", 2))
        likes.add(Like("234-3","murphye","Happy B-Day.", 3))
        likes.add(Like("234-3","bob123","Thanks All!", 3))
        likesMap["234"] = likes

        likes.clear()
        likes.add(Like("345-1","michelle12","Jealous!", 1))
        likesMap["345"] = likes

        likes.clear()
        likes.add(Like("456-1","murphye","Pretty!", 1))
        likes.add(Like("456-2","michelle12","I want to go there!", 2))
        likesMap["456"] = likes
    }

    @QueryMapping
    fun likesByPicId(@Argument picId: String): List<Like>? {
        return likesMap.get(picId)
    }

}