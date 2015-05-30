package com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.climbingtraining.constantine.climbingtraining.data.dto.Category;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by KonstantinSysoev on 24.05.15.
 */
public class CategoryFragment extends AbstractCategoriesFragment {

    private final static String TAG = CategoryFragment.class.getSimpleName();
    private static final int GALLERY_CATEGORY_REQUEST = 2;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public Category loadCategoryParcelable() {
        Log.d(TAG, "loadCategoryParcelable() start. Create new Category.");
        Category category = new Category();
        category.setId(getCategoryParcelable() != null ? getCategoryParcelable().getEntityId() : null);
        category.setName(getName().getText().toString());
        category.setDescription(getDescription().getText().toString());
        category.setComment(getComment().getText().toString());
        Log.d(TAG, "loadCategoryParcelable() done.");
        return category;
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
            case GALLERY_CATEGORY_REQUEST:
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
        return GALLERY_CATEGORY_REQUEST;
    }
}
