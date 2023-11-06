export interface AuthorData {
  img: string;
  name: string;
  email: string;
  job: string[];
  online: boolean;
  date: string
}

export interface Member {
  img: string;
  name: string;
}

export interface ProjectData {
  img: string;
  name: string;
  members: Member[];
  budget: string;
  completion: number;
}