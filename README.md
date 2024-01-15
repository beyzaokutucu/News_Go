# NewsGo

NewsGo, haberleri görüntülemenize ve favori haberleri yönetmenize olanak tanıyan bir haber uygulamasıdır.

## Özellikler

- Haber listesini görüntüleme
- Favori haberleri yönetme
- Haber detaylarını görüntüleme
- Haber kaynağına gitme

## Kullanılan Teknolojiler

- Kotlin
- Firebase Firestore
- Retrofit
- Coroutines
- Glide
- Dagger Hilt

## Kurulum

1. **Firebase Projesi Oluşturma:**
   - Firebase konsolundan yeni bir proje oluşturun.
   - Realtime Database ve Firestore'u etkinleştirin.
   - `google-services.json` dosyasını indirin ve `app` modülü altına ekleyin.

2. **API Anahtarı Oluşturma:**
   - [News API](https://newsapi.org/) üzerinden bir API anahtarı alın.
   - `NewsDataSource` sınıfındaki `API_KEY` değerini bu anahtarla güncelleyin.

3. **Dagger Hilt Bağlamını Güncelleme:**
   - Projede Dagger Hilt kullanıldıysa, gerekli bağlam dosyalarını güncelleyin.

4. **Projeyi Açma:**
   - Projeyi bir IDE'de veya tercih ettiğiniz bir geliştirme ortamında açın.

5. **Bağımlılıkları Güncelleme:**
   - `build.gradle` dosyalarını açın ve gerekli bağımlılıkları güncelleyin.

6. **Çalıştırma:**
   - Uygulamayı başlatın ve haberleri keşfetmeye başlayın!

## Ek Bilgiler

- Projede MVVM mimarisini kullanıyoruz.
- Firebase Firestore, haberleri favorilere eklemek ve yönetmek için kullanılıyor.
- Retrofit ile News API'den haber verileri alınıyor.
- Dagger Hilt, bağımlılık enjeksiyonunu kolaylaştırmak için kullanılıyor.

