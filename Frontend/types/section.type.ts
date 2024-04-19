import { QuizType } from "@/utils/resources";
import Content from "./content.type";

export interface Section {
  id?: string;
  ordinalNumber?: number;
  name?: string;
  lectures?: Lecture[];
  content?: Pick<Content, "id">;
  totalDurationVideoLectures?: number
}

export interface Lecture {
  id?: string;
  ordinalNumber?: number;
  name?: string;
  fileName?: string | null;
  url?: string | null;
  videoDuration?: number | 0;
  lectureType?: string;
  exQuiz?: ExQuiz;
  isSuccess?: boolean;
}
export interface ExQuiz { 
  id?: string;
  difficulty?: string;
  category?: string;
  limitTime?: number;
  questions?: Question[],
}
export interface Question { 
  id?: string;
  ordinalNumber?: number;
  title?: string;
  options?: string;
  rightAnswer?: string;
  answerExplanation?: string;
  quizType?: QuizType;
}
