package com.example.appdocsach;

import android.content.Context;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.InputStream;

public class TranslateService {

    private Translate translate;

    public TranslateService(Context context) {
        try {
            // Đọc file credentials.json từ tệp res/raw
            InputStream stream = context.getResources().openRawResource(R.raw.credentials);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);

            // Khởi tạo Translate service
            translate = TranslateOptions.newBuilder()
                    .setCredentials(credentials)
                    .build()
                    .getService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức dịch văn bản
    public String translateText(String originalText, String sourceLang, String targetLang) {
        try {
            Translation translation = translate.translate(
                    originalText,
                    Translate.TranslateOption.sourceLanguage(sourceLang),
                    Translate.TranslateOption.targetLanguage(targetLang));

            return translation.getTranslatedText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
