# Weekender 🚆

> "Cuma 14:00'te dersim bitiyor, elimde 60€ var, Pazar akşamı dönmem lazım. Nerelere gidebilirim?"

Gerçek toplu taşıma verisi (GTFS) üzerinde sıfırdan yazılmış bir rota motoru (Java) ve haritalı web sitesi.

## Yol haritası

### Faz 0: Anla (1-2 gün)
- [ ] GTFS dosyalarını araştır (stops, trips, stop_times, calendar)
- [ ] Connection Scan Algorithm makalesinin özetini oku
- [ ] `notlar/gun1.md` içindeki 3 soruyu cevapla

### Faz 1: Veriyi oku (3-4 gün)
- [ ] GTFS verisini indir → `data/`
- [ ] Java projesi oluştur
- [ ] `stops.txt` oku → "Kaç durak var?" yazdır
- [ ] `stop_times.txt` oku → bağlantı (connection) listesi oluştur, kalkış saatine göre sırala

### Faz 2: Motor 🧠 (1 hafta)
- [ ] CSA: "A'dan saat X'te çıkarsam B'ye en erken kaçta varırım?"
- [ ] Hangi tren/aktarmalarla gittiğini yazdır (rota)
- [ ] Tüm hedefler: "A'dan saat X'te çıkarsam NERELERE varabilirim?"
- [ ] Hız ölçümü (milisaniye)

### Faz 3: Weekender kuralları (1 hafta)
- [ ] Dönüş garantisi: "Pazar akşamına kadar geri dönebilir miyim?"
- [ ] Hedefte en az X saat geçirme
- [ ] Bütçe (fiyat tahmini)

### Faz 4: Web sitesi 🖥️ (1-2 hafta)
- [ ] Spring Boot ile API
- [ ] HTML/JS + Leaflet harita
- [ ] Sonuçları haritada göster

### Faz 5: Cila
- [ ] Birden fazla ülkenin verisi
- [ ] RAPTOR algoritması ve CSA ile karşılaştırma
- [ ] İnternete yayınla
# Weekender
