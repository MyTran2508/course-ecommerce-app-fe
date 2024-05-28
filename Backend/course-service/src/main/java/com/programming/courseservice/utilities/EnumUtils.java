package com.programming.courseservice.utilities;

import com.programming.courseservice.domain.persistent.enumrate.RatingsLevel;
import com.programming.courseservice.domain.persistent.enumrate.VideoDuration;

public class EnumUtils {

    public static Float getMinRating(RatingsLevel rating) {
        if (rating == null) {
            return null;
        }
        switch (rating) {
            case RATINGS_4_5_TO_UP:
                return 4.5F;
            case RATINGS_4_TO_UP:
                return 4.0F;
            case RATINGS_3_5_TO_UP:
                return 3.5F;
            case RATINGS_3_TO_UP:
                return 3.0F;
            default:
                return null;
        }
    }

    public static Long getMinVideoDurationHour(VideoDuration videoDuration) {
        switch (videoDuration) {
            case VIDDUR_0_TO_1:
                return 0L;
            case VIDDUR_1_TO_3:
                return 1L;
            case VIDDUR_3_TO_6:
                return 3L;
            case VIDDUR_6_TO_17:
                return 6L;
            case VIDDUR_17_TO_MAX:
                return 17L;
            default:
                throw new IllegalArgumentException("Invalid video duration");
        }
    }

    public static Long getMaxVideoDurationHour(VideoDuration videoDuration) {
        switch (videoDuration) {
            case VIDDUR_0_TO_1:
                return 1L;
            case VIDDUR_1_TO_3:
                return 3L;
            case VIDDUR_3_TO_6:
                return 6L;
            case VIDDUR_6_TO_17:
                return 17L;
            case VIDDUR_17_TO_MAX:
                return Long.MAX_VALUE;
            default:
                throw new IllegalArgumentException("Invalid video duration");
        }
    }
}
