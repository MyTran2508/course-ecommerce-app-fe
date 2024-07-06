package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.util.ExcludeFromComparisonField;
import com.programming.userservice.domain.persistent.entity.UserLog;
import com.programming.userservice.repository.UserLogRepository;
import com.programming.userservice.utilities.constant.UserConstant;
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
        String userName = searchKeywordDto.getKeyword().get(0) == null ? null
                : searchKeywordDto.getKeyword().get(0).trim();
        Long startTime = searchKeywordDto.getKeyword().get(1) == null ? null
                : Long.valueOf(searchKeywordDto.getKeyword().get(1).trim());
        Long endTime = searchKeywordDto.getKeyword().get(2) == null ? null
                : Long.valueOf(searchKeywordDto.getKeyword().get(2).trim());


        // Check error
        if(startTime != null && (endTime == null || startTime > endTime)) {
            return ResponseMapper.toListResponse(null, 0, 0, StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
        }

        // Sort by created and paging
        Sort sortBy = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(searchKeywordDto.getPageIndex(), searchKeywordDto.getPageSize(), sortBy);

        // Return response
        return ResponseMapper.toPagingResponseSuccess(userLogRepository.filterUserLog(startTime, endTime, userName, pageable));
    }

    public String writeUpdateLog(Class<?> clazz, Object oldObject, Object newObject, boolean isPrimary, int merginCount) {
        // init log
        StringBuilder result = new StringBuilder();
        if(isPrimary) {
            result.append(UserConstant.PREFIX_USER_LOG);
        }
        try {
            for (Field field: clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object oldFieldValue = field.get(oldObject);
                Object newFieldValue = field.get(newObject);
                if(!field.isAnnotationPresent(ExcludeFromComparisonField.class)) {
                    if(List.class.isAssignableFrom(field.getType())) {
                        System.out.println(newFieldValue.toString());
                        System.out.println(field.getName());
                        if(newFieldValue != null) {
                            List<?> newValueList = (List<?>) newFieldValue;
                            List<?> oldValueList = (List<?>) oldFieldValue;
                            result.append(field.getName().trim()).append(": [");
                            if (newValueList.size() == oldValueList.size()) {
                                int index = 0;
                                boolean checkLog = false;
                                for(Object nObject: newValueList) {
                                    Object oObject = oldValueList.get(index);
                                    StringBuilder logList = new StringBuilder();
                                    logList.append("{")
                                            .append(writeUpdateLog(nObject.getClass(), oObject, nObject, false, merginCount));
                                    if (logList.length() > 2) {
                                        logList.delete(logList.length() - 2, logList.length());
                                    }
                                    logList.append("}; ");
                                    if (!Objects.equals(logList.toString(),"{}; ")) {
                                        result.append(logList);
                                        checkLog = true;
                                    }
                                    index++;
                                }
                                if (result.length() > 2 && checkLog) {
                                    result.delete(result.length() - 2, result.length());
                                }
                                result.append("]; ");
                                int indexChar = result.lastIndexOf(field.getName().trim() + ": [];");
                                if (indexChar != 1) {
                                    result.delete(indexChar, indexChar + result.length());
                                }
                            } else if (newValueList.size() < oldValueList.size()) {
                                System.out.println("new size < old size: " + ((List<?>) newFieldValue).size() + " < " + ((List<?>) oldFieldValue).size());
                                int index = 0;
                                boolean checkLog = false;
                                for(Object nObject: newValueList) {
                                    Object oObject = oldValueList.get(index);
                                    StringBuilder logList = new StringBuilder();
                                    logList.append("{")
                                            .append(writeUpdateLog(nObject.getClass(), oObject, nObject, false, merginCount));
                                    if (logList.length() > 2) {
                                        logList.delete(logList.length() - 2, logList.length());
                                    }
                                    logList.append("}; ");
                                    if (!Objects.equals(logList.toString(),"{}; ")) {
                                        result.append(logList);
                                        checkLog = true;
                                    }
                                    index++;
                                }
                                for (int i = index; i < oldValueList.size(); i++) {
                                    StringBuilder logList = new StringBuilder();
                                    logList.append("[từ {");
                                    Object oObject = oldValueList.get(index);
                                    System.out.println("new: " + oObject.toString());
                                    for (Field fieldChild: oObject.getClass().getDeclaredFields()) {
                                        System.out.println("new field: " + fieldChild.getName());
                                        if (!fieldChild.isAnnotationPresent(ExcludeFromComparisonField.class)) {
                                            fieldChild.setAccessible(true);
                                            Object newChildValue = fieldChild.get(oObject);
                                            System.out.println(newChildValue);
                                            logList.append(fieldChild.getName().trim()).append(": <")
                                                    .append(newChildValue == null ? "N/A" : newChildValue).append(">; ");
                                        }
                                    }
                                    logList.delete(logList.length() - 2, logList.length());
                                    logList.append("} thành N/A]; ");
                                    result.append(logList);
                                }
                                result.delete(result.length() - 2, result.length());
                                result.append("]; ");
                                int indexChar = result.lastIndexOf(field.getName().trim() + ": [];");
                                if (indexChar != 1) {
                                    result.delete(indexChar, indexChar + result.length());
                                }
                            } else if (newValueList.size() > oldValueList.size()) {
                                System.out.println("new size > old size: " + ((List<?>) newFieldValue).size() + " > " + ((List<?>) oldFieldValue).size());
                                int index = 0;
                                boolean checkLog = false;
                                for(Object oObject: oldValueList) {
                                    Object nObject = newValueList.get(index);
                                    StringBuilder logList = new StringBuilder();
                                    logList.append("{")
                                            .append(writeUpdateLog(nObject.getClass(), oObject, nObject, false, merginCount));
                                    if (logList.length() > 2) {
                                        logList.delete(logList.length() - 2, logList.length());
                                    }
                                    logList.append("}; ");
                                    if (!Objects.equals(logList.toString(),"{}; ")) {
                                        result.append(logList);
                                        checkLog = true;
                                    }
                                    index++;
                                }
                                for (int i = index; i < newValueList.size(); i++) {
                                    StringBuilder logList = new StringBuilder();
                                    logList.append("[từ <N/A> thành {");
                                    Object nObject = newValueList.get(index);
                                    System.out.println("new: " + nObject.toString());
                                    for (Field fieldChild: nObject.getClass().getDeclaredFields()) {
                                        System.out.println("new field: " + fieldChild.getName());
                                        if (!fieldChild.isAnnotationPresent(ExcludeFromComparisonField.class)) {
                                            fieldChild.setAccessible(true);
                                            Object newChildValue = fieldChild.get(nObject);
                                            System.out.println(newChildValue);
                                            logList.append(fieldChild.getName().trim()).append(": <")
                                                    .append(newChildValue == null ? "N/A" : newChildValue).append(">; ");
                                        }
                                    }
                                    logList.delete(logList.length() - 2, logList.length());
                                    logList.append("}]; ");
                                    result.append(logList);
                                }
                                result.delete(result.length() - 2, result.length());
                                result.append("]; ");
                                int indexChar = result.lastIndexOf(field.getName().trim() + ": [];");
                                if (indexChar != 1) {
                                    result.delete(indexChar, indexChar + result.length());
                                }
                            }
                        }
                    }
                    else {
                        System.out.println(field.getName());
                        System.out.println(newFieldValue);
                        System.out.println(oldFieldValue);
                        if (!Objects.equals(newFieldValue, oldFieldValue)) {

                            result.append(field.getName().trim()).append(": từ <")
                                    .append(oldFieldValue == null ? "N/A" : oldFieldValue).append("> thành <")
                                    .append(newFieldValue == null ? "N/A" : newFieldValue).append(">; ");
                        }
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
            result.append(UserConstant.PREFIX_USER_LOG);
        }

        try {
            for(Field field: clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object newFieldValue = field.get(newObject);

                if(!field.isAnnotationPresent(ExcludeFromComparisonField.class)) {
                    if(List.class.isAssignableFrom(field.getType())) {
                        if(newFieldValue != null) {
                            result.append(field.getName().trim()).append(": [");
                            for(Object object: (List<?>) newFieldValue) {
                                result.append("{")
                                        .append(writePersistLog(object.getClass(), object, false, merginCount));
                                if (result.length() > 2) {
                                    result.delete(result.length() - 2, result.length());
                                }
                                result.append("}; ");
                            }
                            if (result.length() > 3) {
                                result.delete(result.length() - 3, result.length());
                            }
                            result.append("]; ");
                        }
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
