export interface ForumLectureDTO {
  id?: string;
  lectureId?: string;
  userId?: string;
  userName?: string;
  rawAvatar?: string;
  comment?: string;
  commentReplies?: CommentReply[];
  isUserLike?: boolean;
  isUserDisLike?: boolean;
  created?: number;
  updated?: number;
}

export interface ForumLecture {
  id?: string;
  lectureId?: string;
  userId?: string;
  userName?: string;
  rawAvatar?: string;
  comment?: string;
  commentReplies?: CommentReply[];
  countReplyComment?: number;
  likeAmount?: number;
  disLikeAmount?: number;
  userLikes?: string;
  userDislikes?: string;
}
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
