// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "YinxianweiYlocation",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "YinxianweiYlocation",
            targets: ["YlocationPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "YlocationPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/YlocationPlugin"),
        .testTarget(
            name: "YlocationPluginTests",
            dependencies: ["YlocationPlugin"],
            path: "ios/Tests/YlocationPluginTests")
    ]
)