package com.example.kin.decorate;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.kin.decorate.common.DecorateUtils;
import com.example.kin.decorate.common.Singleton;
import com.example.kin.decorate.models.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ImgGridActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_grid);

        Button b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                openGallery();

            }

        });

        final GridView gridview=(GridView) findViewById(R.id.gridview);

        Intent intent = getIntent();
        final List<String> urls = new ArrayList <String>();
        String url = new StringBuilder("http://123.57.248.228:8080/monitor/list_item_attachment/?id=")
                .append(intent.getStringExtra("iid")).toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String url = "http://123.57.248.228:8080/static/img/useritem/"+jsonObject.getJSONObject("fields").getString("filename");
                        urls.add(url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                gridview.setAdapter(new ImgAdapter(getApplicationContext(), android.R.layout.simple_list_item_activated_1,
                        android.R.id.text1, urls));
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                int i=0;
            }
        });

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_img_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void openGallery(){

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,"Select file to upload "), 0);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == this.RESULT_OK) {


            Uri uri = data.getData();
            try {
                String[] pojo = { MediaStore.Images.Media.DATA };

                Cursor cursor = managedQuery(uri, pojo, null, null, null);
                if (cursor != null) {
                    ContentResolver cr = this.getContentResolver();
                    int colunm_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(colunm_index);
                    /***
                     * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
                     * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
                     */
                    if (true || path.endsWith("jpg") || path.endsWith("png")) {
//
                        RetrieveFeedTask task = new RetrieveFeedTask(uri);
                        task.execute((Void)null);
                    }
                }

            } catch (Exception e) {
            }
        }

        super.onActivityResult(requestCode, resultCode, data);


    }

    public String getPath(Uri uri) {

        String[] proj = { MediaStore.Images.Media.DATA };

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(
                this,
                uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, Boolean> {


        private Uri uri=null;

        RetrieveFeedTask(Uri uri) {
            this.uri = uri;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpURLConnection connection = null;
                DataOutputStream outputStream = null;
                DataInputStream inputStream = null;
                String urlServer = "http://123.57.248.228:8080/monitor/upload_file/";
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary =  "*****";

                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1*1024*1024;

//                FileInputStream fileInputStream = new FileInputStream(new File(path[0]) );

                InputStream fileInputStream = getContentResolver().openInputStream(uri);

                URL url = new URL(urlServer);
                connection = (HttpURLConnection) url.openConnection();

                // Allow Inputs &amp; Outputs.
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                // Set HTTP method to POST.
                Intent intent = getIntent();
                connection.setRequestMethod("POST");
//                connection.setRequestProperty("iid", intent.getStringExtra("iid"));
//                connection.setRequestProperty("name", "test");

                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                outputStream = new DataOutputStream( connection.getOutputStream() );
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"iid\"" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(intent.getStringExtra("iid"));

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"name\"" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes("test");

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + "test" +"\"" + lineEnd);
                outputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // Read file
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                int serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                fileInputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }
    }
}
