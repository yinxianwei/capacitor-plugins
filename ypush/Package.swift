// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "YinxianweiYpush",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "YinxianweiYpush",
            targets: ["YPushPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "YPushPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/YPushPlugin"),
        .testTarget(
            name: "YPushPluginTests",
            dependencies: ["YPushPlugin"],
            path: "ios/Tests/YPushPluginTests")
    ]
)