export interface ForumLecture {
  id?: string;
  lectureId?: string;
  userId?: string;
  userName?: string;
  rawAvatar?: string;
  comment?: string;
  commentReplies?: CommentReply[];
  created?: number;
  updated?: number;
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
