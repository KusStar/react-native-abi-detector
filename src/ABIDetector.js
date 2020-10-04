import { NativeModules } from 'react-native'

const { RNABIDetector } = NativeModules

const ABIs = {
  ARMV8: RNABIDetector.ARMV8,
  ARMV7: RNABIDetector.ARMV7,
  ARMV5: RNABIDetector.ARMV5,
  X86_64: RNABIDetector.X86_64,
  X86: RNABIDetector.X86,
  UNKNOWN: RNABIDetector.UNKNOWN,
}

const ABIDetector = {
  get: RNABIDetector.get,
}

export {
  ABIs,
  ABIDetector
}
