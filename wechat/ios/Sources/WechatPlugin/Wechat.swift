import Foundation

@objc public class Wechat: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
