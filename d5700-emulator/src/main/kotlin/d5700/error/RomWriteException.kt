package d5700.error

class RomWriteException(address: Int) : EmulatorException(
    "Cannot write to ROM at address ${address.toString(16).uppercase()}."
)