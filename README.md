
# 🎬 kinoqor — Your Personal Movie Explorer App

**kinoqor** — это Android-приложение, разработанное в **Android Studio** с использованием **Kotlin**, которое позволяет пользователям просматривать список фильмов, кинотеатров и управлять своим аккаунтом.
Приложение построено по архитектуре **MVVM** и использует современные Android-подходы к разработке.

---

## 🚀 Features Overview

| Feature                | Description                                              |
| ---------------------- | -------------------------------------------------------- |
| 🔍 **Browse Movies**   | Просмотр списка популярных и актуальных фильмов          |
| 🎥 **Movie Details**   | Детальная информация о фильме (описание, рейтинг и т.д.) |
| 🏢 **View Cinemas**    | Просмотр списка кинотеатров                              |
| 🔐 **Authentication**  | вход и выход пользователя                                |
| 🔑 **Change Password** | Возможность смены пароля пользователя                    |

---

## 🏗 App Architecture

Проект реализован с использованием архитектурного паттерна **MVVM (Model–View–ViewModel)**.

| Layer          | Responsibilities                                               |
| -------------- | -------------------------------------------------------------- |
| **Model**      | Модели данных (Movie, User, Cinema и др.), `data class`, `DTO` |
| **ViewModel**  | Бизнес-логика, работа с репозиториями, управление состоянием   |
| **View**       | UI-слой (Activity / Fragment / XML или Jetpack Compose)        |
| **Repository** | Работа с API и источниками данных                              |

---

## 🛠 Technologies Used

* **Kotlin**
* **Android Studio**
* **MVVM**
* **Retrofit + OkHttp** — сетевые запросы
* **RecyclerView** — отображение списков

---

## 📱 Platform

* **Android**
* Минимальная версия SDK: **Android 8.0 (API 26)** *

---

## 👥 Project Members

| Full Name           | ID        |
| ------------------- | --------- |
| Abaizhanov Temirlan | 22B030503 |
| Tasbay Akzhol       | 22B030592 |

---

We used for backend/api our own backend endpoints, link: https://github.com/aqzsha/go-project

