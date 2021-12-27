package com.example.projectandroidcrudfirebase;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseRVModel implements Parcelable {
    String courseName ;
    String courseDesc ;
    String coursePrice ;
    String courseSuitedFor ;
    String courseImg ;
    String courseLink ;
    String courseID ;

    public CourseRVModel() {
    }

    public CourseRVModel(String courseName, String courseDesc, String coursePrice, String courseSuitedFor, String courseImg, String courseLink, String courseID) {
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.coursePrice = coursePrice;
        this.courseSuitedFor = courseSuitedFor;
        this.courseImg = courseImg;
        this.courseLink = courseLink;
        this.courseID = courseID;
    }

    protected CourseRVModel(Parcel in) {
        courseName = in.readString();
        courseDesc = in.readString();
        coursePrice = in.readString();
        courseSuitedFor = in.readString();
        courseImg = in.readString();
        courseLink = in.readString();
        courseID = in.readString();
    }

    public static final Creator<CourseRVModel> CREATOR = new Creator<CourseRVModel>() {
        @Override
        public CourseRVModel createFromParcel(Parcel in) {
            return new CourseRVModel(in);
        }

        @Override
        public CourseRVModel[] newArray(int size) {
            return new CourseRVModel[size];
        }
    };

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseSuitedFor() {
        return courseSuitedFor;
    }

    public void setCourseSuitedFor(String courseSuitedFor) {
        this.courseSuitedFor = courseSuitedFor;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseName);
        dest.writeString(courseDesc);
        dest.writeString(coursePrice);
        dest.writeString(courseSuitedFor);
        dest.writeString(courseImg);
        dest.writeString(courseLink);
        dest.writeString(courseID);
    }
}
