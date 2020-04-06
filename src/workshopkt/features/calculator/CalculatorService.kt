package workshopkt.features.calculator

object CalculatorService {
    fun operate (op1: Int, op2: Int, operand: String? = "+"): Int {
        return when (operand) {
            "-" -> op1 - op2
            "*" -> op1 * op2
            "div" -> op1 / op2
            "mod" -> op1 % op2
            else -> op1 + op2
        }
    }
}