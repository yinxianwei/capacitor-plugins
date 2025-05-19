import Foundation

@objc public class Umeng: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
