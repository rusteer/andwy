package net.andwy.andwyadmin.service;
public class ServiceError {
    public String message;
    public String detail;
    public ServiceError(String key, String value) {
        message = key;
        detail = value;
    }
}
