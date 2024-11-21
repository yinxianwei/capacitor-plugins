// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "YinxianweiWechat",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "YinxianweiWechat",
            targets: ["WechatPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "WechatPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/WechatPlugin"),
        .testTarget(
            name: "WechatPluginTests",
            dependencies: ["WechatPlugin"],
            path: "ios/Tests/WechatPluginTests")
    ]
)