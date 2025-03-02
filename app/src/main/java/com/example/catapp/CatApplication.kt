package com.example.catapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The CatApplication class extends the Application class and is annotated with
 * @HiltAndroidApp to enable dependency injection throughout the app using Hilt.
 *
 * This class is the entry point for the application and is responsible for initializing
 * Hilt's dependency injection framework. It is required to annotate the Application
 * class with @HiltAndroidApp in order to allow Hilt to generate and manage dependencies.
 */
@HiltAndroidApp
class CatApplication: Application()