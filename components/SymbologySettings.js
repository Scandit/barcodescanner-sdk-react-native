export class SymbologySettings {

  constructor() {
    this.checksums = [];
    this.extensions = [];
  }

}

SymbologySettings.Checksum = {
    MOD_10: "mod10",
    MOD_11: "mod11",
    MOD_47: "mod47",
    MOD_43: "mod43",
    MOD_103: "mod103",
    MOD_1010: "mod1010",
    MOD_1110: "mod1110"
}

SymbologySettings.Extension = {
    FULL_ASCII: "full_ascii",
    REMOVE_LEADING_ZERO: "remove_leading_zero",
    RELAXED_SHARP_QUIET_ZONE_CHECK: "relaxed_sharp_quiet_zone_check",
    RETURN_AS_UPCA: "return_as_upca",
    REMOVE_LEADING_UPCA_ZERO: "remove_leading_upca_zero",
    STRIP_LEADING_FNC1: "strip_leading_fnc1"
}
