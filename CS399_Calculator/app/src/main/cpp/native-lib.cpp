#include <jni.h>
#include <string>

extern "C"
jstring
Java_tbgsinc_cs399_1calculator_CalculationScreen_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
