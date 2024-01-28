package com.programming.courseservice.utilities.constant;

public class CourseConstrant {
    public static final class ErrorConstrant {
        public static final String ID_NOT_FOUND = "ID doesn't exists in DB";

        public static final String NAME_NOT_FOUND = "Name doesn't exists in DB";

        public static final String CONTENT_NOT_COMPLETE = "Content not yet completed";

        public static final String CONTENT_NOT_FOUND = "Content not found";
    }

    public static final class SuccessConstrant {
        public static final String UPDATE_SUCCESS = "Update success";
    }

    public static final class S3Constrant {
        public static final String PATH_COURSE_LECTURE = "course/lecture/";
        public static final String PATH_COURSE_DOCUMENT = "course/document/";
        public static final String PATH_COURSE_IMAGE = "course/image/";
        public static final String PATH_COURSE_VIDEO = "course/video/";
    }
}
