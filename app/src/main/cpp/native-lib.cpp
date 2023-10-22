#include <jni.h>
#include <string>


JNIEXPORT jstring  extern "C" JNICALL
Java_com_jyotish_template_network_Base_getBaseUrl(JNIEnv *env, jclass clazz) {
    std::string mUrl = "https://goubba.com/Api/";
    return env->NewStringUTF(mUrl.c_str());
}


JNIEXPORT jstring
extern "C" JNICALL
Java_com_jyotish_template_network_Base_getNewBaseUrl(JNIEnv *env, jclass clazz) {
    std::string mUrl = "https://channel.scorelive.xyz/";
    return env->NewStringUTF(mUrl.c_str());
}

extern "C"
JNIEXPORT jstring  extern "C" JNICALL
Java_com_jyotish_template_network_Base_getTestUrl(JNIEnv *env, jclass clazz) {
    std::string mUrl = "https://goubba.com/iota-dev/api/";
    return env->NewStringUTF(mUrl.c_str());
}