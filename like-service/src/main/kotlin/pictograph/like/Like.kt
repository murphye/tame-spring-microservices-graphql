package pictograph.like

data class Like(
    var likeId: String,
    var userId: String,
    val picId: String,
    var likeComment: String,
    var likeCount: Int
)