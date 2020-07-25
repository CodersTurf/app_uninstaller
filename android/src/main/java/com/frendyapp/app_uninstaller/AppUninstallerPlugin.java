package com.frendyapp.app_uninstaller;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** RemoveAppPlugin */
public class AppUninstallerPlugin implements FlutterPlugin, MethodCallHandler, PluginRegistry.ActivityResultListener, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private static Registrar registrar;
  private  static Activity activity;
  private Result uninstallResult;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {

    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "app_uninstaller");
    channel.setMethodCallHandler(this);

  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "app_uninstaller");
    activity=registrar.activity();
    registrar=registrar;
    AppUninstallerPlugin plugin=new AppUninstallerPlugin();
    registrar.addActivityResultListener(plugin);
    channel.setMethodCallHandler(plugin);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("Uninstall")) {
      this.uninstallResult=result;
      Intent intent = new Intent(Intent.ACTION_DELETE);
      String app=call.argument("Package");
      intent.setData(Uri.parse("package:$app"));
      intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
      intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
      activity.startActivityForResult(intent,1);

    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
    if(data==null)
    {
      uninstallResult.success(false);
    }
    else
    {
      uninstallResult.success(true);
    }
    return  true;
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity=binding.getActivity();
    binding.addActivityResultListener(this);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }
}
