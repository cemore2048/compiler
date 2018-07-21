package backend

import backend.compiler.CodeGenerator
import backend.interpreter.Executor

object BackendFactory {
    @Throws
    fun createBackend(operation: String): Backend {
        return when (operation) {
            "compile" -> CodeGenerator()
            "execute" -> Executor()
            else -> throw Exception("Backend factory: Invalid operation $operation")
        }
    }
}
