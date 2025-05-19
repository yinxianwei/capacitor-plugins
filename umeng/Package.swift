// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "YinxianweiUmeng",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "YinxianweiUmeng",
            targets: ["UmengPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "UmengPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/UmengPlugin"),
        .testTarget(
            name: "UmengPluginTests",
            dependencies: ["UmengPlugin"],
            path: "ios/Tests/UmengPluginTests")
    ]
)