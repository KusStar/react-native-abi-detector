# react-native-abi-detector

## Getting Started

```
$ yarn add react-native-abi-detector
```

or

```
$ npm install --save react-native-abi-detector
```

#### Using React Native >= 0.60

Linking the package manually is not required anymore with [Autolinking](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md).

#### Using React Native < 0.60

```
$ react-native link react-native-abi-detector
```

## Usage

```jsx
import React, { useState, useEffect } from 'react';
import { ABIDetector, ABIs } from 'react-native-abi-detector';

const App = () => { 
  const [abi, setAbi] = useState(ABIs.UNKNOWN)

  useEffect(() => {
    ABIDetector.get().then(abi => {
      setAbi(abi)
    })
  }, [])

  return (
    <View style={{ flex: 1, backgroundColor: '#212121' }}>
      <Text>
        {abi}
      </Text>
    </View>
  )
}
```

## Props

```ts
interface ABIDetector {
  get: () => Promise<string>
}

interface ABIs {
  ARMV8: string
  ARMV7: string
  ARMV5: string
  X86_64: string
  X86: string
  UNKNOWN: string
}
```
## Thanks

- [zhaobozhen/LibChecker](https://github.com/zhaobozhen/LibChecker), the methods of abi detecting is basically copied from it

## License

[MIT](LICENSE)

