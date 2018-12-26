package com.example.dcl.onlineshopserver.FCM_Client;

import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FcmServer  {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA-EPv4MI:APA91bGN14JG1obh_Pb7gHkWxxvHSvpRzSI3G-adHQQQTypM72JIyg-ZonNh6_vS123TiuAxZC_rES9mjIu-ZGm7jntryC8xEQ_ULCMxzd6fJmIbSd3p3736PfGchl6sbDAtPQvsVJy4"
            }

    )
}
