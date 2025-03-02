# CatApp

## Overview
CatApp is an Android application that allows users to browse different cat breeds, mark their favorite breeds, and manage the favorite state efficiently. The app follows the Clean Architecture pattern to ensure a well-structured and scalable codebase.

## Features Implemented
- **Clean Architecture**: Separated concerns into different layers (Presentation, Domain, and Data).
- **Fetching Cat Breeds**: Integrated API calls to fetch the list of cat breeds.
- **Favorite State Management**: Users can mark breeds as favorites, and this state is stored persistently.
- **Room Database**: Storing cat breeds and favorite state locally for offline support.
- **Search Functionality**: Allow users to search for specific cat breeds.
- **Detail Screen**: A dedicated screen displaying in-depth details of a selected cat.
- **Modern UI with Jetpack Compose**:
    - Two-tab navigation (All Cats & Favorites)
    - Grid layout for displaying cats with image, name, and favorite toggle button.

## Upcoming Features

- **Error Handling**: Implement proper error messages and fallback mechanisms.
- **Pagination & Lazy Loading**: Pagination will be added later.
- **Testing**:
    - Unit tests for ViewModel, UseCases, and Repository.
    - UI tests to ensure smooth user experience.

## Technologies Used
- **Kotlin**
- **Jetpack Compose**
- **Hilt (Dependency Injection)**
- **Retrofit (Networking)**
- **Room Database**
- **Flow & LiveData**
- **Coil (Image Loading)**
- **Navigation Component**

## How to Run the Project
1. Clone the repository.
2. Open the project in Android Studio.
3. Sync dependencies.
4. Run the app on an emulator or a physical device.