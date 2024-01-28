package com.programming.courseservice.utilities.constant;

public class CourseConstrant {
    public static final class ErrorConstrant {
        public static final String ID_NOT_FOUND = "ID doesn't exists in DB";

        public static final String NAME_NOT_FOUND = "Name doesn't exists in DB";

        public static final String CONTENT_NOT_COMPLETE = "Content not yet completed";

        public static final String CONTENT_NOT_FOUND = "Content not found";

        public static final String SAVE_USER_ANSWER_FAIL = "Error!! Save user answer fail";

        public static final String TIME_OUT = "Quiz time is over";

        public static final String QUIZ_HAS_ENDED = "Quiz has ended";
    }

    public static final class SuccessConstrant {
        public static final String UPDATE_SUCCESS = "Update successfully";

        public static final String UPSERT_SUCCESS = "Upsert successfully";
    }

    public static final class S3Constrant {
        public static final String PATH_COURSE_LECTURE = "course/lecture/";
        public static final String PATH_COURSE_DOCUMENT = "course/document/";
        public static final String PATH_COURSE_IMAGE = "course/image/";
        public static final String PATH_COURSE_VIDEO = "course/video/";
    }
}
