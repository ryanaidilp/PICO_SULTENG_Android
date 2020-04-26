# PICO SulTeng Android

![PICO SulTeng](http://i.ibb.co/jRtxp9Y/picobar.png)

Pusat Informasi COVID-19 Sulawesi Tengah (PICO SulTeng) merupakan aplikasi berbasis android yang dikembangkan untuk mempermudah masyarakat Sulawesi Tengah dalam memperoleh informasi seputar perkembangan COVID-19 di Sulawesi Tengah. 

#### Instalasi

Download APK [disini](https://banuacoders.com/app/pico) tekan Download lalu install APK di smartphone anda.

#### Minimum system requirements : 
 
Android Lolipop (5.0)



## **Repository**
![project_screen](http://i.ibb.co/j8jwrQJ/screely-1586000616230.png)
Aplikasi ini dibangun menggunakan ***design pattern*** MVVM. Beberapa library yang diperlukan antara lain :
-   [Recycler View](https://developer.android.com/jetpack/androidx/releases/recyclerview) : Digunakan untuk menampilkan data dalam bentuk ***list/scroll view***.
-   [Butter Knife](https://github.com/JakeWharton/butterknife) : Digunakan untuk menerapkan ***view binding***.
-   [RxJava](https://github.com/ReactiveX/RxJava) : Digunakan untuk melakukan proses ***asynchronous***.
-   [Spin Kit](https://github.com/ybq/Android-SpinKit) : Digunakan untuk mengganti tampilan ***progress bar*** biasa menjadi lebih modern.
-   [Glide](https://github.com/bumptech/glide) : Digunakan untuk **menampilkan gambar** dari internet ke  aplikasi.
-   [Google Material](https://www.material.io/) : Digunakan untuk menerapkan ***Material Design***  dari Google.
-   [AnyChart](https://github.com/AnyChart/AnyChart-Android) : Digunakan untuk **memvisualisasikan data** ke dalam berbagai bentuk grafik.
-   [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) : Digunakan untuk **memvisualisasikan data** ke dalam berbagai bentuk grafik.
-   [Gravity SnapHelper]() : Tambahan untuk ***Recycler View*** .
-   [Scrolling Pager Indicator](https://github.com/TinkoffCreditSystems/ScrollingPagerIndicator) : Digunakan untuk memberikan ***scrolling indicator*** pada ***Recycler View***.
-   [TableView](https://github.com/evrencoskun/TableView) : Digunakan untuk **menampilkan data** ke dalam **tabel**.
-   [Retrofit](https://github.com/square/retrofit) : Digunakan untuk **melakukan koneksi** ke server.
-   [OneSignal](https://github.com/OneSignal/OneSignal-Android-SDK) : Digunakan untuk menghubungkan aplikasi ke layanan ***push notification*** [OneSignal](https://onesignal.com/) 
-   [Androidx Room](https://developer.android.com/topic/libraries/architecture/room) : Digunakan untuk menerapkan **penyimpanan data local**.
-   [Android Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) : Digunakan untuk  merespon perubahan ***lifecycle activity/fragment***.
-   [Google Maps](https://github.com/googlemaps/android-maps-utils) : Digunakan untuk menampilkan ***Google Maps*** ke aplikasi.


#### Implementasi library 
- Tambahkan ini di file **build.gradle** di module (**app**) :

```gradle
    
     //RecyclerView
    implementation 'androidx.recyclerview:recyclerview:1.1.0'

    //ButterKnife
    implementation 'com.jakewharton:butterknife:10.1.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:10.1.0'
    
    //RxJava
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    implementation 'io.reactivex.rxjava2:rxjava:2.1.1'
    
    //SpinKit
    implementation 'com.github.ybq:Android-SpinKit:1.4.0'

    //Glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'

    //Google Material
    implementation 'com.google.android.material:material:1.1.0'

    //AnyChart
    implementation 'com.github.AnyChart:AnyChart-Android:1.1.2'

    //MPAndroidChart
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
    
    //GravitySnapper
    implementation 'com.github.rubensousa:gravitysnaphelper:2.2.0'

    //Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.7.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.7.1'

    //TableView
    implementation 'com.evrencoskun.library:tableview:0.8.9'

    //ScrollingPagerIndicator
    implementation "ru.tinkoff.scrollingpagerindicator:scrollingpagerindicator:1.0.6"

    //OneSignal     
    implementation 'com.onesignal:OneSignal:3.13.1'

    //Lifecycle
    implementation 'android.arch.lifecycle:extensions:1.1.1'
    annotationProcessor 'android.arch.lifecycle:common-java8:1.1.1'

   
    //Androidx Room
    implementation 'androidx.room:room-runtime:2.2.5'
    annotationProcessor 'androidx.room:room-compiler:2.2.5'
    
    //Google Maps
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.maps.android:android-maps-utils:1.0.2'
```

- Tambahkan ini di **build.gradle** di modul (**project**) :
```gradle
buildscript {
    
    ....    

    repositories {
        ....
        maven { url 'https://plugins.gradle.org/m2/'}
        ....
    }

    dependencies {
        ....
        classpath 'gradle.plugin.com.onesignal:onesignal-gradle-plugin:0.12.6'
        ....
    }
    ....
}

allprojects {
    repositories {
        ....
        maven { url 'https://jitpack.io' }
        maven { url 'https://maven.google.com' }
        ....
    }
}
```

- Tambahkan [Google API Key](https://console.cloud.google.com) dari Google Cloud Console ke file **main/res/values/strings.xml** :
```xml
   <string name="API_KEY" translatable="false">Put Your Google API Key Here</string>
```

- Jika ingin mengubah layer GeoJson pada peta penyebaran di **MainActivity.java**, silahkan ganti file **map.json** di folder **/assets/map.json**

- Mengubah nama **Kolom** pada tabel bisa diubah pada file **/res/values/strings.xml** untuk versi ***English*** dan **/res/values-in-rID/strings.xml** untuk versi ***Bahasa Indonesia*** :

  - #### /res/values/strings.xml

    ```xml
       <string name="district">District/City</string>
       <string name="odp">ODP</string>
       <string name="completed_odp">Completed ODP</string>
       <string name="in_odp">In ODP</string>
       <string name="pdp">PDP</string>
       <string name="completed_pdp">Completed PDP</string>
       <string name="in_pdp">In PDP</string>
       <string name="positive">Positive</string>
       <string name="negative">Negative</string>
       <string name="dead">Death</string>
    ```
  - #### /res/values-in-rID/strings.xml

    ```xml
       <string name="district">Kabupaten/Kota</string>
       <string name="odp">ODP</string>
       <string name="completed_odp">Selesai ODP</string>
       <string name="in_odp">Sisa ODP</string>
       <string name="pdp">PDP</string>
       <string name="completed_pdp">Selesai PDP</string>
       <string name="in_pdp">Sisa PDP</string>
       <string name="positive">Positif</string>
       <string name="negative">Negatif</string>
       <string name="dead">Meninggal</string>
    ```
- Untuk mengubah perataan teks ataupun urutan kolom tabel, bisa dilakukan pada file **MyTableViewModel.java** dan **ColumnHeaderViewHolder.java** :
  - #### /java/com/banuacoders/pico/ui/tableutil/MyTableViewModel.java
    ```java
    public class MyTableViewModel {
       ....
       
       private List<ColumnHeaderModel> createColumnHeaderModelList() {
           List<ColumnHeaderModel> list = new ArrayList<>();

           // Create Column Headers
           list.add(new ColumnHeaderModel(res.getString(R.string.district)));
           list.add(new ColumnHeaderModel(res.getString(R.string.odp)));
           list.add(new ColumnHeaderModel(res.getString(R.string.completed_odp)));
           list.add(new ColumnHeaderModel(res.getString(R.string.in_odp)));
           list.add(new ColumnHeaderModel(res.getString(R.string.pdp)));
           list.add(new ColumnHeaderModel(res.getString(R.string.completed_pdp)));
           list.add(new ColumnHeaderModel(res.getString(R.string.in_pdp)));
           list.add(new ColumnHeaderModel(res.getString(R.string.positive)));
           list.add(new ColumnHeaderModel(res.getString(R.string.negative)));
           list.add(new ColumnHeaderModel(res.getString(R.string.dead)));

           return list;
       }

       private List<List<CellModel>> createCellModelList(List<District> districtList) {
           List<List<CellModel>> lists = new ArrayList<>();
           for (int i = 0; i < districtList.size(); i++) {
               District district = districtList.get(i);
               List<CellModel> list = new ArrayList<>();
               list.add(new CellModel("1-" + i, district.getName()));
               list.add(new CellModel("2-" + i, district.getODP()));
               list.add(new CellModel("3-" + i, district.getFinishedODP()));
               list.add(new CellModel("4-" + i, district.getInODP()));
               list.add(new CellModel("5-" + i, district.getPDP()));
               list.add(new CellModel("6-" + i, district.getFinishedPDP()));
               list.add(new CellModel("7-" + i, district.getInPDP()));
               list.add(new CellModel("8-" + i, district.getPositive()));
               list.add(new CellModel("9-" + i, district.getNegative()));
               list.add(new CellModel("10-" + i, district.getDeath()));
               lists.add(list);
           }
           return lists;
       }
       
       ....
    }   
    ```
  - #### /java/com/banuacoders/pico/tableutil/model/ColumnHeaderModel.java
    ```java
    public class ColumnHeaderModel {
        ....
        
        static final int[] COLUMN_TEXT_ALIGNS = {
           //city name
           Gravity.START, //Rata Kiri
           //pdp
           Gravity.CENTER, //Rata Tengah
           //Finish PDP
           Gravity.CENTER,
           //In PDP
           Gravity.CENTER,
           //odp
           Gravity.CENTER,
           //Finish ODP
           Gravity.CENTER,
           //In ODP
           Gravity.CENTER,
           //positive
           Gravity.CENTER,
           //negative
           Gravity.CENTER,
           //death
           Gravity.CENTER,
       };
       
       ....
    }
    ```
- Untuk mengubah ***Base Url API*** dapat diubah pada file **NetworkClient.java** dan ***End Point*** yang pada file **Api.java**. Untuk HTTP Request, PICO menggunakan [Retrofit](https://github.com/square/retrofit) :
  - #### /java/com/banuacoders/pico/network/NetworkClient.java
    ```java
    public class NetworkClient {
       ....
       
       private static final String BASE_URL_API = "Put Your API Base Url Here";
       
       ....
    }
    ```
  - #### /java/com/banuacoders/pico/network/Api.java
    ```java
    public interface Api {
    
        @GET("query")
        Call<ResponseBody> getCovidStats(
            @QueryMap Map<String, Object> queryMap
        );

        @GET("kabupaten")
        Call<ResponseBody> getAllCity();

        @GET("rumahsakit")
        Call<ResponseBody> getAllHospital();

        @GET("provinsi")
        Call<ResponseBody> getAllProvince();
        
        @GET("posko")
        Call<ResponseBody> getAllPosts();
    
    } 
    ```
