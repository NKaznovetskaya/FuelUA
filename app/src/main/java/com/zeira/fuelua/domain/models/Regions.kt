package com.zeira.fuelua.domain.models

enum class Regions(val region: String, val city_index: String) {
    ALL_UKRAINE("Вся Україна", "01"),
    VINNYTSIA("Вінницька", "02"),
    VOLYN("Волинська", "03"),
    DNIPROPETROVSK("Дніпропетровська", "04"),
    DONETSK("Донецька", "05"),
    ZHYTOMYR("Житомирська", "06"),
    ZAKARPATTIA("Закарпатська", "07"),
    ZAPORIZHZHIA("Запорізька", "08"),
    IVANO_FRANKIVSK("Івано-Франківська", "09"),
    KYIV("Київська", "10"),
    KIROVOHRAD("Кіровоградська", "11"),
    LUHANSK("Луганьська", "12"),
    LVIV("Львівська", "13"),
    MYKOLAIV("Миколаївська", "14"),
    ODESA("Одеська", "15"),
    POLTAVA("Полтавська", "16"),
    RIVNE("Рівненська", "17"),
    SUMY("Сумська", "18"),
    TERNOPIL("Тернопільська", "19"),
    KHARKIV("Харківська", "20"),
    KHERSON("Херсонська", "21"),
    KHMELNYTSKYI("Хмельницька", "22"),
    CHERKASY("Черкаська", "23"),
    CHERNIVTSI("Чернівецька", "24"),
    CHERNIHIV("Чернігівська", "25")

}