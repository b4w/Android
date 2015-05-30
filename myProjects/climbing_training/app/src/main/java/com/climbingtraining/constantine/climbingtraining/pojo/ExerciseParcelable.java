package com.climbingtraining.constantine.climbingtraining.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;

import java.util.List;

/**
 * Created by KonstantinSysoev on 26.05.15.
 */
public class ExerciseParcelable implements Parcelable {

    private String imageNameAndPath;
    private String name;
    private String description;
    private String comment;
    private int exerciseId;
    private int categoryId;
    private int equipmentId;
    private int typeExerciseId;
    private List<Category> categories;
    private List<Equipment> equipments;
    private List<TypeExercise> typeExercises;

    public ExerciseParcelable(String imageNameAndPath, String name, String description, String comment,
                              int exerciseId, int categoryId, int equipmentId, int typeExerciseId,
                              List<Category> categories, List<Equipment> equipments, List<TypeExercise> typeExercises) {
        this.imageNameAndPath = imageNameAndPath;
        this.name = name;
        this.description = description;
        this.comment = comment;
        this.categoryId = categoryId;
        this.equipmentId = equipmentId;
        this.typeExerciseId = typeExerciseId;
        this.exerciseId = exerciseId;
        this.categories = categories;
        this.equipments = equipments;
        this.typeExercises = typeExercises;
    }

    public ExerciseParcelable(List<Category> categories, List<Equipment> equipments, List<TypeExercise> typeExercises) {
        this.categories = categories;
        this.equipments = equipments;
        this.typeExercises = typeExercises;
    }

    public ExerciseParcelable(Parcel parcel) {
        imageNameAndPath = parcel.readString();
        name = parcel.readString();
        description = parcel.readString();
        comment = parcel.readString();
        exerciseId = parcel.readInt();
        categoryId = parcel.readInt();
        equipmentId = parcel.readInt();
        typeExerciseId = parcel.readInt();
        categories = parcel.readArrayList(Category.class.getClassLoader());
        equipments = parcel.readArrayList(Equipment.class.getClassLoader());
        typeExercises = parcel.readArrayList(TypeExercise.class.getClassLoader());
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
        dest.writeInt(exerciseId);
        dest.writeInt(categoryId);
        dest.writeInt(equipmentId);
        dest.writeInt(typeExerciseId);
//        TODO Разобраться почему здесь ошибка
//        dest.writeArray(categories);
//        dest.writeValue(equipments);
//        dest.writeValue(typeExercises);
    }

    public final static Parcelable.Creator<ExerciseParcelable> CREATOR = new Parcelable.Creator<ExerciseParcelable>() {

        @Override
        public ExerciseParcelable createFromParcel(Parcel source) {
            return new ExerciseParcelable(source);
        }

        @Override
        public ExerciseParcelable[] newArray(int size) {
            return new ExerciseParcelable[size];
        }
    };

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

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getTypeExerciseId() {
        return typeExerciseId;
    }

    public void setTypeExerciseId(int typeExerciseId) {
        this.typeExerciseId = typeExerciseId;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<TypeExercise> getTypeExercises() {
        return typeExercises;
    }

    public void setTypeExercises(List<TypeExercise> typeExercises) {
        this.typeExercises = typeExercises;
    }
}
