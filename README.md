# PICO SulTeng Android

![PICO SulTeng](http://i.ibb.co/jRtxp9Y/picobar.png)

PICO SulTeng (Pusat Informasi COVID19 Sulawesi Tengah) merupakan aplikasi tracking dan pelaporan kasus COVID-19 khusus wilayah Provinsi Sulawesi Tengah.

#### Instalasi

Download APK [disini](https://banuacoders.com/app/pico) tekan Download lalu install APK di smartphone anda.

#### Minimum system requirements : 
 
Android Lolipop (5.0)



## **Repository**
![project_screen](http://i.ibb.co/6WszgDp/screely-1585480662870.jpg)
Aplikasi ini dibangun menggunakan ***design pattern*** MVVM. Beberapa dependensi yang diperlukan antara lain :

- Tambahkan ini di file **build.gradle** di module (**app**) :

```gradle
    
    //RecyclerView
    implementation 'androidx.recyclerview:recyclerview:1.1.0'

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
    implementation 'com.onesignal:OneSignal:3.4.3'

    //Lifecycle
    implementation 'android.arch.lifecycle:extensions:1.1.1'
    annotationProcessor 'android.arch.lifecycle:common-java8:1.1.1'

   
    //Androidx Room
    implementation 'androidx.room:room-runtime:2.2.5'
    annotationProcessor 'androidx.room:room-compiler:2.2.5'
```

- Tambahkan ini di **build.gradle** di modul (**project**) :
```gradle
buildscript {
    
    ....    

    dependencies {
        classpath 'gradle.plugin.com.onesignal:onesignal-gradle-plugin:0.12.6'
    }
    ....
}
```
