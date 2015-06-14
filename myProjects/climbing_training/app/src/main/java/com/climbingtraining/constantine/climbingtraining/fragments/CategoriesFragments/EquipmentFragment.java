package com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by KonstantinSysoev on 24.05.15.
 */
public class EquipmentFragment extends AbstractCategoriesFragment {

    private static final String TAG = EquipmentFragment.class.getSimpleName();
    private static final int GALLERY_EQUIPMENT_REQUEST = 3;

    public static EquipmentFragment newInstance() {
        return new EquipmentFragment();
    }

    @Override
    public Equipment loadCategoryParcelable() {
        Log.d(TAG, "loadCategoryParcelable() start. Create new Equipment.");
        Equipment equipment = new Equipment();
        equipment.setId(getCategoryParcelable() != null ? getCategoryParcelable().getEntityId() : null);
        equipment.setName(getName().getText().toString());
        equipment.setDescription(getDescription().getText().toString());
        equipment.setComment(getComment().getText().toString());
        Log.d(TAG, "loadCategoryParcelable() done.");
        return equipment;
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
            case GALLERY_EQUIPMENT_REQUEST:
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
        return GALLERY_EQUIPMENT_REQUEST;
    }
}
