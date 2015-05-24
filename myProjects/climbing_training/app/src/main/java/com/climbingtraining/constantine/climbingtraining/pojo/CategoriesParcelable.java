package com.climbingtraining.constantine.climbingtraining.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KonstantinSysoev on 24.05.15.
 */
public class CategoriesParcelable implements Parcelable {

    private String imageNameAndPath;
    private String name;
    private String description;
    private String comment;
    private String entity;
    private int entityId;

    public CategoriesParcelable(String imageNameAndPath, String name, String description, String comment,
                                String entity, int entityId) {
        this.imageNameAndPath = imageNameAndPath;
        this.name = name;
        this.description = description;
        this.comment = comment;
        this.entity = entity;
        this.entityId = entityId;
    }

    public CategoriesParcelable(Parcel parcel) {
        imageNameAndPath = parcel.readString();
        name = parcel.readString();
        description = parcel.readString();
        comment = parcel.readString();
        entity = parcel.readString();
        entityId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageNameAndPath);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(comment);
        dest.writeString(entity);
        dest.writeInt(entityId);
    }

    public final static Parcelable.Creator<CategoriesParcelable> CREATOR = new Parcelable.Creator<CategoriesParcelable>() {

        @Override
        public CategoriesParcelable createFromParcel(Parcel source) {
            return new CategoriesParcelable(source);
        }

        @Override
        public CategoriesParcelable[] newArray(int size) {
            return new CategoriesParcelable[size];
        }
    };

//    GET & SET

    public String getImageNameAndPath() {
        return imageNameAndPath;
    }

    public void setImageNameAndPath(String imageNameAndPath) {
        this.imageNameAndPath = imageNameAndPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
