package com.example.dcl.onlineshopapp.Utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private static final int DEFAULT_BUFFER = 4096;
    private  UploadCallBack listener;
    private File file;

    public ProgressRequestBody(UploadCallBack listener, File file) {
        this.listener = listener;
        this.file = file;
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }





    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("image/*");
    }


    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength=file.length();
        byte[] buffer=new byte[DEFAULT_BUFFER];
        FileInputStream in=new FileInputStream(file);
        long uploaded=0;
        try {
            int read;
            Handler handler=new Handler(Looper.getMainLooper());
            while ((read=in.read(buffer))!=-1){
                uploaded+=read;
                handler.post(new ProgressUpdate(uploaded,fileLength));

                sink.write(buffer,0,read);

            }
        }finally {
            in.close();;
        }


    }

    private class ProgressUpdate implements Runnable {

        private  long uploadedfile,fileLength;
        public ProgressUpdate(long uploaded, long fileLength) {
            this.fileLength=fileLength;
            uploadedfile=uploaded;
            Log.d("BAL",uploaded+""+fileLength);
        }

        @Override
        public void run() {



            listener.onProgressUpdate((int) (100 * (uploadedfile/fileLength)));

        }
    }
}
