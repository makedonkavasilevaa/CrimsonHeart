package mk.finki.ukim.mk.crimsonheart.enums;

public enum CityEnum {
    SKOPJE("Скопје"),
    BITOLA("Битола"),
    OHRID("Охрид"),
    PRILEP("Прилеп"),
    TETOVO("Тетово"),
    VELES("Велес"),
    KUMANOVO("Куманово"),
    STRUMICA("Струмица"),
    GEVGELIJA("Гевгелија"),
    STIP("Штип"),
    KAVADARCI("Кавадарци"),
    GOSTIVAR("Гостивар"),
    KOCHANI("Кочани"),
    KICEVO("Кичево"),
    KRATOVO("Кратово"),
    KRIVA_PALANKA("Крива Паланка"),
    MAKEDONSKA_KAMENICA("Македонска Каменица"),
    DEMIR_HISAR("Демир Хисар"),
    PROBISTIP("Пробиштип"),
    BEROVO("Берово"),
    DELCEVO("Делчево"),
    VINICA("Виница"),
    RADOVIS("Радовиш"),
    RESEN("Ресен"),
    DEBAR("Дебар"),
    VALANDOVO("Валандово"),
    BOGDANCI("Богданци"),
    KRUSEVO("Крушево"),
    NEGOTINO("Неготино"),
    SVETI_NIKOLE("Свети Николе"),
    MAVROVO_ROSTUSHE("Маврово и Ростуше"),
    STAR_DOJRAN("Стар Дојран");

    private final String macedonianName;

    CityEnum(String macedonianName) {
        this.macedonianName = macedonianName;
    }

    public String getMacedonianName() {
        return macedonianName;
    }
}