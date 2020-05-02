<p align="center">
  <img src="https://banuacoders.com/app/pico/logo.png"  width="100" height="100" alt="PICO"/>
</p>

<h1 align="center"> PICO SulTeng Android </h1>

<p align="center">
  <a href="https://opensource.org/licenses/MIT">
    <img src="https://img.shields.io/github/license/RyanAidilPratama/PICO_SULTENG_API?color=blue" alt="License : MIT"/>
  </a>
  <a href="https://github.com/RyanAidilPratama/PICO_SULTENG_Android/releases/tag/v3.0.1">
    <img src="https://img.shields.io/github/v/release/RyanAidilPratama/PICO_SULTENG_Android" alt="Version : v3.0.1"/>
  </a>
  <a href="https://github.com/RyanAidilPratama/PICO_SULTENG_Android/commits/master">
    <img src="https://img.shields.io/github/last-commit/RyanAidilPratama/PICO_SULTENG_Android" alt="last commits"/>
  </a>
  <a href="https://img.shields.io/github/commit-activity/m/RyanAidilPratama/PICO_SULTENG_Android">
    <img src="https://img.shields.io/github/commit-activity/m/RyanAidilPratama/PICO_SULTENG_Android" alt="commit activity"/>
  </a>
  <a href="https://github.com/RyanAidilPratama/PICO_SULTENG_Android/stargazers">
    <img src="https://img.shields.io/github/stars/RyanAidilPratama/PICO_SULTENG_Android" alt="stars"/>
  </a>
  <a href="https://github.com/RyanAidilPratama/PICO_SULTENG_Android/network/members">
    <img src="https://img.shields.io/github/forks/RyanAidilPratama/PICO_SULTENG_Android" alt="forks"/>
  </a>
  <a href="">
    <img src="https://img.shields.io/github/downloads/RyanAidilPratama/PICO_SULTENG_Android/total?color=blue" alt="download">
  </a>
</p>

Pusat Informasi COVID-19 Sulawesi Tengah (PICO SulTeng) merupakan aplikasi berbasis android yang dikembangkan untuk mempermudah masyarakat Sulawesi Tengah dalam memperoleh informasi seputar perkembangan COVID-19 di Sulawesi Tengah.

## Daftar Isi

