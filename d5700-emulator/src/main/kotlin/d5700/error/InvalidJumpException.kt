package d5700.error

class InvalidJumpException(address: Int) : EmulatorException(
    "Invalid jump address ${address.toString(16).uppercase()}. Jump addresses must be divisible by 2."
)