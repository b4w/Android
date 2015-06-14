package com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.activity.CategoriesActivity;
import com.climbingtraining.constantine.climbingtraining.data.dto.AbstractEntity;
import com.climbingtraining.constantine.climbingtraining.pojo.CategoriesParcelable;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by KonstantinSysoev on 24.05.15.
 */
public abstract class AbstractCategoriesFragment extends Fragment {

    private final static String TAG = CategoryFragment.class.getSimpleName();
    private static final int GALLERY_REQUEST = 1;

    private ImageView image;
    private TextView name;
    private TextView description;
    private TextView comment;
    private Button saveButton;
    private Button cancelButton;

    private CategoriesParcelable categoryParcelable;
    private IAbstractFragmentCallBack callBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (IAbstractFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IAbstractFragmentCallBack.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_entity, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        image = (ImageView) getActivity().findViewById(R.id.fragment_categories_entity_image);
        name = (TextView) getActivity().findViewById(R.id.fragment_categories_entity_name_edit_text);
        description = (TextView) getActivity().findViewById(R.id.fragment_categories_entity_description_edit_text);
        comment = (TextView) getActivity().findViewById(R.id.fragment_categories_entity_comment_edit_text);
        saveButton = (Button) getActivity().findViewById(R.id.fragment_categories_entity_save_btn);
        cancelButton = (Button) getActivity().findViewById(R.id.fragment_categories_entity_cancel_btn);

        initializeFields();
        loadListeners();
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
    }

    protected abstract AbstractEntity loadCategoryParcelable();
    protected abstract int getGalleryRequest();

    private void loadListeners() {
        Log.d(TAG, "loadListeners() start");
        /**
         * Сохраняем сущность.
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.saveEntity(loadCategoryParcelable(), image.getDrawable());
            }
        });

        /**
         * Отмена сохранения сущности.
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.cancel();
            }
        });

        /**
         * Загружаем изображение из галереи телефона.
         */
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, getGalleryRequest());
            }
        });
        Log.d(TAG, "loadListeners() done");
    }

    /**
     * Инициализация полей, если редактируем сущность.
     */
    private void initializeFields() {
        Log.d(TAG, "initializeFields() start");
        categoryParcelable = getActivity().getIntent().getParcelableExtra(CategoriesActivity.CATEGORIES_PARCELABLE);
        // Редактируем сущность.
        if (categoryParcelable != null) {
            if (categoryParcelable.getImageNameAndPath() != null && !categoryParcelable.getImageNameAndPath().isEmpty()) {
                File file = new File(categoryParcelable.getImageNameAndPath());
                Picasso.with(getActivity()).load(file).into(image);
            }
            name.setText(categoryParcelable.getName());
            description.setText(categoryParcelable.getDescription());
            comment.setText(categoryParcelable.getComment());
        }
        Log.d(TAG, "initializeFields() done");
    }

//    GET & SET

    public CategoriesParcelable getCategoryParcelable() {
        return categoryParcelable;
    }

    public TextView getName() {
        return name;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getComment() {
        return comment;
    }

    public ImageView getImage() {
        return image;
    }

    /**
     * Интерфейс для передачи данных в Activity.
     */
    public interface IAbstractFragmentCallBack {
        // Передаем drawable, что бы после успешного сохранения сущности в БД, сохранять изображение.
        void saveEntity(AbstractEntity abstractEntity, Drawable drawable);

        void cancel();
    }
}
