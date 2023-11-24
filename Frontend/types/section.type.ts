import Content from "./content.type";

export interface Section {
  id?: string;
  ordinalNumber?: number;
  name: string;
  lectures: Lecture[];
  content?: Pick<Content, "id">
}

export interface Lecture {

  id?: string;
  ordinalNumber?: number;
  name: string;
  fileName: string;
  url: string;
  videoDuration?: number | 0;
  isSuccess?: boolean;
}