
export interface Course {
    id?: string;
    name: string;
    subTitle?: string;
    price?: number;
    level: Level;
    language: Language;
    urlCourseImages?: string;
    urlPromotionVideos?: string;
    authorName?: string;
    topic: Topic;
    isApproved?: boolean;
}

export interface Level {
    id: string
    name?: string
    description?: string
}

export interface Topic {
    id: string
    name?: string
    description?: string
}


export interface Language {
    id: string,
    name?: string
}