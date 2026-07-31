package d5700.error

class InvalidAsciiException(value: Int) : EmulatorException(
    "Invalid ASCII value ${value.toString(16).uppercase()}. Value must be between 00 and 7F."
)