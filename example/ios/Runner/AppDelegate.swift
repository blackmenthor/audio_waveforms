import UIKit
import Flutter

@main
@objc class AppDelegate: FlutterAppDelegate {
    
    override func userNotificationCenter(_ center: UNUserNotificationCenter,
                                       didReceive response: UNNotificationResponse,
                                       withCompletionHandler completionHandler: @escaping () -> Void) {
               let userInfo = response.notification.request.content.userInfo
               // Print full message.
               print("tap on on forground app",userInfo)
               completionHandler()
           }
    
    override func userNotificationCenter(_ center: UNUserNotificationCenter,
                                    willPresent notification: UNNotification,
                                    withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
            let userInfo = notification.request.content.userInfo
            print(userInfo) // the payload that is attached to the push notification
            // you can customize the notification presentation options. Below code will show notification banner as well as play a sound. If you want to add a badge too, add .badge in the array.
            completionHandler([.alert,.sound])
        }
    
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    GeneratedPluginRegistrant.register(with: self)
      
    UNUserNotificationCenter.current().delegate = self
      
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
}
