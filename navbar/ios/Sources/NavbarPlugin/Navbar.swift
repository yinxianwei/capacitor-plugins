import Foundation

@objc public class Navbar: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
