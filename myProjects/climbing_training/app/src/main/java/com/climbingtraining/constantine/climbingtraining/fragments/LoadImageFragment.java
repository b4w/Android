package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.activity.CategoriesActivity;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class LoadImageFragment extends Fragment {

    private final static String TAG = LoadImageFragment.class.getSimpleName();

    private final static String YANDEX_KEY = "trnsl.1.1.20150506T175356Z.05fef87a6c475b43.35fb6048325da5232b2bb06666e84fb031b1ed3b";
    private final static String GETTY_KEY = "qzprvp5bu8b2874jxbcqwmd9";
    private final static String GETTY_SECRET = "WKGqpPEKmfVcwWUqQ3pgSpMCTygTaV7TQpxPg5JTgpv38";
    private final static String GETTY_CLIENT_CREDENTIALS = "client_credentials";

    private final static String DIR_SD = "/Climbing_training/images/category/";
    static final int GALLERY_REQUEST = 1;

    private ImageView fragmentLoadImageImage;
    private String imageNameAndPath;

    public static LoadImageFragment newInstance() {
        LoadImageFragment fragment = new LoadImageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load_image, container, false);
        if (getArguments() != null) {
            imageNameAndPath = getArguments().getString(CategoriesActivity.IMAGE_NAME_AND_PATH);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentLoadImageImage = (ImageView) getActivity().findViewById(R.id.fragment_load_image_image);

        if (imageNameAndPath != null && !imageNameAndPath.isEmpty()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imageNameAndPath);
            fragmentLoadImageImage.setImageBitmap(myBitmap);
        }

        fragmentLoadImageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery();
//                    Toast.makeText(getActivity(), "Поле для поиска пустое", Toast.LENGTH_SHORT).show();
//                    TranslateYandex translateYandex = new TranslateYandex();
//                    translateYandex.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                    loadImageFromInternet();
            }
        });
    }

    public void loadImageFromInternet() {

        Callback callback = new Callback() {
            @Override
            public void onSuccess() {
                writeFileSD();
            }

            @Override
            public void onError() {
                Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }

        };

        Picasso.with(getActivity().getApplicationContext()) //передаем контекст приложения
                .load("http://i.imgur.com/DvpvklR.png") //адрес изображения
//                .placeholder(R.drawable.logo) // картинка - заглушка
//                .resize(50, 50) // изменение размера
                .fit()
                .into(fragmentLoadImageImage, callback); //ссылка на ImageView
    }

    public void loadImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    /**
     * Выбираем изображение из галереи телефона и сохраняем в наш ImageView.
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
            case GALLERY_REQUEST:
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
                    fragmentLoadImageImage.setImageBitmap(galleryPic);
                }
        }

    }

    public void writeFileSD() {

        BitmapDrawable btmpDr = (BitmapDrawable) fragmentLoadImageImage.getDrawable();
        Bitmap bmp = btmpDr.getBitmap();

        // проверяем доступность cd
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // название файла
        String imageNameForSDCard = "category_image.jpg";
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File imageToSd = new File(sdPath, imageNameForSDCard);
        try {
            FileOutputStream fos = new FileOutputStream(imageToSd);
            // 100 - 100% качество
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            // пишем данные
            fos.flush();
            // закрываем поток
            fos.close();
            Log.d(TAG, "Файл записан на SD: " + imageToSd.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public class TranslateYandex extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            RestAdapter translateAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://translate.yandex.net/api/v1.5/tr.json")
                    .build();

            Map<String, String> map = new HashMap<>();
            map.put("lang", "en");
//            TODO
//            map.put("text", searchString);
            map.put("key", YANDEX_KEY);

            ITranslateText iTranslateText = translateAdapter.create(ITranslateText.class);
            TranslateText translateText = iTranslateText.getTranslatedText(map);
            String result = translateText.text.get(0);

//            ----------------------------------------

//            1. регистрация (получеие токина) для последующего скачивания фото

            Map<String, String> mapToken = new HashMap<>();

            mapToken.put("client_secret", GETTY_SECRET);
            mapToken.put("client_id", GETTY_KEY);
            mapToken.put("grant_type", GETTY_CLIENT_CREDENTIALS);

            RestAdapter imageAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.gettyimages.com/oauth2/token")
                    .build();

            IRestGetty iRestGetty = imageAdapter.create(IRestGetty.class);
            RegisterOAuth2 oAuth2 = iRestGetty.getToken(mapToken);


            return translateText.text.get(0);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    //    GET & SET
    public ImageView getFragmentLoadImageImage() {
        return fragmentLoadImageImage;
    }

    private interface IRestGetty {
        @POST("/")
        public RegisterOAuth2 getToken(@QueryMap Map<String, String> parameters);
    }

    private class RegisterOAuth2 {
        @SerializedName("access_token")
        private String access_token;
        @SerializedName("token_type")
        private String token_type;
        @SerializedName("expires_in")
        private String expires_in;

    }

    private interface ITranslateText {
        @POST("/translate")
        public TranslateText getTranslatedText(@QueryMap Map<String, String> parameters);
    }

    private class TranslateText {
        @SerializedName("code")
        private int code;
        @SerializedName("lang")
        private String lang;
        @SerializedName("text")
        private List<String> text;
    }
}
