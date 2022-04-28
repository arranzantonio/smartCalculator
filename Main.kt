package calculator

import java.math.BigInteger

fun help () {
    println("Smart Calculartor")
    println("Input your operation. You can use as operators *, /, ^, and any combination of + and -. ")
    println("You can use parentheses. Operands can be integer numbers or variables. Varialbles' identifiers should be")
    println("composed of letters and in order to be used previously you should assign its value.")
    println("For example:")
    println("> a = 3")
    println("> b = -4")
    println("> -2 * a + (3 - b) * 2")
    println("Result: 8")
}
/*
    This function calculates based on the number of + an - in a string if it has to return + or - in order to
    perform addition or subtraction
 */

fun getOperation(string: String): String {
    var result = 1
    for (i in string) {
        result = if (i == '+') result * 1 else result * -1
    }
    return if (result == 1) "+" else "-"
}

/**
 * Esta función acepta una cadena de texto que puede ser un comando válido o una operación en notación infija y devuelve
 * una lista mutable que o bien está vacía significando que ha habido un error en la entrada del ususario o llena y
 * entonces es una lista con los números, variables, paréntesis y operardores en notación infija. Como todo se almacena
 * como strings no hay que hacer ningún cambio a BigInt
 */
fun validateInput(input: String): MutableList<String> {

    val regexCorrectCommand ="""/\w+|\s+|^${'$'}""".toRegex()
    val validCommands = listOf("/exit", "/help", "")
    val list = mutableListOf<String>()

    if (!input.matches(regexCorrectCommand)) {
        // This variable points to the next stringOperaticon to be processed
        var operationIndex = 0
        var lastToken = ""
        var openingParentheses = 0
        var closingParentheses = 0

        val toFindOperand = """\s*[+-]?[0-9]+\s*|\s*[a-zA-z]+\s*""".toRegex()
        val toFindOpeningParentheses = """\s*\(\s*""".toRegex()
        val toFindClosingParentheses = """\s*\)\s*""".toRegex()
        val toFindOperator = """\s*[+-]+\s*|\s*[*^/]\s*""".toRegex()

        while (operationIndex < input.length) {
            /**
             * At the beginning of the expression only a opening parentheses or a numeric (or varialbe) are possible. If
             * neither one nor the other are found the expression is not valid.
             */
            if (operationIndex == 0) {
                val matchOperand = toFindOperand.find(input, operationIndex)
                val matchOpeningParentheses = toFindOpeningParentheses.find(input, operationIndex)
                if (matchOperand?.range?.first == operationIndex) {
                    list.add(matchOperand?.value!!.trim())
                    operationIndex = matchOperand?.range?.last!!
                    operationIndex++
                    lastToken = "value"
                } else if (matchOpeningParentheses?.range?.first == operationIndex) {
                    list.add(matchOpeningParentheses?.value!!.trim())
                    operationIndex = matchOpeningParentheses?.range?.last!!
                    operationIndex++
                    lastToken = "openingParentheses"
                    ++openingParentheses
                } else {
                    println("Invalid expression")
                    list.clear()
                    break
                }
                /**
                 * In the middle of the expression there can be other possibilities. Each one depends on the last token type
                 * found.
                 */
            } else {
                when (lastToken) {
                    "openingParentheses" -> {
                        val matchOperand = toFindOperand.find(input, operationIndex)
                        val matchOpeningParentheses = toFindOpeningParentheses.find(input, operationIndex)
                        if (matchOperand?.range?.first == operationIndex) {
                            list.add(matchOperand?.value!!.trim())
                            operationIndex = matchOperand?.range?.last!!
                            operationIndex++
                            lastToken = "value"
                        } else if (matchOpeningParentheses?.range?.first == operationIndex) {
                            list.add(matchOpeningParentheses?.value!!.trim())
                            operationIndex = matchOpeningParentheses?.range?.last!!
                            operationIndex++
                            lastToken = "openingParentheses"
                            ++openingParentheses
                        } else {
                            println("Invalid expression")
                            list.clear()
                            break
                        }
                    }
                    "value" -> {
                        val matchOperator = toFindOperator.find(input, operationIndex)
                        val matchClosingParentheses = toFindClosingParentheses.find(input, operationIndex)
                        if (matchOperator?.range?.first == operationIndex) {
                            list.add(matchOperator?.value!!.trim())
                            operationIndex = matchOperator?.range?.last!!
                            operationIndex++
                            lastToken = "operator"
                        } else if (matchClosingParentheses?.range?.first == operationIndex) {
                            list.add(matchClosingParentheses?.value!!.trim())
                            operationIndex =matchClosingParentheses?.range?.last!!
                            operationIndex++
                            lastToken = "closingParentheses"
                            ++closingParentheses
                        } else {
                            println("Invalid expression")
                            list.clear()
                            break
                        }
                    }
                    "closingParentheses" -> {
                        val matchOperator = toFindOperator.find(input, operationIndex)
                        val matchClosingParentheses = toFindClosingParentheses.find(input, operationIndex)
                        if (matchOperator?.range?.first == operationIndex) {
                            list.add(matchOperator?.value!!.trim())
                            operationIndex = matchOperator?.range?.last!!
                            operationIndex++
                            lastToken = "operator"
                        } else if (matchClosingParentheses?.range?.first == operationIndex) {
                            list.add(matchClosingParentheses?.value!!.trim())
                            operationIndex =matchClosingParentheses?.range?.last!!
                            operationIndex++
                            lastToken = "closingParentheses"
                            ++closingParentheses
                        } else {
                            println("Invalid expression")
                            list.clear()
                            break
                        }
                    }
                    "operator" -> {
                        val matchOperand = toFindOperand.find(input, operationIndex)
                        val matchOpeningParentheses = toFindOpeningParentheses.find(input, operationIndex)
                        if (matchOperand?.range?.first == operationIndex) {
                            list.add(matchOperand?.value!!.trim())
                            operationIndex = matchOperand?.range?.last!!
                            operationIndex++
                            lastToken = "value"
                        } else if (matchOpeningParentheses?.range?.first == operationIndex) {
                            list.add(matchOpeningParentheses?.value!!.trim())
                            operationIndex = matchOpeningParentheses?.range?.last!!
                            operationIndex++
                            lastToken = "openingParentheses"
                            ++openingParentheses
                        } else {
                            println("Invalid expression")
                            list.clear()
                            break
                        }
                    }
                    else -> println()
                }
            }
        }
        if (closingParentheses != openingParentheses && operationIndex == input.length) {
            println("Invalid expression")
            list.clear()
        }
    } else if (input.matches(regexCorrectCommand) && input !in validCommands) {
        println("Unknown command")
        list.clear()
    } else list.add(input)
    return list
}

