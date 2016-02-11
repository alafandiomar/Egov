package com.egov.egov.AsyncTaskGeneric;

import android.net.Uri;
import android.util.Base64;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tonyhaddad on 6/17/15.
 */
public class ServiceCall {

    public static String BaseUrl = "http://192.168.1.109:81/Internal_Affairs_API/index.php/";
    public static String imageUrl = "http://192.168.1.109:81/Internal_Affairs_API/application/images";
    static String username = "d892ebe8-b991-4408-afa7-48a78d0324ae";
    static String host = "services.adta.ae";
    static String password = "#vad!321";
    static String urlBasePath = "http://" + username + ".adta.ae/api/";
    static String MY_APP_TAG = "com.visitabudhabi.android";
    static Object content;
    static String strResponse;

    public String getXmlString(String url) {

        String authString = username + ":" + password;
        byte[] authEncBytes = Base64.encode(authString.getBytes(), Base64.DEFAULT);
        String authStringEnc = new String(authEncBytes);
        String result = null;
        URL Url = null;
        HttpURLConnection urlConnection = null;
        try {
            Url = new URL(BaseUrl + url);
            urlConnection = (HttpURLConnection) Url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/xm");
            urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

            if (urlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = IOUtils.toString(in, "UTF-8");

                return result;
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                result = IOUtils.toString(in, "UTF-8");

                return null;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        } finally {
            urlConnection.disconnect();
        }


        return result;

    }

    public String postJSON(String url) {


        String result = null;
        System.out.println("URL is " + url);
        HttpURLConnection urlConnection = null;
        try {
            URL Url = new URL(url);
            urlConnection = (HttpURLConnection) Url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(500000);
            urlConnection.setReadTimeout(500000);
            urlConnection.setRequestProperty("X-API-key", "123456");


            if (urlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code result" + result);
                return result;
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code fail result" + result + urlConnection.getResponseCode());

                return null;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        } finally {
            urlConnection.disconnect();
        }


        return result;


    }

    public String PutComplaintTravel(String title, String text, String status, String ps_id, String user_id) {
        String result = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(BaseUrl + "/api_comp/complaint");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(500000);
            urlConnection.setReadTimeout(500000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("X-API-key", "123456");
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("title", title)
                    .appendQueryParameter("text", text)
                    .appendQueryParameter("status", status)
                    .appendQueryParameter("ps_id", ps_id)
                    .appendQueryParameter("user_id", user_id);
            String query = builder.build().getEncodedQuery();
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code result" + result);
                return result;
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code fail result" + result + urlConnection.getResponseCode());

                return null;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        } finally {

        }
        return result;
    }

    public String LoginPostJson(String cvid, String password) {
        String result = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(BaseUrl + "/api_login/login_check");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(500000);
            urlConnection.setReadTimeout(500000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("X-API-key", "123456");
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("cvid", cvid)
                    .appendQueryParameter("password", password);
            String query = builder.build().getEncodedQuery();
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code result" + result);
                return result;
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code fail result" + result + urlConnection.getResponseCode());

                return null;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        } finally {

        }
        return result;
    }

    public String getJson(String url) {


        String result = null;
        System.out.println("URL is " + url);
        HttpURLConnection urlConnection = null;
        try {
            URL Url = new URL(BaseUrl + url);
            urlConnection = (HttpURLConnection) Url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(500000);
            urlConnection.setReadTimeout(500000);
            urlConnection.setRequestProperty("X-API-key", "123456");


            if (urlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code result" + result);
                return result;
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code fail result" + result + urlConnection.getResponseCode());

                return null;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        } finally {

        }


        return result;


    }

    public String putSignUP(String cvid, String name, String password, String email) {
        String result = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(BaseUrl + "/api_user/user");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(500000);
            urlConnection.setReadTimeout(500000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("X-API-key", "123456");
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("cvid", cvid)
                    .appendQueryParameter("password", password)
                    .appendQueryParameter("name", name)
                    .appendQueryParameter("email", email);
            String query = builder.build().getEncodedQuery();
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code result" + result);
                return result;
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code fail result" + result + urlConnection.getResponseCode());

                return null;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        } finally {

        }
        return result;
    }

    public String putComplaint(String cvid, String policestation_id, String subject, String content,String idcard,String doc,String status) {
        String result = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(BaseUrl + "/api_comp/complaint");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(500000);
            urlConnection.setReadTimeout(500000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("X-API-key", "123456");
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("title", subject)
                    .appendQueryParameter("text", content)
                    .appendQueryParameter("status", status)
                    .appendQueryParameter("idcard_upload_id", idcard)
                    .appendQueryParameter("document_upload_id",doc)
                    .appendQueryParameter("user_id",cvid)
                    .appendQueryParameter("ps_id",policestation_id);
            String query = builder.build().getEncodedQuery();
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code result" + result);
                return result;
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                result = IOUtils.toString(in, "UTF-8");
                System.out.println("get code fail result" + result + urlConnection.getResponseCode());

                return null;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        } finally {

        }
        return result;
    }


}
