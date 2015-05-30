package com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by KonstantinSysoev on 24.05.15.
 */
public class TypeExerciseFragment extends AbstractCategoriesFragment {

    private static final String TAG = TypeExerciseFragment.class.getSimpleName();
    private static final int GALLERY_TYPE_EXERCISE_REQUEST = 4;

    public static TypeExerciseFragment newInstance() {
        return new TypeExerciseFragment();
    }

    @Override
    public TypeExercise loadCategoryParcelable() {
        Log.d(TAG, "loadCategoryParcelable() start. Create new TypeExercise.");
        TypeExercise typeExercise = new TypeExercise();
        typeExercise.setId(getCategoryParcelable() != null ? getCategoryParcelable().getEntityId() : null);
        typeExercise.setName(getName().getText().toString());
        typeExercise.setDescription(getDescription().getText().toString());
        typeExercise.setComment(getComment().getText().toString());
        Log.d(TAG, "loadCategoryParcelable() done.");
        return typeExercise;
    }

    /**
     * Выбираем изображение из галереи телефона и сохраняем в imageView.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap galleryPic = null;

        switch (requestCode) {
            case GALLERY_TYPE_EXERCISE_REQUEST:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        galleryPic = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    getImage().setImageBitmap(galleryPic);
                }
        }
    }

    @Override
    protected int getGalleryRequest() {
        return GALLERY_TYPE_EXERCISE_REQUEST;
    }
}