fun getReversedPolish (infixOperation: MutableList<String>): MutableList<String> {
    val operatorStack = mutableListOf<String>()
    val outputQueue = mutableListOf<String>()
    val mapPrecedence = mapOf("^" to 2, "*" to 1, "/" to 1, "+" to 0, "-" to 0)
    while (!infixOperation.isEmpty()) {
        val element = infixOperation.removeFirst()
        when (element) {
            in mapPrecedence.keys -> {
                do {
                    if (operatorStack.isEmpty() || operatorStack.last() == "(") {
                        operatorStack.add(element)
                        break
                    }
                    if (mapPrecedence[element]!! <= mapPrecedence[operatorStack[operatorStack.lastIndex]]!!) {
                        outputQueue.add(operatorStack.removeLast())
                    } else {
                        operatorStack.add(element)
                        break
                    }
                } while (true)
            }
            "(" -> operatorStack.add(element)
            ")" -> {
                while (operatorStack.last() != "(") {
                    outputQueue.add(operatorStack.removeLast())
                }
                operatorStack.removeLast()
            }
            else -> outputQueue.add(element)
        }
    }
    while (operatorStack.isNotEmpty()) {
        outputQueue.add(operatorStack.removeLast())
    }
    return outputQueue
}

fun evaluateReversedPolish (reversedPolish: MutableList<String>): BigInteger {
    val operators = listOf("+", "-", "*", "/") //, "^"
    val stack = mutableListOf<String>()
    while (reversedPolish.isNotEmpty()) {
        if (reversedPolish.first() !in operators) stack.add(reversedPolish.removeFirst())
        else {
            val op1 = stack.removeLast().toBigInteger()
            val op2 = stack.removeLast().toBigInteger()
            val result = when (reversedPolish.removeFirst()) {
                "+" -> op2 + op1
                "-" -> op2 - op1
                "*" -> op2 * op1
//                "^" -> {
//                    var p = 1
//                    for (i in 1..op1) p *= op2
//                    p
//                }
                else -> op2 / op1
            }
            stack.add(result.toString())
        }
    }
    return stack.removeLast().toBigInteger()
}

