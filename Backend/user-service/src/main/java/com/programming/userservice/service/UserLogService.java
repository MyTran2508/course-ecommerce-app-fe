package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.programming.userservice.domain.persistent.entity.UserLog;
import com.programming.userservice.repository.UserLogRepository;
import com.programming.userservice.utilities.annotation.ExcludeFromComparisonField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserLogService {

    private final UserLogRepository userLogRepository;

    public DataResponse<String> addLog(UserLog userLog) {
        userLogRepository.save(userLog);
        return ResponseMapper.toDataResponseSuccess("Add log success");
    }

    public ListResponse<List<UserLog>> filterUserLog(SearchKeywordDto searchKeywordDto) {
        // Get data from searchKeywordDto
        Long startTime = searchKeywordDto.getKeyword().get(0) == null ? null
                : Long.valueOf(searchKeywordDto.getKeyword().get(0).trim());
        Long endTime = searchKeywordDto.getKeyword().get(1) == null ? null
                : Long.valueOf(searchKeywordDto.getKeyword().get(1).trim());
        String userName = searchKeywordDto.getKeyword().get(2) == null ? null
                : searchKeywordDto.getKeyword().get(2).trim();
        String ip = searchKeywordDto.getKeyword().get(3) == null ? null
                : searchKeywordDto.getKeyword().get(3).trim();

        // Check error
        if(startTime != null && (endTime == null || startTime > endTime)) {
            return ResponseMapper.toListResponse(null, 0, 0, StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
        }

        // Sort by created and paging
        Sort sortBy = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(searchKeywordDto.getPageIndex(), searchKeywordDto.getPageSize(), sortBy);

        // Return response
        return ResponseMapper.toPagingResponseSuccess(userLogRepository.filterUserLog(startTime, endTime, userName, ip, pageable));
    }

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

    public String writeLoginLog() {
        return "- Đăng nhập thành công";
    }

    public String writeSignUpLog() {
        return "- Đăng ký tài khoản thành công";
    }

    public String writeForgetPassword(boolean isSuccess) {
        return isSuccess ? "- Lấy mật khẩu thành công" : "- Lấy mật khẩu thất bại";
    }

    public String writeResetPassword(boolean isSuccess) {
        return isSuccess ? "- Đặt lại mật khẩu thành công" : "- Đặt lại mật khẩu thất bại";
    }

    public String writeRemoveLog(boolean isSuccess) {
        return isSuccess ? "- Xóa tạm thời thành công" : "- Xóa tạm thời thất bại";
    }

    public String writeDeleteLog(boolean isSuccess) {
        return isSuccess ? "- Xóa vĩnh viễn thành công" : "- Xóa vĩnh viễn thất bại";
    }
}
