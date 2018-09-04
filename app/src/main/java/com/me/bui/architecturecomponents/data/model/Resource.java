package com.me.bui.architecturecomponents.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

import static com.me.bui.architecturecomponents.data.model.Status.ERROR;
import static com.me.bui.architecturecomponents.data.model.Status.LOADING;
import static com.me.bui.architecturecomponents.data.model.Status.SUCCESS;

/**
 * Created by mao.bui on 9/3/2018.
 */
public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;
    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }
    public static <T> Resource<T> error(@Nullable T data, String msg) {
        return new Resource<>(ERROR, data, msg);
    }
    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource<?> resource = (Resource<?>) o;
        return Objects.equals(status, resource.status) &&
                Objects.equals(message, resource.message) &&
                Objects.equals(data, resource.data);
    }
    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
