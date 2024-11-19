// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "YinxianweiJpush",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "YinxianweiJpush",
            targets: ["JPushPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "JPushPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/JPushPlugin"),
        .testTarget(
            name: "JPushPluginTests",
            dependencies: ["JPushPlugin"],
            path: "ios/Tests/JPushPluginTests")
    ]
)