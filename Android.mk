LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := MusicPlayer
LOCAL_MODULE_TAGS := optional
LOCAL_PACKAGE_NAME := MusicPlayer

app_root  := $(LOCAL_PATH)
app_dir   := app
app_build := $(app_root)/$(app_dir)/build
app_apk   := build/outputs/apk/release/$(app_dir)-release-unsigned.apk

$(app_root)/$(app_dir)/$(app_apk):
	rm -Rf $(app_build)
	cd $(app_root)/$(app_dir) && gradle assembleRelease

LOCAL_CERTIFICATE := platform
LOCAL_SRC_FILES := $(app_dir)/$(app_apk)
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)

include $(BUILD_PREBUILT)
