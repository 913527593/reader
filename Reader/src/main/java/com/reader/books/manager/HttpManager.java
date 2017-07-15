package com.reader.books.manager;

import android.os.AsyncTask;
import android.util.Log;

import com.reader.books.IContact.IURL;
import com.reader.books.entify.Book;
import com.reader.books.entify.BookSpecies;
import com.reader.books.util.StreamUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络请求数据
 * Created by Administrator on 2017/6/4.
 */

public class HttpManager {
    private static final String TAG = "HttpManager";

    /**
     * 得到图书种类汇总
     *
     * @return List<BookSpecies>
     */
    public static List<BookSpecies> getBooksAllKindsWithHttpGet() {
        List<BookSpecies> speciesList = new ArrayList<>();
        String path = IURL.SPECIES_URL + "?dtype=" + IURL.DTYPE + "&key=" + IURL.KEY;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
//            connection.setDoOutput(true);
            connection.connect();
            int statusCode = connection.getResponseCode();
//            Log.i(TAG, "getBooksAllKindsWithHttpGet: statusCode="+statusCode);
            if (statusCode == 200) {
                InputStream is = connection.getInputStream();
                String str = StreamUtil.creatStream(is);
//                Log.i(TAG, "getBooksAllKindsWithHttpGet: str="+str);
                JSONObject object = new JSONObject(str);
                if (!object.getString("reason").equals("success"))
                    return null;
                JSONArray array = object.getJSONArray("result");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    String id = json.getString("id");
                    String name = json.getString("catalog");
                    BookSpecies species = new BookSpecies(Integer.parseInt(id), name);
//                    Log.i(TAG, "getBooksAllKindsWithHttpGet: species="+species.toString());
                    speciesList.add(species);
                }

            } else {
                Log.i(TAG, "getBooksAllKindsWithHttpGet: statusCode=" + statusCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return speciesList;
    }

    /**
     * 根据种类id得到该id中的图书
     *
     * @param id 种类id
     * @return List<Book>
     */
    public static List<Book> getBooksById(int id) {
        List<Book> books = new ArrayList<>();
        String path = IURL.BOOKS_URL + "?catalog_id=" + id + "&pn=0&rn=12&dtype=" + IURL.DTYPE + "&key=" + IURL.KEY;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                InputStream is = connection.getInputStream();
                String str = StreamUtil.creatStream(is);
                JSONObject object = new JSONObject(str);
//                Log.i(TAG, "getBooksById: object=" + object.toString());
                JSONObject jsonObject = object.getJSONObject("result");
                JSONArray array = jsonObject.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    String title = json.getString("title");
//                    Log.i(TAG, "getBooksById: title="+title);
                    String author = json.getString("sub1");
                    if (author.indexOf("《") > 2) {
                        author = author.substring(0, author.indexOf("《"));
                    } else {
                        author = "";
                    }
                    String sub = json.getString("sub2");
                    String img = json.getString("img");
                    String reading = json.getString("reading");
                    String online = json.getString("online");
                    String bytime = json.getString("bytime");
                    Book book = new Book(i + 1, img, title, sub, author, reading, online, bytime);
                    books.add(book);
                }
            } else {
                Log.i(TAG, "findBooksById: statusCode=" + statusCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

    private static LoadBookListener listener;

    public static void anyscBooks(LoadBookListener mListener, int id) {
        if (listener == null) {
            listener = mListener;
        }
        AnyscBooksTask task = new AnyscBooksTask();
        task.execute(id);
    }

    private static class AnyscBooksTask extends AsyncTask<Integer, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(Integer... params) {
            int id = params[0];
            List<Book> books = getBooksById(id);
            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            listener.onLoadBooksEnd(books);
        }
    }

    public interface LoadBookListener {
        void onLoadBooksEnd(List<Book> books);
    }
}
