import Foundation

@objc public class YPush: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
