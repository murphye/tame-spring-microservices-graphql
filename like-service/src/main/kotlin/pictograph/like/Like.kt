package pictograph.like

data class Like(
    var likeId: String,
    var userId: String,
    var likeComment: String,
    var likeCount: Int
)