[![Lang : English](https://img.shields.io/badge/lang-en-yellow)](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/README.md)

Ini merupakan versi **Bahasa Indonesia** dari file README.md, tekan badge untuk membaca versi ***English***.

* [Informasi Umum](#informasi-umum)
* [Tangkapan Layar](#tangkapan-layar)
* [Fitur](#fitur)
* [*Library* & Dependensi](#library-&-dependensi)
* [Konfigurasi](#konfigurasi)
* [Status](#status)
* [Lisensi](#lisensi)
* [Kontak](#kontak)

## Informasi Umum

* Landing page PICO  

  Untuk berkunjung ke ***landing page*** PICO SulTeng App, silahkan klik gambar dibawah.
  
  [![PICO SulTeng](https://i.ibb.co/jRtxp9Y/picobar.png)](https://banuacoders.com/app/pico)
* Unduh APK

  Berikut ini adalah beberapa tautan untuk mengunduh PICO
  * PICO SulTeng v3.0.1 : [download](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/releases/download/v3.0.1/PICO.COVID-19.Sulawesi.Tengahv3.0.1.apk)
  * PICO SulTeng (Unsigned) v3.0.1 (Google Drive) : [download](https://s.id/PICO-SULTENG-UNSIGNED)
* Panduan penggunaan & instalasi PICO

  * Untuk melihat beberapa panduan penggunaan PICO, dapat anda lihat [disini](https://www.youtube.com/playlist?list=PLrF0GvrAdCI_-nZSASNvtGxvdln_lRIUo).
  * Petunjuk instalasi : [disini](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/releases/download/v3.0.1/Petunjuk_Instal.docx)
* Sumber Data

  Aplikasi ini menggunakan data dari [PICO API](https://github.com/RyanAidilPratama/PICO_SULTENG_API). Untuk melihat dokumentasi PICO API, silahkan klik [disini](https://github.com/RyanAidilPratama/PICO_SULTENG_API).
* Versi Minimum OS

  * **Android Lolipop (5.0)**

* Untuk import ke Android Studio :
  * Gunakan versi terbaru Android Studio - versi 3.6.3
  * RAM :  4 GB

## Tangkapan Layar

![Dashboard1](https://i.ibb.co/PcLzhrZ/PICO1.png) ![Dashboard2](https://i.ibb.co/Lnbzkrb/PICO2.png) ![Dashboard3](https://i.ibb.co/W6QzbWj/PICO3.png) ![Dashboar4](https://i.ibb.co/gd2gcmW/PICO4.png)

## Fitur

Beberapa fitur yang sudah selesai dikembangkan :

* ### **Notifikasi :**

  * Dapatkan notifikasi seputar COVID-19
  
  ![Notification](https://i.ibb.co/kmSHrsr/PICO10.png)

* ### **Dashboard, berisi :**

  * Update terkini stuasi COVID-19 di Sulawesi Tengah (Positif, Sembuh, Meninggal, Negatif).
  * Informasi jumlah ODP dan PDP di Sulawesi Tengah.
  * Tabel penyebaran kasus COVID-19 per Kabupaten/Kota se Provinsi Sulawesi Tengah.
  * Peta penyebaran kasus COVID-19 per Kabupaten/Kota se Provinsi Sulawesi Tengah.
  * Nomor telpon penting seputar COVID-19 dengan fitur ***tap to call***.

  ![Dashboard1](https://i.ibb.co/PcLzhrZ/PICO1.png) ![Dashboard2](https://i.ibb.co/Lnbzkrb/PICO2.png) ![Dashboard3](https://i.ibb.co/W6QzbWj/PICO3.png) ![Dashboar4](https://i.ibb.co/gd2gcmW/PICO4.png)

* ### **Daftar rumah sakit rujukan COVID-19 di Sulawesi Tengah :**

  * Tekan pada nomor telpon untuk menelpon rumah sakit ***(Tap to call)***.
  * Tekan pada email untuk mengirim email ke rumah sakit ***(Tap to Email)***.
  * Tombol petunjuk arah rumah sakit.
  
  ![Hospital1](https://i.ibb.co/PcLzhrZ/PICO1.png) ![Hospital2](https://i.ibb.co/vPqqtFt/PICO5.png)

* ### **Daftar posko tim SATGAS COVID-19 per Kabupaten/Kota se Sulawesi Tengah :**

  * Tekan pada nomor telpon untuk menelpon ***(Tap to call)***.

  ![Posts1](https://i.ibb.co/NSFJrKf/PICO6.png) ![Posts2](https://i.ibb.co/9GxkkgW/PICO7.png)

* ### **Pemeriksaan Mandiri :**

  * Pemeriksaan mandiri COVID-19 dengan asisten virtual dari [Detexi App](https://corona.detexi.id). Detexi merupakan startup lokal Sulawesi Tengah yang bergerak dibidang kesehatan dan saat ini bekerja sama dengan pemerintah Provinsi Sulawesi Tengah, IDI Sulawesi Tengah, dan PDUI Sulawesi Tengah.

  ![Detexi1](https://i.ibb.co/JySTdRM/PICO8.png) ![Detexi2](https://i.ibb.co/HdrQgJv/PICO9.png)

* ### **Statistik :**

  * Statistik kasus COVID-19 di Indonesia

  ![Stats1](https://i.ibb.co/G3tDPWh/PICO11.png) ![Stats2](https://i.ibb.co/dtwHPMp/PICO12.png)

* ### **Peta penyebaran (Provinsi):**

  * Peta penyebaran kasus COVID-19 per Provinsi di Indonesia.

  ![Map1](https://i.ibb.co/XDvCzN1/PICO13.png) ![Map2](https://i.ibb.co/0sfyzS3/PICO14.png)

* ### **Multi bahasa:**

  * PICO mendukung Bahasa Indonesia dan English.

  ![Bahasa](https://i.ibb.co/PcLzhrZ/PICO1.png) ![English](https://i.ibb.co/k27DfHB/PICO15.png)

*To-do List* :

* *Push notification*
  * [X] Notifikasi situasi terkini COVID-19 di Indonesia
  * [X] Notifikasi situasi terkini COVID-19 di Sulawesi Tengah
  * [] Notifikasi jika *user* berada di zona merah
  * [] Notifikasi jika *user* pernah berinteraksi dengan pasien COVID-19
  * [] Notifikasi jika di dekat lokasi *user* ada kasus COVID-19
* *In-App push messaging*
  * [X] Infografis seputar COVID-19 (Pencegahan, pengobatan, dll)
* [X] Pemeriksaan mandiri COVID-19
* Data situasi terkini COVID-19 di Sulawesi Tengah
  * [X] Kabupaten/Kota
  * [X] Kasus **ODP**
  * [X] Kasus **Dalam Pemantauan**
  * [X] Kasus **Selesai Pemantauan**
  * [X] Kasus **PDP**
  * [X] Kasus **Dalam Pengawasan**
  * [X] Kasus **Selesai Pengawasan**
  * [X] Kasus **Positif**
  * [X] Kasus **Negatif**
  * [X] Kasus **Meninggal**
  * [X] Kasus **Sembuh**
  * [X] Terakhir diperbarui
  * [] Data perkembangan harian COVID-19 di Sulawesi Tengah
* [X] Tabel distribusi kasus COVID-19 di Sulawesi Tengah
* [X] Peta penyebaran COVID-19 di Sulawesi Tengah
* Data statistik situasi COVID-19 di Indonesia
  * [X] Provinsi
  * [X] Kasus **Positif**
  * [X] Kasus **Sembuh**
  * [X] Kasus **Meninggal**
  * [X] Kumulatif kasus **Positif**
  * [X] Kumulatif kasus **Sembuh**
  * [X] Kumulatif kasus **Meninggal**
  * [X] Perkembangan harian kasus COVID-19.
* [X] Peta penyebaran COVID-19 di Indonesia
* Data rumah sakit rujukan COVID-19 Sulawesi Tengah
  * [X] Nama rumah sakit
  * [X] Alamat/Lokasi rumah sakit
  * [X] Nomor telpon rumah sakit
  * [X] Email rumah sakit
* Data posko gugus tugas COVID-19 Sulawesi Tengah
  * [X] Lokasi posko
  * [X] Nama penanggung jawab
  * [X] Nomor telpon
* Data pengguna
  * [] Registrasi
  * [] Masuk
  * [] Nama
  * [] Usia
  * [] Lokasi
  * [] Status COVID-19 (ODP, OTG, PDP, dll)
  * [] Nomor handphone/telpon

## Library & Dependensi

![project_screen](http://i.ibb.co/j8jwrQJ/screely-1586000616230.png)

> Aplikasi ini dibangun menggunakan ***design pattern*** MVVM. Beberapa library yang diperlukan antara lain :

* [Recycler View](https://developer.android.com/jetpack/androidx/releases/recyclerview) - versi 1.1.0
* [Butter Knife](https://github.com/JakeWharton/butterknife) - versi 10.1.0
* [RxJava](https://github.com/ReactiveX/RxJava) - versi 2.1.1
* [Spin Kit](https://github.com/ybq/Android-SpinKit) - versi 1.4.0
* [Glide](https://github.com/bumptech/glide) - versi 4.11.0
* [Google Material](https://www.material.io/) - versi 1.1.0
* [AnyChart](https://github.com/AnyChart/AnyChart-Android) - versi 1.1.2
* [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) - versi 3.1.0
* [Gravity SnapHelper](https://github.com/rubensousa/GravitySnapHelper) -versi 2.2.0
* [Scrolling Pager Indicator](https://github.com/TinkoffCreditSystems/ScrollingPagerIndicator) - versi 1.0.6
* [TableView](https://github.com/evrencoskun/TableView) - versi 0.8.9
* [Retrofit](https://github.com/square/retrofit) - versi 2.7.1
* [OneSignal Androdi SDK](https://github.com/OneSignal/OneSignal-Android-SDK) - versi 3.13.1
* [Androidx Room](https://developer.android.com/topic/libraries/architecture/room) - versi 2.2.5
* [Android Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - versi 1.1.1
* [Google Maps](https://github.com/googlemaps/android-maps-utils) - versi 17.0.0

## Konfigurasi

* *Clone repository* ini ke komputer/laptop anda
* *Import repository* yang sudah di *clone* ke [Android Studio](https://developer.android.com/studio)
* Setelah itu, *rebuild project* yang telah di-*import* ke Android Studio
* Tambahkan [Google API Key](https://console.cloud.google.com) dari Google Cloud Console ke file [***main/res/values/strings.xml***](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/app/src/main/res/values/strings.xml) :

  ```xml
     <string name="API_KEY" translatable="false">Put Your Google API Key Here</string>
  ```

* Jika ingin mengubah layer GeoJson pada peta penyebaran di ***MainActivity.java***, silahkan ganti file [***map.json***](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/app/src/main/assets/map.json) dengan file GeoJson anda
* Untuk mengubah label **kolom** pada tabel bisa diubah pada file ***/res/values/strings.xml*** untuk versi ***English*** dan ***/res/values-in-rID/strings.xml*** untuk versi **Bahasa Indonesia** :

  * [***main/res/values/strings.xml***](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/app/src/main/res/values/strings.xml)

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
       <string name="recovered">Recovered</string>
    ```

  * [***/res/values-in-rID/strings.xml***](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/app/src/main/res/values-in-rID/strings.xml)

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
       <string name="recovered">Sembuh</string>
    ```

* Untuk mengubah perataan teks ataupun urutan kolom tabel, bisa dilakukan pada file ***MyTableViewModel.java*** dan ***ColumnHeaderModel.java*** :

  * [***MyTableViewModel.java***](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/app/src/main/java/com/banuacoders/pico/ui/tableutil/MyTableViewModel.java)

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
           list.add(new ColumnHeaderModel(res.getString(R.string.recovered)));

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
               list.add(new CellModel("11-" + i, district.getRecovered()));
               lists.add(list);
           }
           return lists;
       }

       ....
    }
    ```

  * [***ColumnHeaderModel.java***](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/app/src/main/java/com/banuacoders/pico/ui/tableutil/model/ColumnHeaderModel.java)

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
           //recovered
           Gravity.CENTER,
       };

       ....
    }
    ```

* Untuk mengubah ***base url API*** dapat diubah pada file ***NetworkClient.java*** dan ***End Point*** yang pada file ***Api.java***. Untuk HTTP Request, PICO menggunakan [Retrofit](https://github.com/square/retrofit) :

  * [***NetworkClient.java***](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/app/src/main/java/com/banuacoders/pico/network/NetworkClient.java)

    ```java
    public class NetworkClient {
       ....

       private static final String BASE_URL_API = "Put Your API Base Url Here";

       ....
    }
    ```

  * [***Api.java***](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/app/src/main/java/com/banuacoders/pico/network/Api.java)

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

## Status

Proyek ini sudah rilis dengan versi terbaru adalah 3.0.1 dan masih akan terus dikembangkan.

## Lisensi

Lisensi [MIT](https://github.com/RyanAidilPratama/PICO_SULTENG_Android/blob/master/LICENSE).

Copyright (c) 2020 [Fajrian Aidil Pratama](https://www.linkedin.com/in/ryanaidilp/)

## Kontak

Created by [@ryanaidilp_](https://twitter.com/ryanaidilp_) - feel free to contact me!
