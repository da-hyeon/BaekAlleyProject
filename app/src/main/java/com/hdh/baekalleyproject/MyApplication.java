package com.hdh.baekalleyproject;

import android.app.Activity;
import android.app.Application;

import com.hdh.baekalleyproject.adapter.KakaoSDKAdapter;
import com.hdh.baekalleyproject.data.util.ServerAPI;
import com.kakao.auth.KakaoSDK;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.realm.Realm;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static MyApplication appInstance;


    //타임아웃
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 15;
    private static ServerAPI Interface;

    private Activity mActivity;

    private static final String BASE_URL = "http://dutchkor.cafe24.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
        Realm.init(this);
    }

    /**
     * MyApplication Singleton
     */
    public static MyApplication getInstance() {
        if (appInstance == null) {
            appInstance = new MyApplication();
        }
        return appInstance;
    }

    /**
     * getActivity
     */
    public Activity getActivity() {
        return mActivity;
    }

    /**
     * setActivity
     */
    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * ServerAPI Adapter
     */
    public synchronized static ServerAPI getRestAdapter() {
        if (Interface == null) {
            //통신로그를 확인하기 위한 부분
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //쿠키 메니저의 cookie policy를 변경 합니다.
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            //OkHttpClient를 생성합니다.
            //인증서 무시
            //연결 타임아웃 시간 설정
            //쓰기 타임아웃 시간 설정
            //읽기 타임아웃 시간 설정
            //쿠키메니져 설정
            //http 로그 확인
            OkHttpClient client = configureClient(new OkHttpClient().newBuilder()) //인증서 무시
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                    .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                    .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                    .build();

            //Retrofit 설정
            Interface = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ServerAPI.class); //인터페이스 연결
        }
        return Interface;
    }

    /**
     * UnCertificated 허용
     */
    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder) {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }
        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };

            builder.sslSocketFactory(ctx.getSocketFactory()).hostnameVerifier(hostnameVerifier);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return builder;
    }


    public static String getBaseUrl() {
        return BASE_URL;
    }
}
