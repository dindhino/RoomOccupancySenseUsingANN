# Room Occupancy Sense Using Artificial Neural Network
Tugas Program 4 - Artificial Intelligence - Implementasi Jaringan Syaraf Buatan
<br/>
<br/>
<br/>
<br/>
Dekripsi Kasus : <br/>
Kasus ini mengimplementasikan Jaringan Syaraf Tiruan (JST) untuk binary classification dengan studi kasus identifikasi okupansi ruangan. Arsitektur JST yang digunakan adalah Multi-Layer Feedforward Networks dengan algoritma pembelajaran Backpropagation.<br/>
<br/>
Pada studi kasus ini, class label y = {0, 1} di mana y = 0 untuk ruangan yang sedang kosong (tidak ada orang di ruangan), sedangkan y = 1 untuk ruangan yang sedang digunakan (ada orang di ruangan). Variabel-variabel yang digunakan untuk menentukan class label antara lain Temperatur (Temperature), Kelembaban (Humidity), Cahaya (Light), dan CO2. Data set yang digunakan berasal dari UCI Machine Learning Repository.<br/>
<br/>
Gunakan data set occupancy.xlsx dan occupancy_norm.xlsx . Perbedaan dari kedua file tersebut adalah pada occupancy_norm.xlsx sudah dilakukan preprocessing, yakni hasil normalisasi nilai-nilai dalam setiap atribut input dari file occupancy.xlsx ke dalam rentang [0.01,0.99]. Di dalam setiap file tersebut terdapat 3 sheets: training, validation, dan testing. Untuk setiap sheet, terdapat beberapa kolom atribut, di mana kolom terakhir (paling kanan) merupakan kelas yang menjadi target (class label). 
