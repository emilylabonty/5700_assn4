package d5700.error

class InvalidHexDigitException(value: Int) : EmulatorException(
    "Invalid hex digit ${value.toString(16).uppercase()}. Value must be between 0 and F."
)