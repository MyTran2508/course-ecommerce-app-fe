import { Course } from "./course.type";

export default interface Content {
    id?: string,
    description: Description,
    course: Pick<Course, "id">
}

interface Description {
    id?: string,
    requirements: string,
    details: string,
    targetConsumers: string
}