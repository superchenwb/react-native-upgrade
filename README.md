
# react-native-upgrade

## Getting started

`$ npm install react-native-zy-upgrade --save`

### Mostly automatic installation

`$ react-native link react-native-zy-upgrade`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.zy.upgrade.RNUpgradePackage;` to the imports at the top of the file
  - Add `new RNUpgradePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-upgrade'
  	project(':react-native-upgrade').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-upgrade/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-upgrade')
  	```


## Usage
```javascript
import RNUpgrade from 'react-native-zy-upgrade';

// TODO: What to do with the module?
RNUpgrade;
```
  