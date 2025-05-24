# patientFollowUp

Spring Boot + MySQL + Docker Compose ile hasta ve randevu takip API’si.

## Gereksinimler

- Java 17
- Docker & Docker Compose

## 🚀 Özellikler

- **Kullanıcı Yönetimi**  
  - Hasta ve Doktor rolleri  
  - Kayıt (`/api/auth/register`) ve Giriş (`/api/auth/login`)  
  - JWT ile stateless authentication  

- **Randevu Yönetimi**  
  - Boş (AVAILABLE) randevu saatleri otomatik oluşturma  
  - Hasta: randevu talep etme, kendi taleplerini listeleme  
  - Doktor: bekleyen talepleri listeleme, onay/red işlemleri, tüm randevularını görme  

- **API Dokümantasyonu**  
  - Swagger UI üzerinden interaktif dokümantasyon  
  - Her endpoint için summary & description  

- **Docker & Docker Compose**  
  - MySQL ve uygulama konteyner olarak tanımlı  
  - Tek komutla ayağa kalkar: `docker-compose up --build -d`  

---

## ⚙️ Kurulum

1. Projeyi klonlayın
   ```bash
   git clone https://github.com/Dawnfairy/patientFollowUp.git
   cd patientFollowUp

2. application.properties dosyasını kontrol edin

    ```properties
    spring.datasource.url=jdbc:mysql://db:3306/nyp?useSSL=false&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=12345
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
   
3. Docker Compose ile başlatın
   ```bash
   docker-compose up --build -d

4. Uygulama çalıştıktan sonra Swagger UI’ya gidin:
   ```bash
   http://localhost:8080/swagger-ui/index.html

## 📖 API Özeti

### Authentication

| Endpoint             | Method | Rol   | Açıklama                |
|----------------------|--------|-------|-------------------------|
| `/api/auth/register` | POST   | Yok   | Hasta veya doktor kaydı |
| `/api/auth/login`    | POST   | Yok   | Giriş yap, JWT al       |

### Appointments

| Endpoint                          | Method | Rol             | Açıklama                                                    |
|-----------------------------------|--------|-----------------|-------------------------------------------------------------|
| `/api/appointments/available`     | GET    | ROLE_PATIENT    | Boş randevuları listele                                     |
| `/api/appointments/{id}/request`  | POST   | ROLE_PATIENT    | ID’li randevuya talep oluştur                               |
| `/api/appointments/my-requests`   | GET    | ROLE_PATIENT    | Hastanın kendi taleplerini listele                          |
| `/api/appointments/pending`       | GET    | ROLE_DOCTOR     | Doktorun bekleyen taleplerini listele                       |
| `/api/appointments/mine`          | GET    | ROLE_DOCTOR     | Doktorun tüm randevularını listele                          |
| `/api/appointments/{id}/approve`  | POST   | ROLE_DOCTOR     | Belirtilen ID’li talebi onayla                              |
| `/api/appointments/{id}/reject`   | POST   | ROLE_DOCTOR     | Belirtilen ID’li talebi reddet                              |


## 🛠️ Test Senaryoları (Swagger UI)

1. **Hasta Kaydı**
    - **Endpoint:** `POST /api/auth/register`
    - **Body:**
      ```json
      {
        "username": "hasta1",
        "password": "secret123",
        "role": "ROLE_PATIENT",
        "patientInfo": {
          "firstName": "Ali",
          "lastName": "Veli",
          "email": "ali.veli@example.com",
          "phone": "05551234567"
        }
      }
      ```

2. **Doktor Kaydı**
    - **Endpoint:** `POST /api/auth/register`
    - **Body:**
      ```json
      {
        "username": "doktor1",
        "password": "secret123",
        "role": "ROLE_DOCTOR",
        "doctorInfo": {
          "firstName": "Dr. Ayşe",
          "lastName": "Yılmaz",
          "specialization": "Cardiology",
          "email": "ayse.demir@example.com",
          "phone": "05559876543"
        }
      }
      ```

3. **Login & JWT Alma**
    - **Endpoint:** `POST /api/auth/login`
    - **Body:**
      ```json
      {
        "username": "hasta1",
        "password": "secret123"
      }
      ```
    - **Beklenen Response:**
      ```json
      {
        "token": "<JWT_TOKENINIZ>"
      }
      ```
    - Bu token’ı Swagger UI’da **Authorize** → `Bearer <JWT_TOKENINIZ>` olarak kullanın.

4. **Randevu İşlemleri**
    - **Boş randevuları listele:**  
      **GET** `/api/appointments/available`
    - **Randevu talebi oluştur:**  
      **POST** `/api/appointments/{id}/request`
    - **Hasta taleplerini listele:**  
      **GET** `/api/appointments/my-requests`
    - **Doktor taleplerini listele:**  
      **GET** `/api/appointments/pending`
    - **Talebi onayla/reddet:**  
      **POST** `/api/appointments/{id}/approve`  
      **POST** `/api/appointments/{id}/reject`
