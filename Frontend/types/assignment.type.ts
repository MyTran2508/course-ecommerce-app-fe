import { Lecture } from "./section.type";

export interface Assignment {
  id?: string;
  questions?: string;
  urlVideoInstructions?: string;
  textInstructions?: string;
  urlFileResource?: string;
  estimatedDuration?: number;
  urlVideoSolution?: string;
  urlFileSolution?: string;
  lecture?: Lecture;
}

export interface AssignmentHistory {
  id?: string;
  username?: string;
  textAnswer?: string;
  urlFileAnswer?: string;
  timeSubmit?: number;
  score?: number;
  evaluation?: string;
  assignment?: Assignment;
}
