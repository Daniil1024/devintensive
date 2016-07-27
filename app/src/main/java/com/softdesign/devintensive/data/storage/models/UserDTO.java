package com.softdesign.devintensive.data.storage.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Parcelable {

    private String mPhoto;
    private String mFullName;
    private int mRating;
    private int mCodeLines;
    private int mProjects;
    private String mBio;

    private List<String> mRepositories;

    public UserDTO(User userData) {
        List<String> repoLink = new ArrayList<>();

        mPhoto = userData.getPhoto();
        mFullName = userData.getFullName();
        mRating = userData.getRating();
        mCodeLines = userData.getCodeLines();
        mProjects = userData.getProjects();
        mBio = userData.getBio();
        for (Repository gitLink : userData.getRepositories()) {
            repoLink.add(gitLink.getRepositoryName());
        }
        mRepositories = repoLink;
    }

    protected UserDTO(Parcel in) {
        mPhoto = in.readString();
        mFullName = in.readString();
        mRating = in.readInt();
        mCodeLines = in.readInt();
        mProjects = in.readInt();
        mBio = in.readString();
        if (in.readByte() == 0x01) {
            mRepositories = new ArrayList<String>();
            in.readList(mRepositories, String.class.getClassLoader());
        } else {
            mRepositories = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPhoto);
        dest.writeString(mFullName);
        dest.writeInt(mRating);
        dest.writeInt(mCodeLines);
        dest.writeInt(mProjects);
        dest.writeString(mBio);
        if (mRepositories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mRepositories);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserDTO> CREATOR = new Parcelable.Creator<UserDTO>() {
        @Override
        public UserDTO createFromParcel(Parcel in) {
            return new UserDTO(in);
        }

        @Override
        public UserDTO[] newArray(int size) {
            return new UserDTO[size];
        }
    };

    public String getPhoto() {
        return mPhoto;
    }

    public String getFullName() {
        return mFullName;
    }

    public int getRating() {
        return mRating;
    }

    public int getProjects() {
        return mProjects;
    }

    public int getCodeLines() {
        return mCodeLines;
    }

    public String getBio() {
        return mBio;
    }

    public List<String> getRepositories() {
        return mRepositories;
    }
}