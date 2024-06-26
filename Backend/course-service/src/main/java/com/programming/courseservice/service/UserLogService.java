package com.programming.courseservice.service;

import com.programming.courseservice.utilities.annotation.ExcludeFromComparisonField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserLogService {

    public String writeUpdateLog(Class<?> clazz, Object oldObject, Object newObject, boolean isPrimary, int merginCount) {
        // init log
        StringBuilder result = new StringBuilder();
        if(isPrimary) {
            result.append("Mô tả chi tiết: \\n");
            result.append("- ");
        }
        try {
            for (Field field: clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object newFieldValue = field.get(newObject);
                Object oldFieldValue = field.get(oldObject);
                if(!field.isAnnotationPresent(ExcludeFromComparisonField.class)) {
                    if(List.class.isAssignableFrom(field.getType())) {
                        merginCount += 2;
                        if(newFieldValue != null) {
                            int index = 0;
                            for(Object nObject: (List<?>) newFieldValue) {
                                Object oObject = ((List<?>) oldFieldValue).get(index);
                                result.append(field.getName().trim()).append(": <\\n");
                                result.append("\\ml-").append(merginCount)
                                        .append(writeUpdateLog(nObject.getClass(), oObject, nObject, false, merginCount));
                                result.append("\\eml").append("\\n>; ");
                                index++;
                            }
                        }
                        merginCount -= 2;
                    }
                } else {
                    if (!Objects.equals(newFieldValue, oldFieldValue)) {
                        result.append(field.getName().trim()).append(": từ <")
                                .append(newFieldValue == null ? "N/A" : newFieldValue).append("> thành <")
                                .append(oldFieldValue == null ? "N/A" : oldFieldValue).append(">; ");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result.toString();
    }

    public String writePersistLog(Class<?> clazz, Object newObject, boolean isPrimary, int merginCount) {
        // init log
        StringBuilder result = new StringBuilder();
        if(isPrimary) {
            result.append("Mô tả chi tiết: \\n");
            result.append("- ");
        }

        try {
            for(Field field: clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object newFieldValue = field.get(newObject);

                if(!field.isAnnotationPresent(ExcludeFromComparisonField.class)) {
                    if(List.class.isAssignableFrom(field.getType())) {
                        merginCount += 2;
                        if(newFieldValue != null) {
                            for(Object object: (List<?>) newFieldValue) {
                                result.append(field.getName().trim()).append(": <\\n");
                                result.append("\\ml-").append(merginCount)
                                        .append(writePersistLog(object.getClass(), object, false, merginCount));
                                result.append("\\eml").append("\\n>; ");
                            }
                        }
                        merginCount -= 2;
                    } else {
                        result.append(field.getName().trim()).append(": <")
                                .append(newFieldValue == null ? "N/A" : newFieldValue).append(">; ");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result.toString();
    }

    public String writeRemoveLog(boolean isSuccess) {
        return isSuccess ? "- Xóa tạm thời thành công" : "- Xóa tạm thời thất bại";
    }

    public String writeDeleteLog(boolean isSuccess) {
        return isSuccess ? "- Xóa vĩnh viễn thành công" : "- Xóa vĩnh viễn thất bại";
    }
}
