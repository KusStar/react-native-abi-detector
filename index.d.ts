interface IABIDetector {
  get: () => Promise<string>
}

interface IABIs {
  ARMV8: string
  ARMV7: string
  ARMV5: string
  X86_64: string
  X86: string
  UNKNOWN: string
}

declare const ABIDetector: IABIDetector
declare const ABIs: IABIs

export {
  ABIDetector,
  ABIs
}
