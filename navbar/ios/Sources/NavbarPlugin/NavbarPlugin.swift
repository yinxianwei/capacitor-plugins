import Foundation
import Capacitor


/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(NavbarPlugin)
public class NavbarPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "NavbarPlugin"
    public let jsName = "Navbar"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "setup", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setTitle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLeftVisibility", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLeftIcon", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setRightVisibility", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setRightIcon", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "allowsBackForwardNavigationGestures", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "exitApp", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setVisibility", returnType: CAPPluginReturnPromise),
    ]
    private let implementation = Navbar()
    public let navbar = UIView(frame: .zero)
    public let titleLabel = UILabel()
    public let leftBtn = UIButton()
    public let rightBtn = UIButton()
    var topConstraint: NSLayoutConstraint!
    var _navigation_bar_height: CGFloat = 44

    @objc func setup(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let viewController = self.bridge?.viewController as? CAPBridgeViewController
            
            self.webView?.scrollView.bounces = true
            self.webView?.scrollView.backgroundColor = .white
            self.webView?.translatesAutoresizingMaskIntoConstraints = false

            self.navbar.backgroundColor = .white
            self.navbar.translatesAutoresizingMaskIntoConstraints = false
            
            self.leftBtn.translatesAutoresizingMaskIntoConstraints = false;
            self.leftBtn.addTarget(self, action: #selector(self.leftClick), for: UIControl.Event.touchUpInside);
            self.leftBtn.isHidden = true;
            self.navbar.addSubview(self.leftBtn)
            
            self.rightBtn.translatesAutoresizingMaskIntoConstraints = false;
            self.rightBtn.addTarget(self, action: #selector(self.rightClick), for: UIControl.Event.touchUpInside);
            self.rightBtn.isHidden = true;
            self.navbar.addSubview(self.rightBtn)

            self.titleLabel.translatesAutoresizingMaskIntoConstraints = false;
            self.titleLabel.textAlignment = .center
            self.titleLabel.textColor = .black
            self.navbar.addSubview(self.titleLabel)
            
            let scene: UIWindowScene? = UIApplication.shared.connectedScenes.first as? UIWindowScene
            self._navigation_bar_height = (scene?.statusBarManager?.statusBarFrame.height ?? 0) + 44
            let view = viewController!.view!
            view.addSubview(self.navbar)
            self.navbar.isHidden = false
            self.webView?.removeConstraints(self.webView!.constraints)
            view.removeConstraints(view.constraints)
            self.topConstraint = self.webView!.topAnchor.constraint(equalTo: view.topAnchor, constant: self._navigation_bar_height)

            NSLayoutConstraint.activate([
                self.topConstraint,
                self.webView!.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: 0),
                self.webView!.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 0),
                self.webView!.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: 0),
                
                self.navbar.topAnchor.constraint(equalTo: view.topAnchor, constant: 0),
                self.navbar.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 0),
                self.navbar.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: 0),
                self.navbar.heightAnchor.constraint(equalToConstant: self._navigation_bar_height),
                
                self.leftBtn.bottomAnchor.constraint(equalTo: self.navbar.bottomAnchor, constant: 0),
                self.leftBtn.leadingAnchor.constraint(equalTo: self.navbar.leadingAnchor, constant: 0),
                self.leftBtn.heightAnchor.constraint(equalToConstant: 44),
                self.leftBtn.widthAnchor.constraint(equalToConstant: 44),
                
                self.rightBtn.bottomAnchor.constraint(equalTo: self.navbar.bottomAnchor, constant: 0),
                self.rightBtn.trailingAnchor.constraint(equalTo: self.navbar.trailingAnchor, constant: 0),
                self.rightBtn.heightAnchor.constraint(equalToConstant: 44),
                self.rightBtn.widthAnchor.constraint(equalToConstant: 44),

                self.titleLabel.heightAnchor.constraint(equalToConstant: 44),
                self.titleLabel.leadingAnchor.constraint(equalTo: self.leftBtn.trailingAnchor, constant: 0),
                self.titleLabel.trailingAnchor.constraint(equalTo: self.rightBtn.leadingAnchor, constant: 0),
                self.titleLabel.bottomAnchor.constraint(equalTo: self.navbar.bottomAnchor, constant: 0)
            ])
            
            call.resolve();
        }
    }
    @objc func setVisibility(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            if self.topConstraint != nil {
                let visible = call.getBool("value") ?? true
                if visible {
                    self.topConstraint.constant = self._navigation_bar_height
                    self.webView!.scrollView.contentInset.top = 0
                    self.navbar.alpha = 1
                } else {
                    self.topConstraint.constant = 0
                    self.webView!.scrollView.contentInset.top = 0
                    self.navbar.alpha = 0
                }
                let viewController = self.bridge?.viewController as? CAPBridgeViewController
                viewController?.view.layoutIfNeeded()
            }
            call.resolve()
        }

    }
    @objc func setTitle(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let value = call.getString("value") ?? ""
            self.titleLabel.text = value
            call.resolve()
        }
    }
    @objc func setLeftVisibility(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let value = call.getBool("value", false);
            self.leftBtn.isHidden = !value;
            call.resolve()
        }
    }
    @objc func setLeftIcon(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let normal = call.getString("normal", "")
            if normal.count != 0 {
                self.leftBtn.setImage(UIImage(named: normal), for: UIControl.State.normal)
            }
            call.resolve()
        }
    }
    @objc func setRightVisibility(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let value = call.getBool("value", false);
            self.rightBtn.isHidden = !value;
            call.resolve()
        }
    }
    @objc func setRightIcon(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let normal = call.getString("normal", "")
            if normal.count != 0 {
                self.rightBtn.setImage(UIImage(named: normal), for: UIControl.State.normal)
            }
            call.resolve()
        }
    }
    @objc func allowsBackForwardNavigationGestures(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let value = call.getBool("value", false);
            self.webView?.allowsBackForwardNavigationGestures = value
            call.resolve()
        }
    }
    @objc func exitApp(_ call: CAPPluginCall) {
        exit(0)
    }
    @objc func leftClick() {
        webView?.goBack()
    }
    @objc func rightClick() {
        self.notifyListeners("onRightClick", data: nil);
    }
}
