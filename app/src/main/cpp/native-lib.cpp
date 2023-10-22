#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_jyotish_template_network_Base_getBaseUrl(JNIEnv *env, jclass clazz) {
    std::string mUrl = "https://goubba.com/Api/";
    return env->NewStringUTF(mUrl.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_jyotish_template_network_Base_getNewBaseUrl(JNIEnv *env, jclass clazz) {
    std::string mUrl = "https://channel.scorelive.xyz/";
    return env->NewStringUTF(mUrl.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_jyotish_template_network_Base_getTestUrl(JNIEnv *env, jclass clazz) {
    std::string mUrl = "https://goubba.com/iota-dev/api/";
    return env->NewStringUTF(mUrl.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_jyotish_template_network_Base_getToken(JNIEnv *env, jclass clazz) {
    std::string mUrl = "1561905fe0fc8d5c642187dcdbbaaff170ac126d";
    return env->NewStringUTF(mUrl.c_str());
}
