[librerias a importar en gradle:]
(fuera de dependencies):
def lifecycleExtensionVersion = '1.1.1'
def butterknifeVersion = '10.1.0'
def supportVersion = '29.0.0'
def retrofitVersion = '2.3.0'
def glideVersion = '4.9.0'
def rxJavaVersion = '2.1.1'
def daggerVersion = '2.14.1'
def mockitoVersion = '2.11.0 '

(dentro de dependencies):
implementation "com.android.support:design:$supportVersion"
implementation "android.arch.lifecycle:extensions:$lifecycleExtensionVersion"
implementation "com.jakewharton:butterknife:$butterknifeVersion"
annotationProcessor "com.jakewharton:butterknife-compiler:$butterknifeVersion"
//retrofit
implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
//equivalente a suspend function de kotlin
implementation "io.reactivex:rxjava2:rxjava:$rxJavaVersion"
implementation "io.reactivex:rxjava2:rxandroid:$rxJavaVersion"
//glide
implementation "com.github.bumptech.glide:glide:$glideVersion"
//dagger
implementation "com.google.dagger:dagger:$daggerVersion"
implementation "com.google.dagger:dagger-android-support:$daggerVersion"
implementation "androidx.legacy:legacy-support-v4:1.0.0"
annotationProcessor "com.google.dagger:dagger-compiler:$daggerVersion"
annotationProcessor "com.google.dagger:dagger-android-processor:$daggerVersion"
//mockito
testImplementation "org.mockito:mockito-inline:$mockitoVersion"
testImplementation "android.arch.core:core-testing:1.1.1"

[para evitar el error en activity con .of en la instancia de viewModel]
en gradle.properties agregar:
android.enableJetifier=true