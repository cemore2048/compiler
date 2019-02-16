package intermediate

sealed class SymbolTableKeyImpl : SymbolTableKey {
    //Constant
    object ConstantValue : SymbolTableKeyImpl()

    //Procedure or function
    object RoutineCode : SymbolTableKeyImpl()

    object RoutineSymbolTable : SymbolTableKeyImpl()

    object RoutineIntermediateCode : SymbolTableKeyImpl()

    object RoutineParams : SymbolTableKeyImpl()

    object RoutineRoutines : SymbolTableKeyImpl()

    //variable or record field value
    object DataValue : SymbolTableKeyImpl()
}