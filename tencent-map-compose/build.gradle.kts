plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
}

android {
    namespace = "com.melody.map.tencent_compose"
    compileSdk = libs.versions.compile.sdk.version.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.version.get().toInt()
        lint.targetSdk = libs.versions.target.sdk.version.get().toInt()

        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = "19"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    lint {
        abortOnError = false
        checkReleaseBuilds =  false
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.lifecycle.runtime.ktx)

    // 地图库
    api(libs.tencent.map.vector.sdk)
    // 地图组件库，包括小车平移、点聚合等组件功能。
    api(libs.tencent.map.sdk.utilities)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.mariohide" // 替换为您的 GitHub 用户名
                artifactId = "tencentmap" // 替换为您的模块名称，例如 "OmniMap-Compose" 或 "amap-compose"
                version = "1.0.0" // 您希望的版本号，JitPack 会使用 Git Tag

                from(components["release"])
            }
        }
    }
}