/**
 * Parameters:
 * -> variables: Is a map that contains the defined variables and the values that they have.
 * -> inputList: Is the list that contains numbers, variables, operators.
 * Returns:
 * -> A mutable list that contains variables' values and +++--+ operators evaluated to + or -
 */
fun parseInputList (variables: MutableMap<String, BigInteger>, inputList: MutableList<String>): MutableList<String> {
    var list = mutableListOf<String>()
    for (i in inputList) {
        if (i.matches("""[a-zA-Z]+""".toRegex())) {
            if (variables.containsKey(i)) list.add(variables.getValue(i).toString())
            else {
                println("Unknown variable")
                list.clear()
            }
        } else  if (i.matches("""[+-]+""".toRegex())) list.add(getOperation(i))
                else list.add(i)
    }
    return list
}

fun main() {
    var exit = false
    val variables = mutableMapOf<String, BigInteger>()
    while (!exit) {
        val s = readln()

        /**
         * Toda esta parte de código lo que hace es comprobar que lo que el usuario introduce es una asignación válida
         * para una variable. Primero comprueba que es una igualdad y separa en dos los términos de la igualdad. Si
         * el primer término es un nombre válido de variable comienza a evaluar si el segundo término es o un número o
         * una expresión válida de variable. Si es un número lo almacena en el mapa de variables. Si es un nombre de
         * variable y ese nombre no está en el mapa avisa sobre que no existe esa variable y si sí que existe actualiza
         * el valor de la variable de la izquierda de la igualadad con el valor de la variable de la derecha.
         */
        val equalityRegex = """.*=.*""".toRegex()
        if (s.matches(equalityRegex)) {
            val (valName, valValue) = s.split("""\s*=\s*""".toRegex(), 2).map { it.trim() }
            val variableNameRegex = """[a-zA-z]+""".toRegex()
            val numberRegex = """[+-]?\d+""".toRegex()
            if (valName.matches(variableNameRegex)) {
                if (valValue.matches(numberRegex)) variables[valName] = valValue.toBigInteger()
                else  if(valValue.matches(variableNameRegex))
                    if (variables.containsKey(valValue)) variables[valName] = variables[valValue]!!
                    else println("Unknown variable")
                else println("Invalid assignment")
            } else println("Invalid identifier")
        } else {
            /**
             * En esta parte del código, al que se entra solamente si la entrada no es una igualdad se evalúa la
             * expresión que se introduce, que puede ser un comando válido (/help o /exit) o bien una expresión numérica
             * en notación infija que hay que calcular. Dicha expresión en notación infija al introducir los paréntesis
             * y las operaciones se me está haciendo muy complicada de procesar pues según los ejemplo
             */
            val list = validateInput(s)
            if (list.isNotEmpty()) {
                when (list[0]) {
                    "/exit" -> {
                        println("Bye!")
                        exit = true
                    }
                    "/help" -> help()
                    "" -> continue
                    else -> {
                        val listReversed = getReversedPolish(parseInputList(variables, list))
                        if (listReversed.isNotEmpty()) println(evaluateReversedPolish(listReversed))
                    }
                }
            }
        }
    }
}