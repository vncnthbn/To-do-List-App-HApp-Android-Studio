package com.example.h_app;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoModal implements Parcelable {
    private Boolean task_status;
    private String task;

    public TodoModal(Boolean task_status, String task) {
        this.task_status = task_status;
        this.task = task;
    }

    protected TodoModal(Parcel in) {
        byte tmpTask_status = in.readByte();
        task_status = tmpTask_status == 0 ? null : tmpTask_status == 1;
        task = in.readString();
    }

    public static final Creator<TodoModal> CREATOR = new Creator<TodoModal>() {
        @Override
        public TodoModal createFromParcel(Parcel in) {
            return new TodoModal(in);
        }

        @Override
        public TodoModal[] newArray(int size) {
            return new TodoModal[size];
        }
    };

    public Boolean getTask_status() {
        return task_status;
    }

    public void setTask_status(Boolean task_status) {
        this.task_status = task_status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (task_status == null ? 0 : task_status ? 1 : 2));
        dest.writeString(task);
    }
}
