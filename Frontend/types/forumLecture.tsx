export interface ForumLecture {
  id?: string;
  lectureId?: string;
  userId?: string;
  userName?: string;
  avatarUrl?: string;
  comment?: string;
  commentReplies?: CommentReply[];
  created?: number;
  updated?: number;
}
export interface CommentReply {
  id?: string;
  userId?: string;
  userName?: string;
  avatarUrl?: string;
  comment?: string;
  created?: number;
  updated?: number;
}
