export interface ForumLecture {
  id?: string;
  lectureId?: string;
  userId?: string;
  userName?: string;
  rawAvatar?: string;
  comment?: string;
  commentReplies?: CommentReply[];
  isUserLike?: boolean;
  isUserDislike?: boolean;
  created?: number;
  updated?: number;
  likeAmount?: number;
  disLikeAmount?: number;
}

// export interface ForumLecture {
//   id?: string;
//   lectureId?: string;
//   userId?: string;
//   userName?: string;
//   rawAvatar?: string;
//   comment?: string;
//   commentReplies?: CommentReply[];
//   countReplyComment?: number;
//   likeAmount?: number;
//   disLikeAmount?: number;
//   userLikes?: string;
//   userDislikes?: string;
//   created?: number;
//   updated?: number;
// }
export interface CommentReply {
  id?: string;
  userId?: string;
  userName?: string;
  rawAvatar?: string;
  forumLectureId?: string;
  comment?: string;
  created?: number;
  updated?: number;
}
