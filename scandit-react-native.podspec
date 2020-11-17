require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = package['name']
  s.version      = package['version']
  s.summary      = package['description']
  s.license      = package['license']

  s.authors      = package['author']
  s.homepage     = package['homepage']
  s.platform     = :ios, "9.0"

  s.source       = { :git => "https://github.com/Scandit/barcodescanner-sdk-react-native.git", :tag => package['version'] }
  s.source_files  = "ios/**/*.{h,m}"
  s.vendored_frameworks = 'ios/ScanditBarcodeScanner/Frameworks/ScanditBarcodeScanner.framework'
  s.frameworks = 'CoreText', 'OpenGLES', 'MessageUI', 'CoreVideo', 'UIKit', 'Foundation', 'CoreGraphics', 'AudioToolbox', 'AVFoundation', 'CoreMedia', 'QuartzCore', 'SystemConfiguration', 'MediaPlayer', 'Accelerate'
  s.weak_frameworks = 'CoreML'
  s.libraries = 'c++', 'iconv', 'z'

  s.dependency 'React'
end
