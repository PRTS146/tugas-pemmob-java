Open in Android Studio: File > Open > select the MahasiswaApp folder, wait for Gradle sync.
(If it offers to upgrade the Android Gradle Plugin, you can accept or skip.)

Edit BASE_URL in app/src/main/java/com/example/myapplication/api/RetrofitClient.java:
  emulator        -> http://10.0.2.2/api_mahasiswa/
  phone via USB   -> http://localhost/api_mahasiswa/   (run: adb reverse tcp:80 tcp:80)
  phone via Wi-Fi -> http://YOUR_PC_IP/api_mahasiswa/

Backend (api_mahasiswa folder) goes in C:\laragon\www\ - see mahasiswa_complete.zip.
