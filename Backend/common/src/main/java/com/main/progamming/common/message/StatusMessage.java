package com.main.progamming.common.message;

public interface StatusMessage {
    public static final String REQUEST_SUCCESS = "REQUEST SUCCESS";
    public static final String DATA_NOT_FOUND = "REQUEST SUCCESS! NOT FOUND ANY DATA";
    public static final String DATA_NOT_MAP = "REQUEST FAILURE! DATA NOT MAPPING";
    public static final String NOT_IMPLEMENTED = "REQUEST FAILURE! RESOURCE, FEATURE DOES NOT EXIST";
    public static final String DATA_CONFLICT = "REQUEST SUCCESS! DATA ALREADY EXISTS";
    public static final String NOT_PERMISSION = "ACCESS DENIED";
}
