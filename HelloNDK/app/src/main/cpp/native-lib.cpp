#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_utte_hellondk_JNIUtils_stringFromJNI(JNIEnv* env, jobject /* this */) {
    std::string hello = "hello jni";
    return env->NewStringUTF(hello.c_str());
}
